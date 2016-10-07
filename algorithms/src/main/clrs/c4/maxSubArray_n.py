MIN = -1000000


def find_max_sub_array(array):
    if len(array) == 0:
        return 0
    return _find_max_sub_array(array)


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
