from math import floor


def matrix_multiply_dnc(A, B):
    return _matrix_multiply_recursion(A, B, len(A), 0, 0, 0, 0)


def _matrix_multiply_recursion(A, B, N, A_i, A_j, B_i, B_j):
    if N == 1:
        C = list()
        C.append(list())
        C[0].append(A[A_i][A_j] * B[B_i][B_j])
        return C

    N_by_2 = floor(N/2)

    C11 = _matrix_add(
        _matrix_multiply_recursion(A, B, N_by_2,
                                   A_i, A_j, B_i, B_j),
        _matrix_multiply_recursion(A, B, N_by_2,
                                   A_i, A_j+N_by_2, B_i+N_by_2, B_j))

    C12 = _matrix_add(
        _matrix_multiply_recursion(A, B, N_by_2,
                                   A_i, A_j, B_i, B_j+N_by_2),
        _matrix_multiply_recursion(A, B, N_by_2,
                                   A_i, A_j+N_by_2, B_i+N_by_2, B_j+N_by_2))

    C21 = _matrix_add(
        _matrix_multiply_recursion(A, B, N_by_2,
                                   A_i+N_by_2, A_j, B_i, B_j),
        _matrix_multiply_recursion(A, B, N_by_2,
                                   A_i+N_by_2, A_j+N_by_2, B_i+N_by_2, B_j))

    C22 = _matrix_add(
        _matrix_multiply_recursion(A, B, N_by_2,
                                   A_i+N_by_2, A_j, B_i, B_j+N_by_2),
        _matrix_multiply_recursion(A, B, N_by_2,
                                   A_i+N_by_2, A_j+N_by_2, B_i+N_by_2, B_j+N_by_2))

    C = list()
    for i in range(0, N_by_2):
        C.append(list())
        for j in range(0, N_by_2):
            C[i].append(C11[i][j])
        for j in range(N_by_2, N):
            C[i].append(C12[i][j-N_by_2])
    for i in range(N_by_2, N):
        C.append(list())
        for j in range(0, N_by_2):
            C[i].append(C21[i-N_by_2][j])
        for j in range(N_by_2, N):
            C[i].append(C22[i-N_by_2][j-N_by_2])

    return C


def _matrix_add(C1, C2):
    C = list()
    N = len(C1)
    for i in range(0, N):
        C.append(list())
        for j in range(0, N):
            C[i].append(C1[i][j] + C2[i][j])

    return C
