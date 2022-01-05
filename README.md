# PacMan

Description du projet d'IA pour les jeux :

Le principe du jeu se déroule en **tour par tour**. 
L'utilisateur joue l'un des quatres fantômes et cherche à battre PacMan, joué par une IA.
Le jeu s'arrête quand PacMan réussit à manger toutes les gommes ou au bout de N tours, ou encore, au bout d'un temps donné.

L'IA du PacMan est codée par un **algorithme MinMax** et des **heuristiques** permettant de fixer l'objectif de ce dernier.

Heuristiques utilisées :
- Fantômes : plus vite PacMan est tué, plus ils ont de points ;
- PacMan : il doit **survivre** le plus longtemps possible, mais également **manger** un maximum de gommes _(les gommes restantes lui procureront un malus en fin de partie)_.



Notes pout le développement :

- PacMan et les fantômes sont des **entités** ;
- L'**IA désigne PacMan**, un **joueur réel** peut jouer l'un des **fantômes**, le reste d'entre-eux sont des **joueurs ordi** ;
- Les parties sont gérées par un **GameManager** ;
- Le plateau de jeu est stocké dans une **matrice**, qui sera initialisé par le GameManager _(attribut static, commun à chaque entité)_ ;
- Chaque case contiendra un entier correspondant à ce qui est dessus :
    - Case vide : 0
    - Mur : 1
    - PacMan : 10
    - Fantôme 1 : 11
    - Fantôme 2 : 12
    - Fantôme 3 : 13
    - Fantôme 4 : 14
    - Gomme "classique" : 20
    - PacGomme _(permettent de manger des fantômes - pendant un nombre de tours donné)_ : 21 ;
- Le score du PacMan est stocké dans sa propre classe (trouver comment définir son score...) ;
- La classe Solver permet de gérer le fonctionnement de l'IA ;
- Les fantômes sont tous aggressifs (cela permet d'avoir une IA plus performante).