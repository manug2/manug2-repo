#include <stdio.h>
#include <stdlib.h>

#ifdef __APPLE__
#include <OpenCL/opencl.h>
#else
#include <CL/cl.h>
#endif

#define MEM_SIZE (5)
#define MAX_SOURCE_SIZE (0x100000)

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
cl_program program = NULL;
cl_kernel kernel = NULL;
cl_platform_id platform_id = NULL;
cl_uint ret_num_devices;
cl_uint ret_num_platforms;
cl_int ret;

float *result;
int i;
cl_mem image, out;
cl_bool support;
cl_image_format fmt;
int num_out = 9;

FILE *fp;
char *source_str;
size_t source_size, r_size;
int mem_size = sizeof(cl_float4) * num_out;

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

result = (float*) malloc(mem_size);
//check image support
clGetDeviceInfo(device_id, CL_DEVICE_IMAGE_SUPPORT, sizeof(support), &support, &r_size);
if (support != CL_TRUE) {
	puts("image not supported");
	return 1;
}

command_queue = clCreateCommandQueue(context, device_id, 0, &ret);
printf("queue ret = %d\n", ret);

out = clCreateBuffer(context, CL_MEM_READ_WRITE, mem_size, NULL, &ret);
printf("create buffer ret = %d\n", ret);

fmt.image_channel_order = CL_R;
fmt.image_channel_data_type = CL_FLOAT;

image = clCreateImage2D(context, CL_MEM_READ_ONLY, &fmt, 4, 4, 0, 0, NULL);

size_t origin[] = {0,0,0};
size_t region[] = {4,4,1};
float data[] = {
	10,20,30,40,
	10,20,30,40,
	10,20,30,40,
	10,20,30,40
};

clEnqueueWriteImage(command_queue, image, CL_TRUE, origin, region, 4*sizeof(float), 0, data, 0, NULL, NULL);

program = clCreateProgramWithSource(context, 1, (const char**) &source_str, (const size_t*) &source_size, &ret);
ret = clBuildProgram(program, 1, &device_id, NULL, NULL, NULL);
printf("build program ret = %d\n", ret);
kernel = clCreateKernel(program, progName, &ret);
printf("create kernel ret = %d\n", ret);

//How to set int arguments?
ret = clSetKernelArg(kernel, 0, sizeof(cl_mem), (void*) &image);
printf("arg 0 ret = %d\n", ret);
ret = clSetKernelArg(kernel, 1, sizeof(cl_mem), (void*) &out);
printf("arg 1 ret = %d\n", ret);

cl_event ev;
ret = clEnqueueTask(command_queue, kernel, 0, NULL, &ev);

//How to read a int?
ret = clEnqueueReadBuffer(command_queue, out, CL_TRUE, 0, mem_size, result, 0, NULL, NULL);
for(int i=0; i < num_out; i++) {
	printf("%f,%f,%f,%f\n", result[i*4+0], result[i*4+1], result[i*4+2], result[i*4+3]);
}

ret=clFlush(command_queue);
ret=clFinish(command_queue);
ret=clReleaseKernel(kernel);
ret=clReleaseProgram(program);

ret=clReleaseMemObject(out);
ret=clReleaseMemObject(image);

ret=clReleaseCommandQueue(command_queue);
ret=clReleaseContext(context);

free(source_str);

printf("\n");

return 0;
}
