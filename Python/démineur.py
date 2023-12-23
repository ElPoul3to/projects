from random import randint

global GAME

"""
Règles du démineur
-> Choisir la hauteur, largeur et le nombre de mines
-> Pour marquer une case: d + numéro de la ligne + lettre de la colonne. 
Par exemple, saisir "d02A" indiquera qu’on veut marquer (ou enlever le drapeau)
la case 2A.
-> Pour relever une case: r + nuḿero de la ligne + lettre de la colonne.
Par exemple, saisir "r10F" indiquera qu’on veut reveler la case 10F.
"""

def init(h:int, l:int, n:int):
    global T
    global TADJ
    global cooN
    
    if h >= 1 and l >= 1 and n>=1:
        T = [ [0 for i in range(l)] for j in range(h)]
        TADJ = [ [0 for i in range(l)] for j in range(h)]
        
        tot_n = []
        nb= 0
        while nb != n:
            alea_n = randint(0, h*l-1)
            if tot_n.count(alea_n) == 0:
                tot_n.append(alea_n)
                nb+=1
        
        cooN = []
        for n in tot_n:
            cooN.append((n // l, n % l))
            TADJ[n // l][n % l] = -1

def caseCorrecte(i: int, j: int):
    return (0 <= i < len(TADJ)) and (0 <= j < len(TADJ[0]))


def coordCompteur():
    coordCompt = [ [] for n in range(len(cooN))]
    for nbN in range(len(cooN)):
        cooH = cooN[nbN][0]
        cooL = cooN[nbN][1]
        for y in range(-1,2):
            for x in range(-1,2):
                coordCompt[nbN].append((cooH+y, cooL+x))
        coordCompt[nbN].pop(4)
    return coordCompt


def calculerAdjacent():
    coordAdja = coordCompteur()
    for col in range(len(coordAdja)):
        for lig in range(8):
            if caseCorrecte(coordAdja[col][lig][0], coordAdja[col][lig][1]) == True:
                TADJ[coordAdja[col][lig][0]][coordAdja[col][lig][1]] +=1
    for coord in cooN:
        TADJ[coord[0]][coord[1]] = -1


def generationGrilleCol():
    global ALPHABET
    ALPHABET = ["A","B","C","D","E","F",'G',"H",'I',"J","K","L","M","N","O","P",
                "Q","R",'S',"T",'U',"V","W","X","Y","Z","a","b","c","d","e","f",
                "g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v",
                "w","x","y","z"]
    nbCol = len(TADJ[0])
    del ALPHABET[nbCol:]
    grille = "  |"+" | ".join(ALPHABET)+"|\n"
    return grille


def affichageGrille():
    grille = generationGrilleCol()
    tablAffiche = [ [] for tabl in range(len(T))]
    for i in range(len(T)):
        for j in range(len(T[0])):
            if T[i][j] == 0:
                tablAffiche[i].append(" ")
            elif T[i][j] == 1:
                if TADJ[i][j] == -1:
                    compt = '!'
                    GAME[0] = False
                else:
                    compt = f"{str(TADJ[i][j])}"
                tablAffiche[i].append(compt)
            elif T[i][j] == 2:
                tablAffiche[i].append("X")
    
    for nbL in range(len(T)):
        if nbL < 10:
            nbAffiché = "0"+str(nbL)
        else:
            nbAffiché = str(nbL)
        grille += f"{nbAffiché}|"
        grille += " | ".join(tablAffiche[nbL]) + "|\n"
    print(grille)


def cooSaisie(saisie:str):
    i = int(saisie[1]+ saisie[2])
    j = int(ALPHABET.index(saisie[3]))
    return (i ,j)


def actionDrap(i, j):
    T[i][j] = 2
    
def actionRevelation(i, j):
    T[i][j] = 1


def jeuInit():
    h = input("Hauteur de la grille: ")
    l = input("Largeur de la grille: ")
    n = input("Nombre de mines: ")
    if verifInit(h,l,n) == True:
        h, l, n = int(h), int(l), int(n)
    else:
        return False
    init(h, l, n)
    calculerAdjacent()
    return True


def verifInit(h, l, n):
    try:
        h, l, n = int(h), int(l), int(n)
    except:
        print("Les valeurs renseignées doivent être des nombres entiers !")
        return False
    else:
        return True

def verifFormat(saisie:str):
    if saisie[0] == "r" or saisie[0] == "d":
        if int(saisie[1]+saisie[2]) < len(T):
            if saisie[3] in ALPHABET:
                return True
    return False


def jeuCase():
    global GAME
    GAME = [True, False]
    nbNonMines = len(T)*len(T[0]) - len(cooN)
    while GAME[0] == True:
        cmd = input("Saisir la commande: ")
        if verifFormat(cmd) == True:
            coord = cooSaisie(cmd)
            if cmd[0] == "d":
                actionDrap(coord[0], coord[1])
            elif cmd[0] == "r":
                actionRevelation(coord[0], coord[1])
        else:
            print("La commande que vous avez saisie est invalide !\nRecommencez !")
        affichageGrille()
        
        nbUn = 0
        for ligne in T :
            nbUn += ligne.count(1)
            if nbUn == nbNonMines:
                print("Vous avez gagné")
                GAME[0], GAME[1] = False, True
    if GAME[1] == False:
        print("Vous avez perdu")
    
def affichageTADJ():
    for ligne in range(len(TADJ)):
        print(TADJ[ligne] , "\n")
        
 
def main():
    jeuInit()
    affichageGrille()
    jeuCase()

main()
