# Java Game Of Life
El juego de la vida es un autómata celular diseñado por el matemático británico John Horton Conway en 1970. Se trata de un juego de cero jugadores, lo que quiere decir que su evolución está determinada por el estado inicial y no necesita ninguna entrada de datos posterior.
## Contenido
* [Reglas](#reglas)
* [GUI](#gui)
    * [Controles](#controles)
        * [Guardando y cargando patrones](#guardando-y-cargando-patrones)
    * [Rejilla](#rejilla)
* [Ejemplo](#ejemplo)
## Reglas
El "tablero de juego" es una malla plana formada por cuadrados (las "células") que se extiende por el infinito en todas las direcciones. Por tanto, cada célula tiene 8 células "vecinas", que son las que están próximas a ella, incluidas las diagonales. Las células tienen dos estados: están "vivas" o "muertas" (o "encendidas" y "apagadas"). El estado de las células evoluciona a lo largo de unidades de tiempo discretas (se podría decir que por turnos). El estado de todas las células se tiene en cuenta para calcular el estado de las mismas al turno siguiente. Todas las células se actualizan simultáneamente en cada turno, siguiendo estas reglas:

1. Una célula muerta con exactamente 3 células vecinas vivas "nace" (es decir, al turno siguiente estará viva).
2. Una célula viva con 2 o 3 células vecinas vivas sigue viva, en otro caso muere (por "soledad" o "superpoblación").

Extraído de [Wikipedia](https://es.wikipedia.org/wiki/Juego_de_la_vida)
## GUI
![GUI](/images/gen-pop2.png)
### Controles
![Controles](/images/contro-gui.png)
#### Guardando y cargando patrones
![Guardar archivo](/images/save-dialog.png)
![Abrir archivo](/images/open-dialog.png)
![Archivo cargado](/images/file-opened.png)
### Rejilla
![Rejilla](/images/grid-gui.png)
## Ejemplo
[ejemplos](https://github.com/crixodia/java-game-of-life/blob/master/examples/)

[osc.jglf](https://github.com/crixodia/java-game-of-life/blob/master/examples/osc.jglf)

![Grid Gif](/images/grid-gif.gif)