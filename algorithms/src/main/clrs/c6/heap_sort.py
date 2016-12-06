from clrs.c6.heap import MaxHeap, MinHeap


def heap_sort(array):
    heap = MaxHeap(array)

    heap.build()
    last = heap.size

    for i in range(last, 1, -1):
        heap.swap(1, i)
        heap.size -= 1
        heap.heapify(1)

    return heap.array


def heap_sort_min(array):
    heap = MinHeap(array)

    heap.build()
    last = heap.size

    for i in range(last, 1, -1):
        heap.swap(1, i)
        heap.size -= 1
        heap.heapify(1)

    return heap.array
