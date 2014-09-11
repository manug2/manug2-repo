#!/bin/bash
#echo "make starting for '${*}' .."
echo "make starting .."

cd ./target

if [ -f "../src/${1}.cpp" ]
then
	srcfile=${1}
	echo "making source file = ${srcfile}.cpp .."

	gcc -c ../src/wrap/WrapOpenCL.cpp
	gcc -c ../src/${srcfile}.cpp
	gcc -o ${srcfile} WrapOpenCL.o ${srcfile}.o -framework opencl
	#gcc -o ${srcfile} ${srcfile}.o WrapOpenCL.o -framework opencl
	cp ../src/*.cl .
	cd ..
	echo "make completed!"
else
	echo "Source file ${1}.cpp not found"
fi


