__kernel void mavgsum4(__global int4 *values, __global int4 *sums, __global float4 *average, int length, int width)
{
	int i;
	int4 add_value; //A vector of 4 ints

	for(i=0; i<width;i++)
		average[i] = (float4)0.0f;

	add_value=(int4)0;
	for (i=0; i<width; i++)
	{
		add_value += values[i];
		sums[i] = add_value;
	}
	
	for (i=width; i< length; i++)
	{
		add_value = add_value + values[i] - values[i-width];
		sums[i] = add_value;
	}
	for (i=width; i< length;i++)
		average[i] = convert_float4(sums[i]) / (float4)width;

}
