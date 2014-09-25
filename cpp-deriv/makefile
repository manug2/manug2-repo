COPY=cp
CXX=g++
RM=rm -f

VPATH=src

all:	MonteCarloMain3 MonteCarloMain2 MonteCarloMain1 MonteCarloMain

MonteCarloMain3:	MonteCarloMain2.cpp Random1 DoubleDigital PayOff2 MonteCarlo2 MinMax
	${CXX} -c src/$@.cpp -o target/$@.o
	${CXX} -o target/$@ target/$@.o target/Random1 target/DoubleDigital target/PayOff2 target/MonteCarlo2 target/MinMax
DoubleDigital:	DoubleDigital.cpp DoubleDigital.h PayOff2.h
	${CXX} -c src/$@.cpp -o target/$@

MonteCarloMain2:	MonteCarloMain2.cpp Random1 PayOff2 MonteCarlo2 MinMax
	${CXX} -c src/$@.cpp -o target/$@.o
	${CXX} -o target/$@ target/$@.o target/Random1 target/PayOff2 target/MonteCarlo2 target/MinMax
MonteCarlo2:	MonteCarlo2.cpp Random1.h PayOff2
	${CXX} -c src/$@.cpp -o target/$@
PayOff2:	PayOff2.cpp PayOff2.h MinMax
	${CXX} -c src/$@.cpp -o target/$@
	

MonteCarloMain1:	MonteCarloMain1.cpp Random1 PayOff1 MonteCarlo1
	${CXX} -c src/$@.cpp -o target/$@.o
	${CXX} -o target/$@ target/$@.o target/Random1 target/PayOff1 target/MonteCarlo1 target/MinMax
MonteCarlo1:	MonteCarlo1.cpp Random1 PayOff1
	${CXX} -c src/$@.cpp -o target/$@

MonteCarloMain:	MonteCarloMain.cpp Random1 PayOff1 MinMax
	${CXX} -c src/$@.cpp -o target/$@.o
	${CXX} -o target/$@ target/$@.o target/Random1 target/PayOff1 target/MinMax

PayOff1:	PayOff1.cpp PayOff1.h MinMax
	${CXX} -c src/$@.cpp -o target/$@

MinMax:	MinMax.cpp MinMax.h
	${CXX} -c src/$@.cpp -o target/$@

Random1:	Random1.cpp Random1.h
	${CXX} -c src/$@.cpp -o target/$@

clean:
	${RM} target/*

