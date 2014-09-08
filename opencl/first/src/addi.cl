__kernel void addi(__global int *result, __global int* n1, __global int* n2)
{
*result = (*n1) + (*n2);
}
