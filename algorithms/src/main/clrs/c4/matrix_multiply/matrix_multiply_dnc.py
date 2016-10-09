

def matrix_multiply_dnc(A, B):
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

