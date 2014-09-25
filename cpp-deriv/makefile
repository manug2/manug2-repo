COPY=cp
CXX=g++
RM=rm -f

VPATH=src

all:	MonteCarlo1

MonteCarlo1:	MonteCarlo1.cpp Random1.o
	${CXX} -c src/$@.cpp -o target/$@.o
	${CXX} -o target/$@ target/$@.o target/Random1.o

Random1.o:	Random1.cpp Random1.h
	${CXX} -c src/Random1.cpp -o target/$@

clean:
	${RM} target/*

