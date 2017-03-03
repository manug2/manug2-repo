MIN = -1000000


def find_max_sub_array_linear(array):
    if len(array) == 0:
        return 0
    return _find_max_sub_array(array)


def _find_max_sub_array(A):
    max_ending_here = max_so_far = A[0]
    for x in A[1:]:
        max_ending_here = max(x, max_ending_here + x)
        max_so_far = max(max_so_far, max_ending_here)
    return max_so_far
