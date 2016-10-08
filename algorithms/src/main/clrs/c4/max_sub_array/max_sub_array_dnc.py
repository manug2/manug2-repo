from math import floor

from clrs.c4.max_sub_array.max_sub_array_brute_force import find_max_sub_array_brute_force

MIN = -1000000
RECURSION_THRESHOLD = 3


class Result:
    def __init__(self, low, high, maximum):
        self.low = low
        self.high = high
        self.maximum = maximum

    def tuple(self):
        return self.low, self.high, self.maximum


def find_max_sub_array_tuple_dnc(array):
    if len(array) == 0:
        return None
    else:
        return _find_max_sub_array_tuple(array, 0, len(array)-1).tuple()


def find_max_sub_array_dnc(array):
    if len(array) == 0:
        return 0

    if len(array) < RECURSION_THRESHOLD:
        return find_max_sub_array_brute_force(array)

    """Using divide and conquer technique"""
    return _find_max_sub_array_tuple(array, 0, len(array)-1).maximum


def _find_max_sub_array_tuple(array, low, high):
    """Using divide and conquer technique"""

    if low == high:
        return Result(low, high, array[low])

    mid = floor((low + high)/2)
    left  = _find_max_sub_array_tuple(array, low, mid)
    right = _find_max_sub_array_tuple(array, mid+1, high)
    cross = _find_max_cross_tuple(array, low, mid, high)

    return _compare_for_max_tuple(cross, left, right)
    # end of _find_max_sub_array


def _find_max_cross_tuple(array, low, mid, high):
    left = low
    right = high
    total_left = MIN
    total_right = MIN

    total = 0
    for i in range(mid, low-1, -1):
        total += array[i]
        if total > total_left:
            total_left = total
            left = i

    total = 0
    for i in range(mid, high):
        total += array[i+1]
        if total > total_right:
            total_right = total
            right = i+1

    return Result(left, right, total_left+total_right)
    #end of _find_max_cross


def _compare_for_max_tuple(cross, left, right):
    if left.maximum > right.maximum:
        if left.maximum > cross.maximum:
            return left
        else:
            return cross
    else:
        if right.maximum > cross.maximum:
            return right
        else:
            return cross
    #end of _compare_for_max
