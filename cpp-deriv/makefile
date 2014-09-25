COPY=cp
CXX=g++
RM=rm -f

VPATH=src

all:	MonteCarlo2Main

MonteCarlo2Main:	MonteCarlo2Main.cpp MonteCarlo2 Random1.o PayOff.o
	${CXX} -c src/$@.cpp -o target/$@.o
	${CXX} -o target/$@ target/$@.o target/Random1.o target/PayOff.o target/MonteCarlo2 target/MinMax.o

MonteCarlo2:	MonteCarlo2.cpp Random1.o PayOff.o
	${CXX} -c src/$@.cpp -o target/$@

PayOff.o:	PayOff1.cpp PayOff1.h MinMax.o
	${CXX} -c src/PayOff1.cpp -o target/$@

MonteCarlo1:	MonteCarlo1.cpp Random1.o
	${CXX} -c src/$@.cpp -o target/$@.o
	${CXX} -o target/$@ target/$@.o target/Random1.o

MinMax.o:	MinMax.cpp MinMax.h
	${CXX} -c src/MinMax.cpp -o target/$@

Random1.o:	Random1.cpp Random1.h
	${CXX} -c src/Random1.cpp -o target/$@

clean:
	${RM} target/*

