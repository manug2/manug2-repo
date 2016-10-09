

def multiply_brute_force(A, B):
    C = list()

    M = len(A)
    assert len(A[0]) == len(B)
    N = len(A[0])
    K = len(B[0])

    for i in range(0, M):
        row = list()
        for j in range(0, K):
            row.append(0)
        C.append(row)

    for i in range(0, M):
        for k in range(0, K):
            for j in range(0, N):
                C[i][k] += A[i][j] * B[j][k]

    return C
