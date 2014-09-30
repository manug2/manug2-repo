COPY=cp
CXX=g++
RM=rm -f
CPPFLAGS=
LDFLAGS=
LDLIBS="-framework opencl"

VPATH=src:src/wrap

TARGET_DIR="../target"

all:	hello sqi mavgsum1 mavgsum4

mavgsum4:	mavgsum4.cpp WrapOpenCL.o
	$(CXX) $(CPPFLAGS) -c src/mavgsum4.cpp -o target/$@.o
	$(COPY) src/mavgsum4.cl target/
	$(CXX) $(LDFLAGS) -o target/$@ target/mavgsum4.o target/WrapOpenCL.o -framework opencl

mavgsum1:	mavgsum1.cpp WrapOpenCL.o
	$(CXX) $(CPPFLAGS) -c src/mavgsum1.cpp -o target/$@.o
	$(COPY) src/mavgsum1.cl target/
	$(CXX) $(LDFLAGS) -o target/$@ target/mavgsum1.o target/WrapOpenCL.o -framework opencl

sqi:	sqi.cpp WrapOpenCL.o
	$(CXX) $(CPPFLAGS) -c src/sqi.cpp -o target/$@.o
	$(COPY) src/sqi.cl target/
	$(CXX) $(LDFLAGS) -o target/$@ target/sqi.o target/WrapOpenCL.o -framework opencl

hello:	hello.cpp WrapOpenCL.o
	$(CXX) $(CPPFLAGS) -c src/hello.cpp -o target/$@.o
	$(COPY) src/hello.cl target/
	$(CXX) $(LDFLAGS) -o target/$@ target/hello.o target/WrapOpenCL.o -framework opencl

WrapOpenCL.o:	WrapOpenCL.cpp WrapOpenCL.h
	$(CXX) $(CPPFLAGS) -c src/wrap/WrapOpenCL.cpp -o target/$@

clean:
	$(RM) target/*

