#!/bin/bash
echo "starting .."

cd ./target

if [ -f "${1}" ]
then
	if [ -f "${1}.cl" ]
	then
		./${1} ${1}
	else
		if [ -f "${2}.cl" ]
        	then
                	./${1} ${2}
		else
			echo "kernel file ${1}.cl not found"
		fi
	fi
else
	echo "Binary file ${1} not found"
fi


