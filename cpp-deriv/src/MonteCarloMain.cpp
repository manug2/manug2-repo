#include "Random1.h"
#include <iostream>
#include <cmath>
using namespace std;

double MonteCarlo(double Expiry,
		double Strike,
		double Spot,
		double Vol,
		double r,
		unsigned long NumberOfPaths)
{
	double variance = Vol*Vol*Expiry;
	double rootVariance = sqrt(variance);
	double itoCorrection = -0.5*variance;

	double movedSpot = Spot*exp(r*Expiry + itoCorrection);
	double thisSpot;
	double runningSum=0;

	for (unsigned long i=0; i < NumberOfPaths; i++)
	{
		double thisGaussian = GetOneGaussianByBoxMuller();
		thisSpot = movedSpot*exp(rootVariance*thisGaussian);
		double thisPayoff = thisSpot - Strike;
		thisPayoff = thisPayoff >0? thisPayoff : 0;
		runningSum += thisPayoff;
	}

	double mean = runningSum / NumberOfPaths;
	mean *= exp(-r*Expiry);
	return mean;
}

int main()
{
	double Expiry;
	double Strike;
	double Spot;
	double Vol;
	double r;
	unsigned long NumberOfPaths;

	cout << endl << "Enter " << "expiry :";
	cin >> Expiry;

	cout << endl << "Enter " << "Strike :";
	cin >> Strike;

	cout << endl << "Enter " << "Spot :";
	cin >> Spot;

	cout << endl << "Enter " << "Vol :";
	cin >> Vol;

	cout << endl << "Enter " << "r :";
	cin >> r;

	cout << endl << "Enter " << "NumberOfPaths :";
	cin >> NumberOfPaths;

	double result = MonteCarlo(Expiry, Strike, Spot, Vol, r, NumberOfPaths);

	cout << endl << "the price is " << result << endl;

	double tmp;
	cin >> tmp;

	return 0;
}
