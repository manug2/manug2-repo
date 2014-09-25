#include "PayOff2.h"
#include "MinMax.h"

PayOffCall::PayOffCall(double Strike_)
	: PayOff(Strike_)
{
}

double PayOffCall::operator()(double spot) const
{
	return max(spot-Strike, 0.0);
}


PayOffPut::PayOffPut(double Strike_)
	: PayOff(Strike_)
{
}

double PayOffPut::operator()(double spot) const
{
	return max(Strike-spot, 0.0);
}
