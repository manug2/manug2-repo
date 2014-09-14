#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include "wrap/WrapOpenCL.h"


#define WINDOW_SIZE (10)

int stock_array1[] = {
	#include "../data/stock_array1.txt"
};

int main(int argc, char *argv[])
{

#ifdef DEBUG
printf("Argument count = [%d]\n", argc);
#endif

        char *pn;

        pn = WrapOpenCL::parseArgument(argc, argv);

        WrapOpenCL wrapper(pn);
        wrapper.initCL();

        /* ***************************************
        Code specific to problem at hand STARTS
        *************************************** */

	cl_mem memobj, memobj1, memobj2;
	float *result;
	int data_num = sizeof(stock_array1) / sizeof(stock_array1[0]);
	int window_num = (int) WINDOW_SIZE;

	printf("# of Records = [%d], # of windows = [%d]\n", data_num, window_num);
	int *sums;
	result = (float*) malloc(data_num * sizeof(float));
	sums = (int*) malloc(data_num * sizeof(int));


	memobj = wrapper.createBuffer(CL_MEM_READ_WRITE, data_num * sizeof(int), NULL);
	memobj1 = wrapper.createBuffer(CL_MEM_WRITE_ONLY, data_num * sizeof(int), NULL);
	memobj2 = wrapper.createBuffer(CL_MEM_WRITE_ONLY, data_num * sizeof(float), NULL);

	wrapper.writeBuffer(memobj, CL_TRUE, 0, data_num * sizeof(int), stock_array1, 0, NULL);

	wrapper.setKernelArg(0, sizeof(cl_mem), (void*) &memobj);
	wrapper.setKernelArg(1, sizeof(cl_mem), (void*) &memobj1);
	wrapper.setKernelArg(2, sizeof(cl_mem), (void*) &memobj2);
	wrapper.setKernelArg(3, sizeof(int), (void*) &data_num);
	wrapper.setKernelArg(4, sizeof(int), (void*) &window_num);

	wrapper.invoke();
	wrapper.readBuffer(memobj1, CL_TRUE, 0, data_num * sizeof(int), sums, 0, NULL);
	wrapper.readBuffer(memobj2, CL_TRUE, 0, data_num * sizeof(float), result, 0, NULL);

for (int i=0; i<data_num; i++)
	printf("i = %d \t value = %d \t sum = %d \t average = %f\n", i, stock_array1[i], sums[i], result[i]);

free(sums);
free(result);
        /* ***************************************
        Code specific to problem at hand ENDS
        *************************************** */

printf("\n");

return 0;
}
