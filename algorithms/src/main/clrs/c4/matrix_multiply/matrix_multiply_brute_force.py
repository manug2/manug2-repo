

def multiply_brute_force(A, B):
    C = list()

    N = len(A)

    for i in range(0, N):
        row = list()
        for j in range(0, N):
            row.append(0)
        C.append(row)

    for i in range(0, N):
        for j in range(0, N):
            for k in range(0, N):
                C[i][j] += A[i][k] * B[k][j]

    return C


def _find_len(maybe_element_or_row):
    if type(maybe_element_or_row) == list:
        return len(maybe_element_or_row)
    else:
        return 1


def _get_element_from_row(maybe_element_or_row, k):
    if type(maybe_element_or_row) == list:
        return maybe_element_or_row[k]
    else:
        return maybe_element_or_row


def _get_element(maybe_element_or_row_or_matrix, i, j=None):
    if j is None:
        return _get_element_from_row(maybe_element_or_row_or_matrix[i])

    elif type(maybe_element_or_row_or_matrix) == list:
        row_i = _get_element_from_row(maybe_element_or_row_or_matrix, i)
        if type(row_i) == list:
            return row_i[j]
        else:
            return row_i

    else:
        return maybe_element_or_row_or_matrix
