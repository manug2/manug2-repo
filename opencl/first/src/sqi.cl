__kernel void sqi(__global int *result, __global int* ts)
{
*result = (*ts) * (*ts);
}
