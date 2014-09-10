#include <stdio.h>
#include <stdlib.h>

int stock_array1[] = {
	#include "stock_array1.txt"
};

#define WINDOW_SIZE (10)


void moving_average(int *values, int *sums, float *average, int length, int width)
{
	int i;
	int add_value;

	for(i=0; i<width-1;i++)
		average[i] = 0.0f;

	add_value=0;
	for (i=0; i<width; i++)
	{
		add_value += values[i];
		sums[i] = add_value;
	}
	
	average[width-1] = (float) add_value;
	for (i=width; i< length; i++)
	{
		add_value = add_value + values[i] - values[i-width];
		sums[i] = add_value;
		average[i] = (float) add_value;
	}
	for (i=width-1; i< length;i++)
		average[i] = average[i] / (float)width;

}

void mavgsums1(int *values, int *sums, float *average, int length, int width)
{
	int i;
        int add_value;

        for(i=0; i<width;i++)
                average[i] = 0.0f;
        
        add_value=0;
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
                average[i] = (float)sums[i] / (float)width;

}

int main (int argc, char *argv[])
{
	float *result;
	int data_num = sizeof(stock_array1) / sizeof(stock_array1[0]);
	int window_num = (int) WINDOW_SIZE;
	
	int i;
	
	result = (float*) malloc (data_num * sizeof(float));
	int * sums = (int*) malloc(data_num* sizeof(int));
	
	//moving_average(stock_array1, sums, result, data_num, window_num);
	mavgsums1(stock_array1, sums, result, data_num, window_num);
	
	for (int i=0; i < data_num; i++) {
		printf("i = %d \t value = %d \t sum = %d \t average = %f\n", i, stock_array1[i], sums[i], result[i]);
	}
	
	free(sums);
	free(result);
}
