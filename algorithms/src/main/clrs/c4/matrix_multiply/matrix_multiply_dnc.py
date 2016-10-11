from math import floor


def matrix_multiply_dnc(A, B):
    assert len(A[0]) == len(B)

    return _matrix_multiply_recursion(A, B, len(A), len(B), len(B[0]), 0, 0, 0, 0)


def _matrix_multiply_recursion(A, B, M, N, K,
                               A_i, A_j, B_i, B_j):
    if N == 1:
        C = list()
        C.append(list())
        try:
            a_index = A[A_i][A_j]
            b_index = B[B_i][B_j]
            C[0].append(a_index * b_index)
        except IndexError as e:
            raise e
        return C

    if not M == N:
        print ('asymmetric', M, N, K)
    N_by_2 = floor(N/2)
    M_by_2 = floor(M/2)
    K_by_2 = floor(K/2)

    C11 = _matrix_add(
        _matrix_multiply_recursion(A, B, M_by_2, N_by_2, K_by_2,
                                   A_i, A_j, B_i, B_j),
        _matrix_multiply_recursion(A, B, M_by_2, N_by_2, K_by_2,
                                   A_i, A_j+N_by_2, B_i+N_by_2, B_j))

    if M <= N:
        C12 = _matrix_add(
            _matrix_multiply_recursion(A, B, M_by_2, N_by_2, K_by_2,
                                       A_i, A_j, B_i, B_j+K_by_2),
            _matrix_multiply_recursion(A, B, M_by_2, N_by_2, K_by_2,
                                       A_i, A_j+K_by_2, B_i+N_by_2, B_j+K_by_2))

    if M >= N:
        C21 = _matrix_add(
            _matrix_multiply_recursion(A, B, M_by_2, N_by_2, K_by_2,
                                       A_i+K_by_2, A_j, B_i, B_j),
            _matrix_multiply_recursion(A, B, M_by_2, N_by_2, K_by_2,
                                       A_i+K_by_2, A_j+N_by_2, B_i+N_by_2, B_j))

    C22 = _matrix_add(
        _matrix_multiply_recursion(A, B, M_by_2, N_by_2, K_by_2,
                                   A_i+M_by_2, A_j, B_i, B_j+K_by_2),
        _matrix_multiply_recursion(A, B, M_by_2, N_by_2, K_by_2,
                                   A_i+M_by_2, A_j+N_by_2, B_i+N_by_2, B_j+K_by_2))

    C = list()
    for i in range(0, len(C11)):
        C.append(list())
        for j in range(0, len(C11[0])):
            try:
                C[i].append(C11[i][j])
            except IndexError as e:
                raise e
        if M <= N:
            for j in range(K_by_2, K):
                try:
                    C[i].append(C12[i][j-K_by_2])
                except IndexError as e:
                    raise e

    for i in range(M_by_2, M):
        C.append(list())
        if M >= N:
            for j in range(0, K_by_2):
                C[i].append(C21[i-M_by_2][j])
        for j in range(K_by_2, K):
            C[i].append(C22[i-M_by_2][j-K_by_2])

    return C


def _matrix_add(C1,
                C2):
    C = list()
    N = len(C1)
    for i in range(0, N):
        C.append(list())
        if len(C1[i]) > 0:
            for j in range(0, len(C1[i])):
                try:
                    C[i].append(C1[i][j] + C2[i][j])
                except IndexError as e:
                    raise e

    return C
