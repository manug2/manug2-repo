

def counting_sort(A):
    k = max(A)
    length = len(A)
    result = [None] * length

    C = [0] * (k+1)

    for j in range(0, length):
        C[A[j]] += 1

    for i in range(1, len(C)):
        C[i] += C[i-1]

    for j in range(length, 0, -1):
        t = j-1
        a = A[t]
        c = C[a]
        result[c-1] = a
        C[a] -= 1

    return result
