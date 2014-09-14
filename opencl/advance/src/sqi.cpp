#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include "wrap/WrapOpenCL.h"

using namespace std;

int main(int argc, char *argv[]) {

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

	int sum=0;
	cl_int to_sq = 3;
	cl_mem memobj = wrapper.createBuffer(CL_MEM_READ_WRITE, sizeof(int), NULL);
	
	wrapper.setKernelArg(0, sizeof(cl_mem), (void*) &memobj);
	wrapper.setKernelArg(1, sizeof(int), (void*) &to_sq);
	wrapper.invoke();
	wrapper.readBuffer(memobj, CL_TRUE, 0, sizeof(int), &sum, 0, NULL);
	
	printf ("Square of [%d] = [%d]", to_sq, sum);

	/* ***************************************
  	Code specific to problem at hand ENDS
	*************************************** */

printf("\n");

return 0;
}
