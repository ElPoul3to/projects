<h2>Explication de la classe NonogramConverter </h2>
Cette classe permet de créer un fichier JSON qui contient toutes les informations essentielles pour produire un Picross.

Cette classe manipule initialement des fichiers .non qui est une extension propre aux nongrammes.


Ce qu'il faut retenir c'est qu'en sortie, on a un ficheir JSON qui contient les informations propres à chaque picross:
- La largeur: *width*
- La hauteur: *height*
- Les indices des colonnes: *columns*
- Les indices des lignes: *rows*
- Un titre: *title*
- La réponse: *solve*

De plus, chaque Picross a un identifiant unique (un entier)