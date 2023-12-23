# Projet de Guilherme Martins et de Thomas Fargues #


# Importations #
from tkinter import *
from tkinter.messagebox import *
from random import *
import time
import math
import threading

# Fenêtre #
fen=Tk()
fen.geometry('600x600')
fen.title('Un Jour, Un Arbre')

# Fonctions #
def sun_color():
    m=100
    while x1<549:
        couleur='#{:02x}{:02x}{:02x}'
        if x1 < 299:
            m +=25
        elif x1 > 299:
            m -=25
        
        n,p=255,0
        can.itemconfigure('pion',fill=couleur.format(n,m,p))
        
        time.sleep(1.5)
        can.update()
    
def fct_quitter():
    showinfo('Notification','Au revoir !')
    fen.destroy()

def demarrage():
    qu = askquestion('Notification','    Voulez-vous planter un arbre ?',
                     icon='info')
    if qu == 'no':
        showinfo('Notification','Dommage !')
    else:
        threading.Thread(target=fct_arbre).start()
        threading.Thread(target=fct_soleil).start()
        threading.Thread(target=sun_color).start()

def fct_soleil():
    global x1
    t = 0                
    x0,y0,d = 300,300,45    
    r =     250    
    x1 = x0+d
    y1 = y0+d
    sun = can.create_oval(x0,y0,x1,y1,fill='#ff6400',tag='pion')
     
    while x1 < 549:
        delta = 0.00003 #En fct des ordis
        x1 = x0 - r * math.cos(t)
        y1 = y0 - r * math.sin(t)
        t += delta
        if t > 2*math.pi:
            t = 1
        can.coords(sun,x1-20,y1-20,x1+20,y1+20)
    
    
def fct_arbre():
        xyab2 = 278,450,324,450
        can.create_line(xyab2, fill="Black")
        time.sleep(0.4)
        xybd2 = 324,450,324,502
        can.create_line(xybd2, fill="Black")
        time.sleep(0.4)
        xyde2 = 324,502,278,502
        can.create_line(xyde2, fill="Black")
        time.sleep(0.4)
        xyea2 = 278,502,278,450
        can.create_line(xyea2, fill="Black")
        time.sleep(0.4)
        xyac2 = 278,450,302,470
        can.create_line(xyac2, fill="Black")
        time.sleep(0.4)
        xycb2 = 302,470,324,450
        can.create_line(xycb2, fill="Black")
        time.sleep(0.4)
        xycd2 = 302,470,323,502
        can.create_line(xycd2, fill="Black")
        time.sleep(0.4)
        xyce2 = 302,470,278,502
        can.create_line(xyce2, fill="Black")
        time.sleep(0.4)
        
        xyac = 132,450,468,450
        can.create_line(xyac, fill="Black")
        time.sleep(0.4)
        xyci = 468,450,384,333
        can.create_line(xyci, fill="Black")
        xyha = 216,333,132,450
        can.create_line(xyha, fill="Black")
        time.sleep(0.4)
        xyik = 384,333,435,333
        can.create_line(xyik, fill="Black")
        xyjh = 164,333,216,333
        can.create_line(xyjh, fill="Black")
        time.sleep(0.4)
        xyko = 435,333,349,226
        can.create_line(xyko, fill="Black")
        xyvj = 252,226,164,333
        can.create_line(xyvj, fill="Black")
        time.sleep(0.4)
        xyop = 349,226,399,226
        can.create_line(xyop, fill="Black")
        xysv = 202,226,252,226
        can.create_line(xysv, fill="Black")
        time.sleep(0.4)
        xypt =399,226,300,88
        can.create_line(xypt, fill="Black")
        xyts = 300,88,202,226
        can.create_line(xyts, fill="Black")
        time.sleep(0.4)

        xytu = 300,88,283,184
        can.create_line(xytu, fill="Black")
        time.sleep(0.4)
        xyur = 283,184,329,129
        can.create_line(xyur, fill="Black")
        xyus = 283,184,202,226
        can.create_line(xyus, fill="Black")
        time.sleep(0.4)
        xyuv = 283,184,252,226
        can.create_line(xyuv, fill="Black")
        xyum = 283,184,313,274
        can.create_line(xyum, fill="Black")
        time.sleep(0.4)
        xymf = 313,274,346,365
        can.create_line(xymf, fill="Black")
        xymo = 313,274,349,226
        can.create_line(xymo, fill="Black")
        time.sleep(0.4)
        xyoq = 349,226,375,193
        can.create_line(xyoq, fill="Black")
        xyor = 349,226,329,129
        can.create_line(xyor, fill="Black")
        time.sleep(0.4)
        xyou = 349,226,283,184
        can.create_line(xyou, fill="Black")
        xyuh = 283,184,216,333
        can.create_line(xyuh, fill="Black")
        time.sleep(0.4)
        xymh = 313,274,216,333
        can.create_line(xymh, fill="Black")
        xyhl = 216,333,207,281
        can.create_line(xyhl, fill="Black")
        time.sleep(0.4)
        xyof = 349,226,346,365
        can.create_line(xyof, fill="Black")
        xyoi = 349,226,384,333
        can.create_line(xyoi, fill="Black")
        time.sleep(0.4)
        xyhg = 216,333,272,337
        can.create_line(xyhg, fill="Black")
        xygf = 272,337,346,365
        can.create_line(xygf, fill="Black")
        time.sleep(0.4)
        xymg = 313,274,272,337
        can.create_line(xymg, fill="Black")
        xyfi = 346,365,384,333
        can.create_line(xyfi, fill="Black")
        time.sleep(0.4)
        xyfc = 346,365,468,450
        can.create_line(xyfc, fill="Black")
        xyfe = 346,365,383,425
        can.create_line(xyfe, fill="Black")
        time.sleep(0.4)
        xygd = 272,337,231,399
        can.create_line(xygd, fill="Black")
        xyhd = 216,333,231,399
        can.create_line(xyhd, fill="Black")
        time.sleep(0.4)
        xyeb = 383,425,255,450
        can.create_line(xyeb, fill="Black")
        xyec = 383,425,468,450
        can.create_line(xyec, fill="Black")
        time.sleep(0.4)
        xyda = 231,399,132,450
        can.create_line(xyda, fill="Black")
        xydb = 231,399,255,450
        can.create_line(xydb, fill="Black")
        time.sleep(0.4)
        xydf = 231,399,346,365
        can.create_line(xydf, fill="Black")
        xyfb = 346,365,255,450
        can.create_line(xyfb, fill="Black")
        time.sleep(0.4)

        can.create_polygon(278,502,279,450,302,470,fill="#421C11")
        time.sleep(0.4)
        can.create_polygon(279,450,302,470,324,450,fill="#7D4C2E")
        time.sleep(0.4)
        can.create_polygon(302,470,324,450,323,502,fill="#93533A")
        time.sleep(0.4)
        can.create_polygon(323,502,302,470,278,502,fill="#712F15")
        time.sleep(0.4)
        can.create_polygon(132,450,255,450,231,399,fill="#0D790A")
        time.sleep(0.4)
        can.create_polygon(255,450,231,399,346,365,fill="#238A14")
        time.sleep(0.4)
        can.create_polygon(255,450,383,425,346,365,fill="#066E0B")
        time.sleep(0.4)
        can.create_polygon(383,425,255,450,468,450,fill="#004809")
        time.sleep(0.4)
        can.create_polygon(383,425,346,365,468,450,fill="#107D0A")
        time.sleep(0.4)
        can.create_polygon(384,333,346,365,468,450,fill="#469D12")
        time.sleep(0.4)
        can.create_polygon(231,399,346,365,272,337,fill="#249112")
        time.sleep(0.4)
        can.create_polygon(231,399,132,450,216,333,fill="#17840D")
        time.sleep(0.4)
        can.create_polygon(216,333,231,399,272,337,fill="#1B8D13")
        time.sleep(0.4)
        can.create_polygon(216,333,313,274,272,337,fill="#77B524")
        time.sleep(0.4)
        can.create_polygon(313,274,272,337,346,365,fill="#64B029")
        time.sleep(0.4)
        can.create_polygon(313,274,349,226,346,365,fill="#A4D152")
        time.sleep(0.4)
        can.create_polygon(349,226,346,365,384,333,fill="#86BF32")
        time.sleep(0.4)
        can.create_polygon(384,333,435,333,349,226,fill="#78B83D")
        time.sleep(0.4)
        can.create_polygon(349,226,313,274,283,184,fill="#CFE97C")
        time.sleep(0.4)
        can.create_polygon(313,274,216,333,283,184,fill="#A0D94A")
        time.sleep(0.4)
        can.create_polygon(164,333,216,333,207,281,fill="#A0DA47")
        time.sleep(0.4)
        can.create_polygon(216,333,207,281,283,184,fill="#B3E051")
        time.sleep(0.4)
        can.create_polygon(252,226,202,226,283,184,fill="#A4DB4A")
        time.sleep(0.4)
        can.create_polygon(202,226,283,184,300,88,fill="#E0EE8F")
        time.sleep(0.4)
        can.create_polygon(330,127,283,184,300,88,fill="#F0F6AE")
        time.sleep(0.4)
        can.create_polygon(349,226,283,184,330,127,fill="#E7F59D")
        time.sleep(0.4)
        can.create_polygon(349,226,375,193,330,127,fill="#D8EC8B")
        time.sleep(0.4)
        can.create_polygon(349,226,375,193,399,226,fill="#AECB61")
        time.sleep(0.4)
  
    
## MAIN ##
compteur = 0
can = Canvas(fen,width=600,height=600, bg='#769fec')
can.place(x=0,y=0)

b1=Button(fen,text='Quitter', command=fct_quitter, bg='Salmon',relief=FLAT)
b1.place(x=500,y=575)
b2=Button(fen,text='Commencer', command=demarrage, bg='LightBlue',relief=FLAT)
b2.place(x=50,y=575)
fen.mainloop()
