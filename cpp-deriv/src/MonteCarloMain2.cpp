#include "Random1.h"
#include "PayOff2.h"
#include "MonteCarlo2.h"
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

	PayOffCall callPayOff (Strike);
	double resultCall = MonteCarlo2(callPayOff, Expiry, Spot, Vol, r, NumberOfPaths);
	cout << endl << "the call price is " << resultCall << endl;

	PayOffPut putPayOff (Strike);
	double resultPut = MonteCarlo2(putPayOff, Expiry, Spot, Vol, r, NumberOfPaths);
	cout << endl << "the put price is " << resultPut << endl;

	double tmp;
	cin >> tmp;

	return 0;
}
