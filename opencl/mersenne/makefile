COPY=cp
CXX=gcc
RM=rm -f
CPPFLAGS=
LDFLAGS=
LDLIBS="-framework opencl"

VPATH=src

all:	mt

mt:	mt.c
	$(CXX) $(CPPFLAGS) -c src/$@.c -o ../target/$@.o
	$(COPY) src/mt.cl ../target/
	$(CXX) $(LDFLAGS) -o ../target/$@ ../target/$@.o -framework opencl

clean:
	$(RM) ../target/*

