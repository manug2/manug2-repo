#include <stdio.h>
#include "dc.h"

int main(int argc, char ** argv)
{
	int i, n=32;
	mt_struct **mts;
	init_dc(4172);
	mts = get_mt_parameters(32, 521, n);
	for (i=0; i<n, i++)
	{
		printf("{%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d}\n",
			mts[i]->aaa, mts[i]->mm, mts[i]->nn, mts[i]->rr, mts[i]->ww,
			mts[i]->wmask, mts[i]->umask, mts[i]->lmask, mts[i]->shift0, mts[i]->shift1,
			mts[i]->shiftB, mts[i]->shiftC, mts[i]->maskB, mts[i]->maskC);
	}
	return 0;
}

