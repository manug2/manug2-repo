#!/bin/bash
#echo "make starting for '${*}' .."
echo "make starting .."

cd ./src
gcc -o ../target/hello hello.c -framework opencl
cp *.cl ../target/
cd ..

echo "make completed!"
