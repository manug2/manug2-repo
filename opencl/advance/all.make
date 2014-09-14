COPY=cp
CXX=g++
RM=rm -f
CPPFLAGS=
LDFLAGS=
LDLIBS="-framework opencl"

VPATH=src:src/wrap

TARGET_DIR="../target"

all:	hello

hello:	hello.o WrapOpenCL.o
	$(CXX) $(LDFLAGS) -o target/$@ target/hello.o target/WrapOpenCL.o -framework opencl

hello.o:	hello.cpp WrapOpenCL.h
	$(CXX) $(CPPFLAGS) -c src/hello.cpp -o target/$@
	$(COPY) src/hello.cl target/

WrapOpenCL.o:	WrapOpenCL.cpp WrapOpenCL.h
	$(CXX) $(CPPFLAGS) -c src/wrap/WrapOpenCL.cpp -o target/$@

clean:
	$(RM) target/

