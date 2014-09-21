COPY=cp
CXX=g++
RM=rm -f
CPPFLAGS=
LDFLAGS=
BOOST_ROOT=/Users/ManuGarg/DeveloperSoftware/boost_1_56_0
LDLIBS="-I /Users/ManuGarg/DeveloperSoftware/boost_1_56_0"

VPATH=src

TARGET_DIR="../target"

all:	multiplier

multiplier:	multiplier.cpp
	$(CXX) $(CPPFLAGS) -c src/$@.cpp -o target/$@.o -I ${BOOST_ROOT}
	$(CXX) $(LDFLAGS) -o target/$@ target/$@.o ${LDLIBS}

clean:
	$(RM) target/*

