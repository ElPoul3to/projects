###-----------------------------IMPORTATIONS-------------------------------###
import numpy as np
from numpy.polynomial import Polynomial
import copy
import time
from secrets import token_hex

###-------------------------VARIABLES GLOABLES------------------------------###
P_AES = Polynomial([1,1,0,1,1,0,0,0,1])




###------FONCTIONS DE CALCULS POUR LES POLYNOMES DANS R256------###
def polynomeZ2_Z(P):
    for i in range(P.degree()+1):
        P.coef[i] = P.coef[i]%2
    return P

def addition(P1, P2):         #ou XOR dans Z/2Z
    return polynomeZ2_Z((P1%P_AES) + (P2%P_AES))

def multiplication(P1, P2):   #ou AND dans Z/2Z
    return polynomeZ2_Z( ((P1%P_AES)*(P2%P_AES))%P_AES )

def division(P1, P2):
    return polynomeZ2_Z(P1//P2)

def inverse(P):
    if P == Polynomial([0]):
        return Polynomial([0])
    else:
        return euclide_etendu(P_AES, P)[2]

def pgcd(P1, P2):   #où P1 est le polynôme irréductible du corps
    r1 = False
    while not r1:
        P1, P2 = P2, polynomeZ2_Z(P1%P2)
        #Condition car P2 n'est pas forcément Polynomial([1])
        if (P2.coef[1:] == np.zeros(P2.degree())).all():
            r1 = True
    return P1

#P2 le polynôme avec lequel on veut trouver l'inverse
def euclide_etendu(P1, P2):
    X = Polynomial([0])
    Y = Polynomial([1])
    U = Polynomial([1])
    V = Polynomial([0])
    
    while P1 != Polynomial([0]):
        Q = polynomeZ2_Z(P2 // P1)
        R = polynomeZ2_Z(P2%P1)
        
        M = polynomeZ2_Z(X - U * Q)
        N = polynomeZ2_Z(Y - V * Q)
        
        P2 = P1
        P1 = R
        X = U
        Y = V
        U = M
        V = N
    'retourne (pgcd, u, v) v -> inverse de P2'
    return (P2, X, Y)






###---------FONCTIONS DE CONVERTIONS----------------###
#Convention '0b' pour bianire et '0x' pour hexa
def binEnPoly(nbBin):
    L=[int(x) for x in str(nbBin[2::])]
    L.reverse()
    return Polynomial(L)

def hexaEnPoly(nbHexa):
    return binEnPoly(hexaEnBin(nbHexa))

def polyEnBin(P):
    coeff = list(P.coef)
    coeff = list(map(int, coeff))
    coeff.reverse()
    nbBinSTR = "".join( list(map(str, coeff)) )
    #Tjrs écrire dans un octet complet
    return "0b"+(8-len(nbBinSTR))*"0"+nbBinSTR

def binEnHexa(nbBin):
    return hex(int(nbBin, 2))

def hexaEnBin(nbHexa):
    return bin(int(nbHexa, 16))







###----------------------FONCTIONS SUR LES MATRICES-------------------------###
#Convention Matrice = list
#Matrice Polynomiale = le polynôme

def msgHexaEnMatBin(msg):
    L=msg.split()
    Mbin=[[0 for i in range(4)] for i in range(4)]
    for i in range(4):
        for j in range(4):
            Mbin[j][i]= bin(int(L[i*4+j], 16))
    return Mbin

def matBinEnMsgHexa(Mbin):
    msg=""
    for i in range(4):
        for j in range(4):
            msg+=binEnHexa(Mbin[j][i])[2::]
            msg+=" "
    return msg

def msgHexaEnMatPoly(msg):
    return matBinEnPoly(msgHexaEnMatBin(msg))

def matPolyEnMSgHexa(Mpoly):
    return matBinEnMsgHexa(matPolyEnBin(Mpoly))

def matBinEnHexa(Mbin):
    for i in range(4):
        for j in range(4):
            Mbin[i][j]=binEnHexa(Mbin[i][j])
    return Mbin

def matHexaEnBin(Mhexa):
    for i in range(4):
        for j in range(4):
            Mhexa[i][j]=hexaEnBin(Mhexa[i][j])
    return Mhexa


def matBinEnPoly(Mbin):
    for i in range(4):
        for j in range(4):
            Mbin[i][j] = binEnPoly(Mbin[i][j])
    return Mbin


def matPolyEnBin(Mpoly):
    for i in range(4):
        for j in range(4):
            Mpoly[i][j] = polyEnBin(Mpoly[i][j])
    return Mpoly

def matPolyEnHexa(Mpoly):
    return matBinEnHexa(matPolyEnBin(Mpoly))

def matHexaEnPoly(Mhexa):
    return matBinEnPoly(matHexaEnBin(Mhexa))


def octetEnMatCol(octet):   #NB: octet en bianire donc str
    M = [[0] for i in range(8)]
    for i in range(1,9):
        M[i-1][0] = (int(octet[-i]))    #Matrice colonne du terme de plus bas degré au plus haut
    return M

def polyEnMatCol(P):
    return octetEnMatCol(polyEnBin(P))

def matColEnOctet(Mcol):
    octet="0b"
    for i in range(1,9):
        octet+= str(Mcol[-i][0])
    return octet

def matColEnPoly(Mcol):
    return binEnPoly(matColEnOctet(Mcol))

def polyEnMatCol(P):
    return octetEnMatCol(polyEnBin(P))


#Opération matricielle dans Z/2Z
def multplicationMat(M1, M2):   #NB: M1*M2
    n=len(M1)
    p=len(M1[0])
    q=len(M2[0])
    M = [[0 for x in range(q)] for y in range(n)]  
  
    for i in range(n): 
        for j in range(q): 
            for k in range(p): 
                M[i][j] = (M[i][j] + M1[i][k] * M2[k][j])%2
    return M


def additionMat(M1,M2):
    for i in range(len(M1)):
        for j in range(len(M1[0])):
            M1[i][j]= (M1[i][j]+M2[i][j])%2
    return M1


def multplicationMatPoly(M1, M2):   #NB: M1*M2
    n=len(M1)
    p=len(M1[0])
    q=len(M2[0])
    M = [[0 for x in range(q)] for y in range(n)]  
  
    for i in range(n): 
        for j in range(q): 
            for k in range(p): 
                M[i][j] = addition(M[i][j] , multiplication(M1[i][k] , M2[k][j]))
    return M


def additionMatPoly(M1, M2):
    for i in range(len(M1)):
        for j in range(len(M1[0])):
            M1[i][j]= addition(M1[i][j],M2[i][j])
    return M1




###---------------------------------AES-----------------------------------###
def addRoundKey(matPoly, matPolyClef):
    for i in range(4):
        for j in range(4):
            matPoly[i][j] = addition(matPoly[i][j], matPolyClef[i][j])
    return matPoly



#Obtention de la S-BOX en utilisant la transformation affine 
#avec les éléments de F256
def subBytes(matPoly):
    matSB = matPoly
    for i in range(4):
        for j in range(4):
            matSB[i][j] = inverse(matSB[i][j])
    
    for i in range(4):
        for j in range(4):
            matSB[i][j] = matColEnPoly(transfoLin(polyEnMatCol(matSB[i][j])))
    return matSB


def transfoLin(matCol):     #matCol représente en octet comme c
    A = [[1, 0, 0, 0, 1, 1, 1, 1],
         [1, 1, 0, 0, 0, 1, 1, 1],
         [1, 1, 1, 0, 0, 0, 1, 1],
         [1, 1, 1, 1, 0, 0, 0, 1],
         [1, 1, 1, 1, 1, 0, 0, 0],
         [0, 1, 1, 1, 1, 1, 0, 0],
         [0, 0, 1, 1, 1, 1, 1, 0],
         [0, 0, 0, 1, 1, 1, 1, 1]]
    c= [[1],[1],[0],[0],[0],[1],[1],[0]]
    return additionMat(multplicationMat(A, matCol), c)



def shiftRows(matPoly):
    for i in range(4):
        j=0
        while j<i:
            elt = matPoly[i][0]
            for k in range(3, -1, -1):
                matPoly[i][k], elt =elt, matPoly[i][k] 
            j+=1
    return matPoly


def mixColumns(matPoly):
    C = [[Polynomial([0,1]),Polynomial([1,1]),Polynomial([1]),  Polynomial([1])],
         [Polynomial([1]),  Polynomial([0,1]),Polynomial([1,1]),Polynomial([1])],
         [Polynomial([1]),  Polynomial([1]),  Polynomial([0,1]),Polynomial([1,1])],
         [Polynomial([1,1]),Polynomial([1]),  Polynomial([1]),  Polynomial([0,1])]]
    return multplicationMatPoly(C, matPoly)



#k = 4k', l=4k'+1, n=4k'+2, m=4k'+3
def sousCleEnCle(wk, wl, wn , wm):   #NB wi = MatPoly 
    K = [[] for _ in range(4)]
    for i in range(4):
            K[i].append(wk[i][0])
            K[i].append(wl[i][0])
            K[i].append(wn[i][0])
            K[i].append(wm[i][0])
    return K


def genClef(matPolyClef):
    wk = [0 for _ in range(4) ]
    wl = [0 for _ in range(4) ]
    wn = [0 for _ in range(4) ]
    wm = [0 for _ in range(4) ]
    for i in range(4):
        wk[i] = [matPolyClef[i][0]]     #création de sous liste pour le calcul matriciel
        wl[i] = [matPolyClef[i][1]]
        wn[i] = [matPolyClef[i][2]]
        wm[i] = [matPolyClef[i][3]]
    
        
    Kmult=[sousCleEnCle(wk, wl, wn, wm)]
    for i in range(10):  #AES-128 = 9 tours -> jusqu'à w39 à déterminer 10
        wmBIS = copy.deepcopy(wm)   #Problème de copiage de liste en Python
        wmBIS[0][0], wmBIS[1][0], wmBIS[2][0], wmBIS[3][0]= wmBIS[1][0], wmBIS[2][0], wmBIS[3][0], wmBIS[0][0]
        
        
        for a in range(4):
            wmBIS[a][0]= matColEnPoly(transfoLin(polyEnMatCol(inverse(wmBIS[a][0]))))     #Application de Subtype sur wm
        
        
        coeffPolyActu = [0 for k in range(i)]
        coeffPolyActu.append(1)
        wmBIS = additionMatPoly(wmBIS, [[Polynomial(coeffPolyActu)], [Polynomial([0])], [Polynomial([0])], [Polynomial([0])]])
        
        
        
        wk=additionMatPoly(wk, wmBIS)
        wl=additionMatPoly(wl, wk)
        wn=additionMatPoly(wn, wl)
        wm=additionMatPoly(wm, wn)
        Kmult.append(sousCleEnCle(wk, wl, wn, wm))
    return Kmult



def chiffrementAES(msg, clef):
    matPolyMsg = msgHexaEnMatPoly(msg)
    matPolyClef = msgHexaEnMatPoly(clef)
    Kmult = genClef(matPolyClef)
    
    matPolyMsg = addRoundKey(matPolyMsg, Kmult[0])
    
    for i in range(1, 10):  #9 Tours pour AES-128
        matPolyMsg = subBytes(matPolyMsg)
        matPolyMsg = shiftRows(matPolyMsg)
        matPolyMsg = mixColumns(matPolyMsg)
        matPolyMsg = addRoundKey(matPolyMsg, Kmult[i])
        
    #Tour final
    matPolyMsg = subBytes(matPolyMsg)
    matPolyMsg = shiftRows(matPolyMsg)
    matPolyMsg = addRoundKey(matPolyMsg, Kmult[10])
    
    return matPolyEnMSgHexa(matPolyMsg)






def invSubBytes(matPoly):
    matSB = matPoly
    
    for i in range(4):
        for j in range(4):
            matSB[i][j] = matColEnPoly(invTransfoLin(polyEnMatCol(matSB[i][j])))
    
    for i in range(4):
        for j in range(4):
            matSB[i][j] = inverse(matSB[i][j])
    
    return matSB

def invTransfoLin(matCol):     #matCol représente en octet comme c
    Ainv = [[0, 0, 1, 0, 0, 1, 0, 1],
            [1, 0, 0, 1, 0, 0, 1, 0],
            [0, 1, 0, 0, 1, 0, 0, 1],
            [1, 0, 1, 0, 0, 1, 0, 0],
            [0, 1, 0, 1, 0, 0, 1, 0],
            [0, 0, 1, 0, 1, 0, 0, 1],
            [1, 0, 0, 1, 0, 1, 0, 0],
            [0, 1, 0, 0, 1, 0, 1, 0]]
    c= [[1],[1],[0],[0],[0],[1],[1],[0]]
    return multplicationMat(Ainv, additionMat(matCol, c))



def invShiftRows(matPoly):
    for i in range(4):
        j=0
        while j<i:
            elt = matPoly[i][3]
            for k in range(4):
                matPoly[i][k], elt =elt, matPoly[i][k] 
            j+=1
    return matPoly


def invMixColumns(matPoly):
    Cinv = [[Polynomial([0,1,1,1]), Polynomial([1,1,0,1]), Polynomial([1,0,1,1]), Polynomial([1,0,0,1])],
            [Polynomial([1,0,0,1]), Polynomial([0,1,1,1]), Polynomial([1,1,0,1]), Polynomial([1,0,1,1])],
            [Polynomial([1,0,1,1]), Polynomial([1,0,0,1]), Polynomial([0,1,1,1]), Polynomial([1,1,0,1])],
            [Polynomial([1,1,0,1]), Polynomial([1,0,1,1]), Polynomial([1,0,0,1]), Polynomial([0,1,1,1])]]
    return multplicationMatPoly(Cinv, matPoly)


def dechiffrementAES(msgC, clef):
    matPolyMsg = msgHexaEnMatPoly(msgC)
    matPolyClef = msgHexaEnMatPoly(clef)
    Kmult = genClef(matPolyClef)
    
    matPolyMsg = addRoundKey(matPolyMsg, Kmult[10])
    
    for i in range(9,0,-1):  #9 Tours pour AES-128
        matPolyMsg = invShiftRows(matPolyMsg)
        matPolyMsg = invSubBytes(matPolyMsg)
        matPolyMsg = addRoundKey(matPolyMsg, Kmult[i])
        matPolyMsg = invMixColumns(matPolyMsg)
        
    #Tour final
    matPolyMsg = invShiftRows(matPolyMsg)
    matPolyMsg = invSubBytes(matPolyMsg)
    matPolyMsg = addRoundKey(matPolyMsg, Kmult[0])
    
    return matPolyEnMSgHexa(matPolyMsg)
    



###------------------------TEST PERFORMANCES-------------------------------###
def espace(s):
    newS = ""
    n=len(s)
    for i in range(n):
        newS+=s[i]
        if i %2 == 1 and i<n-1:
            newS+=" "
    return newS

def msg_cle_aleatoire():
    return espace(token_hex(16)), espace(token_hex(16))


def tps_chffirement_moyen(n):   #n est le nombre d'expérience générée
    temps=[]
    for _ in range(n):
        msg, cle = msg_cle_aleatoire()
        start = time.time()
        chiffrementAES(msg, cle)
        end = time.time()
        temps.append(end - start)
    return sum(temps)/len(temps)


#print(tps_chffirement_moyen(10))
###-------------------------------MAIN--------------------------------------###
'''
GEN = Polynomial([1,1])
GEN_Puissance=GEN
PUISSANCES_GENERATEUR = [Polynomial([1]), GEN]      #Indice = à la n-ième puissance
                                                    #Pas de dictionnaire car les Polynômes ne peuvent pas être stocké
for i in range(256):
    GEN_Puissance = multiplication(GEN_Puissance, GEN)
    PUISSANCES_GENERATEUR.append(GEN_Puissance)

msg=   "32 43 f6 a8 88 5a 30 8d 31 31 98 a2 e0 37 07 34"
cle=   "2b 7e 15 16 28 ae d2 a6 ab f7 15 88 09 cf 4f 3c"
msgC = "39 25 84 1d 02 dc 09 fb dc 11 85 97 19 6a 0b 32"
'''


msg=   "32 43 f6 a8 88 5a 30 8d 31 31 98 a2 e0 37 07 34"
cle=   "2b 7e 15 16 28 ae d2 a6 ab f7 15 88 09 cf 4f 3c"
start = time.time()
cipher=chiffrementAES(msg, cle)
print(cipher)
print(dechiffrementAES(cipher, cle))
end = time.time()
elapsed = end - start
print(f'Temps d\'exécution : {elapsed}')

"""
start = time.time()
msgC = "39 25 84 1d 02 dc 09 fb dc 11 85 97 19 6a 0b 32"
print(dechiffrementAES(msgC, cle))
end = time.time()
elapsed = end - start
print(f'Temps d\'exécution : {elapsed}')
"""

