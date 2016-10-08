MIN = -1000000


def find_max_sub_array_tuple_brute_force(array):
    if len(array) == 0:
        return None
    else:
        return _find_max_sub_array_tuple(array)


def find_max_sub_array_brute_force(array):
    if len(array) == 0:
        return 0
    return _find_max_sub_array_tuple(array)[2]


def _find_max_sub_array_tuple(array):
    """Find max sub total in O(n^2)"""
    maximum = MIN
    low  = -1
    high = -1

    for i in range(0, len(array)):
        total = 0
        for j in range(i, len(array)):
            total += array[j]
            if total > maximum:
                maximum = total
                low = i
                high = j

    return low, high, maximum
