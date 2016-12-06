from clrs.c6.common import swap
from copy import copy


def quick_sort(array):
    array_copy = copy(array)
    return quick_sort_internal(array_copy, 0, len(array)-1)


def quick_sort_internal(array, p, r):

    if p < r:
        q = partition(array, p, r)
        quick_sort_internal(array, p, q-1)
        quick_sort_internal(array, q+1, r)
    return array


def partition(array, p, r):
    x = array[r]

    i = p-1
    for j in range(p, r):
        if array[j] < x:
            i += 1
            swap(array, i, j)

    swap(array, i+1, r)

    return i + 1


def quick_sort_min(array):
    array_copy = copy(array)
    return quick_sort_min_internal(array_copy, 0, len(array)-1)


def quick_sort_min_internal(array, p, r):

    if p < r:
        q = partition_min(array, p, r)
        quick_sort_min_internal(array, p, q-1)
        quick_sort_min_internal(array, q+1, r)
    return array


def partition_min(array, p, r):
    x = array[r]

    i = p-1
    for j in range(p, r):
        if array[j] > x:
            i += 1
            swap(array, i, j)

    swap(array, i+1, r)

    return i + 1
