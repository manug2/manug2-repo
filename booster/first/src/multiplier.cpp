#include <boost/lambda/lambda.hpp>
#include <iostream>
#include <stdlib.h>
#include <stdio.h>

using namespace std;

int main (int argc, char* argv[]) {

#ifdef DEBUG
printf("Argument count = [%d]\n", argc);
#endif

	using namespace boost::lambda;
	typedef std::istream_iterator<int> in;

	std::for_each(
		in(std::cin), in(), std::cout << (_1 * 3) << " " );
	
}
