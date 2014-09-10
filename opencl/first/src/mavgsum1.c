#include <stdio.h>
#include <stdlib.h>

#ifdef __APPLE__
#include <OpenCL/opencl.h>
#else
#include <CL/cl.h>
#endif

#define MEM_SIZE (5)
#define MAX_SOURCE_SIZE (0x100000)

#define WINDOW_SIZE (10)

int stock_array1[] = {
	#include "stock_array1.txt"
};

int main(int argc, char *argv[])
{

#ifdef DEBUG
printf("Argument count = [%d]\n", argc);
#endif

if(argc!=2)
{
printf("Expecting one argument!\n");
exit(1);
}
if(argv[1]==NULL)
{
printf("Expecting one non-null argument!\n");
exit(1);
}

char *progName = argv[1];
char fileName[100];
sprintf(fileName, "./target/%s.cl",progName);
printf("Using kernel file [%s], with kernel name [%s]\n", fileName, progName);

cl_device_id device_id = NULL;
cl_context context = NULL;
cl_command_queue command_queue = NULL;
cl_mem memobj = NULL;
cl_mem memobj1 = NULL, memobj2 = NULL, memobj3;
cl_program program = NULL;
cl_kernel kernel = NULL;
cl_platform_id platform_id = NULL;
cl_uint ret_num_devices;
cl_uint ret_num_platforms;
cl_int ret;

float *result;
int data_num = sizeof(stock_array1) / sizeof(stock_array1[0]);
int window_num = (int) WINDOW_SIZE;

printf("# of Records = [%d], # of windows = [%d]\n", data_num, window_num);

int *sums;

result = (float*) malloc(data_num * sizeof(float));
sums = (int*) malloc(data_num * sizeof(int));

FILE *fp;
char *source_str;
size_t source_size;

/*load the source code containing the kernel*/
fp = fopen (fileName, "r");
if (!fp) {
fprintf(stderr, "failed to load kernel.\n");
exit(1);
}
source_str = (char*)malloc(MAX_SOURCE_SIZE);
source_size = fread(source_str, 1, MAX_SOURCE_SIZE, fp);
fclose(fp);

/*Get platform and device info*/
ret = clGetPlatformIDs(1, &platform_id, &ret_num_platforms);
printf("ret_num_platforms = %d\n", ret_num_platforms);
ret = clGetDeviceIDs(platform_id, CL_DEVICE_TYPE_DEFAULT, 1 ,&device_id, &ret_num_devices);
printf("ret_num_platforms = %d\n", ret_num_platforms);

context = clCreateContext(NULL, 1, &device_id, NULL, NULL, &ret);
command_queue = clCreateCommandQueue(context, device_id, 0, &ret);
printf("queue ret = %d\n", ret);

memobj = clCreateBuffer(context, CL_MEM_READ_WRITE, data_num * sizeof(int), NULL, &ret);
printf("create buffer ret = %d\n", ret);
memobj1 = clCreateBuffer(context, CL_MEM_WRITE_ONLY, data_num * sizeof(int), NULL, &ret);
printf("create buffer ret = %d\n", ret);
memobj2 = clCreateBuffer(context, CL_MEM_WRITE_ONLY, data_num * sizeof(float), NULL, &ret);
printf("create buffer ret = %d\n", ret);

ret = clEnqueueWriteBuffer(command_queue, memobj, CL_TRUE, 0, data_num * sizeof(int), stock_array1, 0, NULL, NULL);

program = clCreateProgramWithSource(context, 1, (const char**) &source_str, (const size_t*) &source_size, &ret);
ret = clBuildProgram(program, 1, &device_id, NULL, NULL, NULL);
printf("build program ret = %d\n", ret);
kernel = clCreateKernel(program, progName, &ret);
printf("create kernel ret = %d\n", ret);

//How to set int arguments?
ret = clSetKernelArg(kernel, 0, sizeof(cl_mem), (void*) &memobj);
printf("arg 0 ret = %d\n", ret);
ret = clSetKernelArg(kernel, 1, sizeof(cl_mem), (void*) &memobj1);
printf("arg 1 ret = %d\n", ret);
ret = clSetKernelArg(kernel, 2, sizeof(cl_mem), (void*) &memobj2);
printf("arg 2 ret = %d\n", ret);
ret = clSetKernelArg(kernel, 3, sizeof(int), (void*) &data_num);
printf("arg 3 ret = %d\n", ret);
ret = clSetKernelArg(kernel, 4, sizeof(int), (void*) &window_num);
printf("arg 4 ret = %d\n", ret);

ret = clEnqueueTask(command_queue, kernel, 0, NULL, NULL);
printf("enqueue task ret = %d\n", ret);

ret = clEnqueueReadBuffer(command_queue, memobj1, CL_TRUE, 0, data_num * sizeof(int), sums, 0, NULL, NULL);
printf("read buffer ret = %d\n", ret);
ret = clEnqueueReadBuffer(command_queue, memobj2, CL_TRUE, 0, data_num * sizeof(float), result, 0, NULL, NULL);
printf("read buffer ret = %d\n", ret);

ret=clFlush(command_queue);
ret=clFinish(command_queue);
ret=clReleaseKernel(kernel);
ret=clReleaseProgram(program);

ret=clReleaseMemObject(memobj);
ret=clReleaseMemObject(memobj1);
ret=clReleaseMemObject(memobj2);

ret=clReleaseCommandQueue(command_queue);
ret=clReleaseContext(context);

for (int i=0; i<data_num; i++)
	printf("i = %d \t value = %d \t sum = %d \t average = %f\n", i, stock_array1[i], sums[i], result[i]);


free(source_str);
free(sums);
free(result);

printf("\n");

return 0;
}
