#include "Random1.h"
#include "PayOff2.h"
#include "MonteCarlo2.h"
#include "DoubleDigital.h"
#include <iostream>
#include <cmath>
using namespace std;

int main()
{
	double Expiry;
	double Low, Up;
	double Spot;
	double Vol;
	double r;
	unsigned long NumberOfPaths;

	cout << endl << "Enter " << "expiry :";
	cin >> Expiry;

	cout << endl << "Enter " << "Lower barrier :";
	cin >> Low;

	cout << endl << "Enter " << "Upper barrier :";
	cin >> Up;

	cout << endl << "Enter " << "Spot :";
	cin >> Spot;

	cout << endl << "Enter " << "Vol :";
	cin >> Vol;

	cout << endl << "Enter " << "r :";
	cin >> r;

	cout << endl << "Enter " << "NumberOfPaths :";
	cin >> NumberOfPaths;

	PayOffDoubleDigital payOff (Low, Up);
	double resultPut = MonteCarlo2(payOff, Expiry, Spot, Vol, r, NumberOfPaths);
	cout << endl << "the DD price is " << resultPut << endl;

	double tmp;
	cin >> tmp;

	return 0;
}
