#include <iostream>
#include <stdlib.h>
#include <stdio.h>
#include "wrap/WrapOpenCL.h"

using namespace std;

int main (int argc, char* argv[]) {

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

char string[wrapper.getMemSize()];
cl_mem memobj = wrapper.createBuffer(CL_MEM_READ_WRITE, wrapper.getMemSize() * sizeof(char), NULL);

wrapper.setKernelArg(0, sizeof(cl_mem), (void*) &memobj);
wrapper.invoke();
wrapper.readBuffer(memobj, CL_TRUE, 0, wrapper.getMemSize() * sizeof(char), string, 0, NULL);

cout << endl << "Result from kernel : " << string;

/* ***************************************
  Code specific to problem at hand ENDS
*************************************** */

cout << endl << "Bye!!" << endl;

}
