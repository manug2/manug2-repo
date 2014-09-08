#!/bin/bash
#echo "make starting for '${*}' .."
echo "make starting .."

cd ./src

if [ -f "${1}.c" ]
then
	srcfile=${1}
	echo "making source file = ${srcfile}.c .."

	gcc -o ../target/${srcfile} ${srcfile}.c -framework opencl
	cp *.cl ../target/
	cd ..
	echo "make completed!"
else
	echo "Source file ${1}.c not found"
fi


