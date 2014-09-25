#include "MinMax.h"

double max(double one, double two)
{
	return (one - two) > 0.0001 ? one : two;
}

double min(double one, double two)
{
	return (one - two) < 0.0001 ? one : two;
}

