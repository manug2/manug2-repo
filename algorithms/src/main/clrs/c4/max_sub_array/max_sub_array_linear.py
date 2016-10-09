MIN = -1000000


def find_max_sub_array_linear(array):
    if len(array) == 0:
        return 0
    return _find_max_sub_array(array)


def find_max_sub_array_tuple_linear(array):
    if len(array) == 0:
        return None
    return _find_max_sub_array_tuple(array)


def _find_max_sub_array_tuple(array):
    """Find max sub total in O(n^2)"""
    total = array[0]
    total2 = array[0]
    maximum = total
    low = 0
    high = 0

    for i in range(1, len(array)):
        total += array[i]
        if total > maximum:
            maximum = total
            high = i
        if total < array[i]:
            total = array[i]

    return low, high, maximum


def _find_max_sub_array(array):
    """Find max sub total in O(n^2)"""
    total = array[0]
    maximum = total

    for i in range(1, len(array)):
        total += array[i]
        if total > maximum:
            maximum = total
        if total < array[i]:
            total = array[i]
        if array[i] > maximum:
            maximum = array[i]

    return maximum
