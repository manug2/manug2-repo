__kernel void fft(__global int *result, __global int* n1, __global int* n2)
{
int i = get_global_id(0);
*(result+i) = *(n1+i) + *(n2+i);
}
