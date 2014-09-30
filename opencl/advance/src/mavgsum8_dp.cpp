#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include "wrap/WrapOpenCL.h"

#define NAME_NUM (8)
#define WINDOW_SIZE (10)

int stock_array1[] = {
	#include "../data/stock_array8.txt"
};

using namespace std;

/*******************
DATA PARALLEL example
***********************/

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
	int data_num = sizeof(stock_array1) / (NAME_NUM * sizeof(stock_array1[0]));
	int window_num = (int) WINDOW_SIZE;
	int name_num = (int) NAME_NUM;
	int point_num = data_num * name_num;
	int i, j;

for (int i=0; i<data_num; i++) {
	cout << endl << "result [" << i << "]: ";
	for (int j=0; j < name_num; j++)
		cout << stock_array1[i*name_num + j] << "\t";
}
	cout <<endl;

	printf("# of Records = [%d], # of windows = [%d]\n", data_num, window_num);
	int *sums;
	result = (float*) malloc(point_num * sizeof(float)); //data rows * 4 items per row
	sums = (int*) malloc(point_num * sizeof(int));


	memobj = wrapper.createBuffer(CL_MEM_READ_WRITE, point_num * sizeof(int), NULL);
	memobj1 = wrapper.createBuffer(CL_MEM_WRITE_ONLY, point_num * sizeof(int), NULL);
	memobj2 = wrapper.createBuffer(CL_MEM_WRITE_ONLY, point_num * sizeof(float), NULL);

	wrapper.writeBuffer(memobj, CL_TRUE, 0, point_num * sizeof(int), stock_array1, 0, NULL);

	wrapper.setKernelArg(0, sizeof(cl_mem), (void*) &memobj);
	wrapper.setKernelArg(1, sizeof(cl_mem), (void*) &memobj1);
	wrapper.setKernelArg(2, sizeof(cl_mem), (void*) &memobj2);
	wrapper.setKernelArg(3, sizeof(int), (void*) &data_num);
	wrapper.setKernelArg(4, sizeof(int), (void*) &window_num);
	wrapper.setKernelArg(5, sizeof(int), (void*) &name_num);

	wrapper.invoke(2, 1);
	cout << endl << "after invoking kernel..";
	wrapper.readBuffer(memobj1, CL_TRUE, 0, point_num * sizeof(int), sums, 0, NULL);
	wrapper.readBuffer(memobj2, CL_TRUE, 0, point_num * sizeof(float), result, 0, NULL);

for (int i=0; i<data_num; i++) {
	cout << endl << "result [" << i << "]: ";
	for (int j=0; j < name_num; j++)
		cout << result[i*name_num + j] << "\t";
}

free(sums);
free(result);
        /* ***************************************
        Code specific to problem at hand ENDS
        *************************************** */

printf("\n");

return 0;
}
