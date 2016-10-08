from math import floor

from clrs.c4.max_sub_array.max_sub_array_brute_force import find_max_sub_array_brute_force

MIN = -1000000
RECURSION_THRESHOLD = 3


def find_max_sub_array_dnc(array):
    if len(array) == 0:
        return 0

    if len(array) < RECURSION_THRESHOLD:
        return find_max_sub_array_brute_force(array)

    """Using divide and conquer technique"""
    return _find_max_sub_array(array, 0, len(array)-1)


def _find_max_sub_array(array, low, high):
    """Using divide and conquer technique"""

    if low == high:
        return array[low]

    mid = floor((low + high)/2)
    left_max  = _find_max_sub_array(array, low, mid)
    right_max = _find_max_sub_array(array, mid+1, high)
    cross_max = _find_max_cross(array, low, mid, high)

    return _compare_for_max(cross_max, left_max, right_max)
    # end of _find_max_sub_array


def _find_max_cross(array, low, mid, high):
    """Find max total around middle in O(n)"""

    total_left = MIN
    total_right = MIN

    total = 0
    for i in range(mid, low-1, -1):
        total += array[i]
        if total > total_left:
            total_left = total

    total = 0
    for i in range(mid, high):
        total += array[i+1]
        if total > total_right:
            total_right = total

    return total_left+total_right
    #end of _find_max_cross


def _compare_for_max(cross_max, left_max, right_max):
    if left_max > right_max:
        if left_max > cross_max:
            return left_max
        else:
            return cross_max
    else:
        if right_max > cross_max:
            return right_max
        else:
            return cross_max
    #end of _compare_for_max
