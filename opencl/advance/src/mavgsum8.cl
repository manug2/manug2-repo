__kernel void mavgsum8(__global int4 *values, __global int4 *sums, __global float4 *average,
	int length, int width, int name_num)
{
	int i, j;
	int4 add_value; //A vector of 4 ints
	int loop_num = name_num / 4;
	
	for (j=0; j < loop_num; j++) {

		for(i=0; i<width;i++)
			average[i*loop_num + j] = (float4)0.0f;
	
		add_value=(int4)0;
		for (i=0; i<width; i++)
		{
			add_value += values[i*loop_num + j];
			sums[i*loop_num + j] = add_value;
		}
		
		for (i=width; i< length; i++)
		{
			add_value = add_value + values[i*loop_num + j] - values[(i-width)*loop_num + j];
			sums[i*loop_num + j] = add_value;
		}
		for (i=width; i< length;i++)
			average[i*loop_num + j] = convert_float4(sums[i*loop_num + j]) / (float4)width;
	}
}
