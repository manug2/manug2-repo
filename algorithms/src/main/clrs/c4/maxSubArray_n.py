MIN = -1000000


def find_max_sub_array(array):
    if len(array) == 0:
        return 0
    return _find_max_sub_array(array)


def _find_max_sub_array(array):
    """Find max sub total in O(n^2)"""
    total = array[0]
    prev_total = total
    maximum = total

    for i in range(1, len(array)):
        total += array[i]
        if total > maximum:
            maximum = total
        elif prev_total > total:
            total = 0
            if array[i] > maximum:
                maximum = array[i]

        prev_total = total

    return maximum
