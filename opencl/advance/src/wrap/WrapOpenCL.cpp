#include <stdio.h>
#include <stdlib.h>

#include "WrapOpenCL.h"

WrapOpenCL::WrapOpenCL(const char* kernelName) {
	memSize = 128;
	//Check null
	this->kernelName = kernelName;

	this->kernelFile = (char*) malloc(100*sizeof(char));
	sprintf(this->kernelFile, "%s.cl", kernelName);
	this->kernelFile = (char*) malloc(100*sizeof(char));
}

WrapOpenCL::WrapOpenCL(int memSize, const char* kernelName) {
	//if(memSize < 1)
	this->memSize = memSize;
	//Check null
	this->kernelName = kernelName;

	this->kernelFile = (char*) malloc(100*sizeof(char));
	sprintf(this->kernelFile, "%s.cl", kernelName);
}

void WrapOpenCL::invoke() {
	cl_int ret;
	ret = clEnqueueTask(command_queue, kernel, 0, NULL, &this->ev);
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

	cl_int ret;
        char *source_str;
        size_t source_size;
	
	printf("Using kernel file [%s], with kernel name [%s]\n", this->kernelFile, this->kernelName);

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
	
	
	/*Get platform and device info*/
	ret = clGetPlatformIDs(1, &platform_id, &ret_num_platforms);
	ret = clGetDeviceIDs(platform_id, CL_DEVICE_TYPE_DEFAULT, 1 ,&device_id, &ret_num_devices);
	
	this->context = clCreateContext(NULL, 1, &device_id, NULL, NULL, &ret);
	this->command_queue = clCreateCommandQueue(context, device_id, 0, &ret);
	this->program = clCreateProgramWithSource(context, 1, (const char**) &source_str, (const size_t*) &source_size, &ret);
	ret = clBuildProgram(program, 1, &device_id, NULL, NULL, NULL);
	this->kernel = clCreateKernel(program, this->kernelName, &ret);

	//this-> memobj = (cl_mem*) malloc( ((int) MAX_MEM_BUFFERS) * sizeof(cl_mem));
	//for (int i=0; i < (int) MAX_MEM_BUFFERS); i++)
		//this->memobj[i] = NULL;

	free(source_str);

}

void WrapOpenCL::freeResources() {
	cl_int ret;
	ret=clFlush(this->command_queue);
	ret=clFinish(this->command_queue);
	ret=clReleaseKernel(this->kernel);
	ret=clReleaseProgram(this->program);

	/*
	for (int i=0; i < (int) MAX_MEM_BUFFERS); i++) {
		if(this->memobj[i] != NULL)
			ret=clReleaseMemObject(this->memobj[i]);
	}
	*/

	ret=clReleaseCommandQueue(this->command_queue);
	ret=clReleaseContext(this->context);
	
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
	printf("Using kernel file [%s.cl], with kernel name [%s]\n", progName, progName);

	return progName;
}

