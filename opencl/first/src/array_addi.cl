__kernel void array_addi(__global int *result, __global int* n1, __global int* n2)
{
for (int i=0; i < 5; i++)
{
*(result+i) = *(n1+i) + *(n2+i);
}
}
