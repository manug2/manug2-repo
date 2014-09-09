__kernel void mavgsum1(__global int *values, __global int *sums, __global float *average, int length, int width)
{
	int i;
	int add_value;

	for(i=0; i<width-1;i++)
		average[i] = 0.0f;

	add_value=0;
	for (i=0; i<width; i++)
	{
		add_value += values[i];
		sums[[i] = add_value;
	}
	
	average[width-1] = (float) add_value;
	for (i=width; i< length; i++)
	{
		add_value = add_value + values[i] - values[i-width];
		sums[i] = add_value;
	}
	for (i=width-1; i< length;i++)
		average[i] = (float)sums[i] / (float)width;

}
