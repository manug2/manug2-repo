#include <iostream>
#include <stdio.h>
#include <stdlib.h>

#include "WrapOpenCL.h"

using namespace std;

WrapOpenCL::WrapOpenCL(char* kernelName) {
	memSize = 128;
	//Check null
	this->kernelName = kernelName;

	this->kernelFile = (char*) malloc(100*sizeof(char));
	sprintf(this->kernelFile, "%s.cl", kernelName);
	initNULL();
}

WrapOpenCL::WrapOpenCL(int memSize, char* kernelName) {
	//if(memSize < 1)
	this->memSize = memSize;
	//Check null
	this->kernelName = kernelName;

	this->kernelFile = (char*) malloc(100*sizeof(char));
	sprintf(this->kernelFile, "%s.cl", kernelName);
	initNULL();
}

void WrapOpenCL::initNULL() {
	device_id 	= NULL;
        context 	= NULL;
        command_queue 	= NULL;
        program 	= NULL;
        platform_id 	= NULL;
	ev 		= NULL;
	num_of_memobjs	= 0;
}

void WrapOpenCL::print() {
	cout << endl << "File : " << this->kernelFile << ", Kernel : " << this->kernelName << endl;
}

void WrapOpenCL::setKernelArg(int argn, size_t arg_size, void * arg_ptr) {
	cl_int ret;
	ret = clSetKernelArg(this->kernel, argn, sizeof(cl_mem), arg_ptr);
#ifdef DEBUG
	cout << endl << "Set kernel arg #" << argn << " : " << ret;
#endif
}

cl_mem WrapOpenCL::createBuffer(cl_mem_flags flags, size_t buf_size, void * host_ptr) {
	cl_int ret;
	cl_mem memobj;
	memobj = clCreateBuffer(this->context, flags, buf_size, NULL, &ret);
	this->memobjs[num_of_memobjs] = memobj;
	num_of_memobjs++;
#ifdef DEBUG
	cout << endl << "Create Buffer : " << ret;
#endif
	return memobj;
}

void WrapOpenCL::readBuffer(cl_mem memobj, cl_bool blocking_write, size_t offset, size_t buf_size, void * ptr, cl_uint num_of_events_in_wait_list, cl_event *event_wait_list) {

	cl_int ret;
	clEnqueueReadBuffer(this->command_queue, memobj, blocking_write, offset, buf_size, ptr, num_of_events_in_wait_list, event_wait_list, &this->ev);
#ifdef DEBUG
	cout << endl << "Read Buffer : " << ret;
#endif
} 

void WrapOpenCL::writeBuffer(cl_mem memobj, cl_bool blocking_write, size_t offset, size_t buf_size, void * ptr, cl_uint num_of_events_in_wait_list, cl_event *event_wait_list) {

	cl_int ret;
	clEnqueueWriteBuffer(this->command_queue, memobj, blocking_write, offset, buf_size, ptr, num_of_events_in_wait_list, event_wait_list, &this->ev);
#ifdef DEBUG
	cout << endl << "Read Buffer : " << ret;
#endif
} 

void WrapOpenCL::invoke() {
	cl_int ret;
	ret = clEnqueueTask(command_queue, kernel, 0, NULL, &this->ev);
#ifdef DEBUG
	cout << endl << "Enqueue Task : " << ret;
#endif
}

void WrapOpenCL::invoke(int global_size, int local_size) {

	//Check for non-zero, non-neg numbers

	cl_int ret;
	size_t global_item_size = global_size;
	size_t local_item_size = local_size;
	ret = clEnqueueNDRangeKernel(this->command_queue, this->kernel, 1, NULL
		, &global_item_size, &local_item_size, 0, NULL, &this->ev);
}

int WrapOpenCL::getMemSize() {
	return this->memSize;
}

void WrapOpenCL::initCL() {
	initCL(0);
}

void WrapOpenCL::initCL(int queueSetting) {

	cl_int ret;
	size_t source_size;

	char *source_str = this->readSourceCL(source_size);
        printf("Using kernel name [%s]\n", this->kernelName);
	
	/*Get platform and device info*/
	ret = clGetPlatformIDs(1, &platform_id, &ret_num_platforms);
#ifdef DEBUG
	cout << endl << "Get Plaform ID : " << ret;
#endif

	ret = clGetDeviceIDs(platform_id, CL_DEVICE_TYPE_DEFAULT, 1 ,&device_id, &ret_num_devices);
#ifdef DEBUG
	cout << endl << "Get Device ID : " << ret;
#endif
	
	this->context = clCreateContext(NULL, 1, &device_id, NULL, NULL, &ret);
#ifdef DEBUG
	cout << endl << "Create Context : " << ret;
#endif

	this->command_queue = clCreateCommandQueue(context, device_id, queueSetting, &ret);
#ifdef DEBUG
	cout << endl << "Create Command Queue : " << ret;
#endif

	this->program = clCreateProgramWithSource(context, 1, (const char**) &source_str, (const size_t*) &source_size, &ret);
#ifdef DEBUG
	cout << endl << "Create Program : " << ret;
#endif

	ret = clBuildProgram(program, 1, &device_id, NULL, NULL, NULL);
#ifdef DEBUG
	cout << endl << "Build Program : " << ret;
#endif

	this->kernel = clCreateKernel(program, this->kernelName, &ret);
#ifdef DEBUG
	cout << endl << "Create Kernel : " << ret;
#endif

	free(source_str);

}

char* WrapOpenCL::readSourceCL(size_t &source_size) {

        char *source_str;
        printf("Using kernel file [%s]\n", this->kernelFile);

        /*load the source code containing the kernel*/
        FILE *fp;
        fp = fopen (this->kernelFile, "r");
        if (!fp) {
        	fprintf(stderr, "failed to load kernel.\n");
        	exit(1);
        }

        source_str = (char*)malloc(MAX_SOURCE_SIZE);
        source_size = fread(source_str, 1, MAX_SOURCE_SIZE, fp);
        fclose(fp);

	return source_str;
}

char * WrapOpenCL::parseArgument(int argc, char* argv[]) {
	if(argc < 2)
	{
		printf("Expecting one argument!\n");
		exit(1);
	}
	if(argv[1]==NULL)
	{
		printf("Expecting one non-null argument!\n");
		exit(1);
	}
	
	char * progName = argv[1];
	return progName;
}


WrapOpenCL::~WrapOpenCL() {
#ifdef DEBUG
	printf("Inside ~WrapOpenCL()..\n");
#endif

	cl_int ret;
	ret=clFlush(this->command_queue);
	ret=clFinish(this->command_queue);
	ret=clReleaseKernel(this->kernel);
	ret=clReleaseProgram(this->program);

	for (int i=0; i < num_of_memobjs; i++) {
		if(this->memobjs[i] != NULL)
			ret=clReleaseMemObject(this->memobjs[i]);
	}

	ret=clReleaseCommandQueue(this->command_queue);
	ret=clReleaseContext(this->context);

	//delete this->kernelFile;
	
}

cl_kernel WrapOpenCL::createAdditionalKernel()
{
	cl_kernel k;
	cl_int ret;
	k = clCreateKernel(this->program, this->kernelName, &ret);
#ifdef DEBUG
	cout << endl << "Create Additional Kernel : " << ret;
#endif
	return k;
}
