#include "Random1.h"
#include "PayOff1.h"
#include "MonteCarlo1.h"
#include <iostream>
#include <cmath>
using namespace std;

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

	PayOff callPayOff (Strike, PayOff::call);
	double resultCall = MonteCarlo1(callPayOff, Expiry, Spot, Vol, r, NumberOfPaths);
	cout << endl << "the call price is " << resultCall << endl;

	PayOff putPayOff (Strike, PayOff::put);
	double resultPut = MonteCarlo1(putPayOff, Expiry, Spot, Vol, r, NumberOfPaths);
	cout << endl << "the put price is " << resultPut << endl;

	double tmp;
	cin >> tmp;

	return 0;
}
