from math import floor


def bucket_sort(A):
    n = len(A)

    buckets = []
    for i in range(0, n):
        buckets.append([])

    for i in range(0, n):
        a = A[i]
        buckets[int(floor(a))].append(a)

    for i in range(0, n):
        buckets[i].sort()

    result = []
    for i in range(0, n):
        for j in range(0, len(buckets[i])):
            result.append(buckets[i][j])

    return result
