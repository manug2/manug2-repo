MIN = -1000000


def find_max_sub_array(array):
    if len(array) == 0:
        return 0
    return _find_max_sub_array(array)


def _find_max_sub_array(array):
    """Find max sub total in O(n^2)"""
    maximum = MIN

    for i in range(0, len(array)):
        total = array[i]
        prev_sum = total
        for j in range(i+1, len(array)):
            total += array[j]
            if total < prev_sum:
                if prev_sum > maximum:
                    maximum = prev_sum
                break
            else:
                prev_sum = total

        if total > maximum:
            maximum = total

    return maximum
