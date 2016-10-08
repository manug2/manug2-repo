MIN = -1000000


def find_max_sub_array_brute_force(array):
    if len(array) == 0:
        return 0
    return _find_max_sub_array(array)


def _find_max_sub_array(array):
    """Find max sub total in O(n^2)"""
    maximum = MIN

    for i in range(0, len(array)):
        total = 0
        for j in range(i, len(array)):
            total += array[j]
            if total > maximum:
                maximum = total

    return maximum
