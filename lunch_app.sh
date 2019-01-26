#!/usr/bin/env bash

echo mode console
#java -jar projet_cpoo_julia_set.jar

mkdir -p bin
javac -d bin -classpath .:lib/jfoenix-8.0.7.jar:lib/fontawesomefx-8.9.jar src/julia_set_paquage/Main.java src/julia_set_paquage/controller/Controller_interface_main.java src/julia_set_paquage/model/Complexe.java src/julia_set_paquage/model/Fractal.java src/julia_set_paquage/model/Julia.java src/julia_set_paquage/model/Mandelbrot.java
cp -R src/julia_set_paquage/view bin/julia_set_paquage/
cd bin
java -classpath .:../lib/jfoenix-8.0.7.jar:../lib/fontawesomefx-8.9.jar julia_set_paquage.Main
