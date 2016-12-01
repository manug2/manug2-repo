from clrs.c6.heap import Heap


def heap_sort(array):
    heap = Heap(array)

    heap.build_max_heap()
    last = heap.size

    for i in range(last, 1, -1):
        heap.swap(1, i)
        heap.size -= 1
        heap.heapify(1)

    return heap.array
