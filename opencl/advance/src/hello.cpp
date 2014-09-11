#include <stdlib.h>
#include <stdio.h>
#include "wrap/WrapOpenCL.h"


int main (int argc, char* argv[]) {

#ifdef DEBUG
printf("Argument count = [%d]\n", argc);
#endif

char *pn;

pn = WrapOpenCL::parseArgument(argc, argv);
printf("Using kernel file [%s.cl], with kernel name [%s]\n", pn, pn);

printf ("\nBye!!\n");


}
