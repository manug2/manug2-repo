from clrs.c6.common import swap
from copy import copy
from random import randint
from clrs.c6.quick_sort import partition


def quick_sort_randomized(array):
    array_copy = copy(array)
    quick_sort_internal_randomized(array_copy, 0, len(array)-1)
    return array_copy


def quick_sort_internal_randomized(array, p, r):
    if p < r:
        q = partition_randomized(array, p, r)
        quick_sort_internal_randomized(array, p, q-1)
        quick_sort_internal_randomized(array, q+1, r)


def partition_randomized(array, p, r):
    i = randint(p, r)
    if i < r:
        swap(array, r, i)
    return partition(array, p, r)
