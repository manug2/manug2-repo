
def heapify(heap, index):
    largest = index
    left = heap.left(index)
    right = heap.right(index)

    if left <= heap.size and heap[left] > heap[largest]:
        largest = left

    if right <= heap.size and heap[right] > heap[largest]:
        largest = right

    if largest != index:
        heap.swap(index, largest)
        heapify(heap, largest)

    return

