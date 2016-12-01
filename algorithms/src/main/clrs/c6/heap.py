from copy import copy
from math import floor


class Heap:
    def __init__(self, array, size=0):
        self.array = copy(array)
        self.size = size

    def __getitem__(self, item):
        if item > len(self.array):
            raise IndexError

        return self.array[item-1]

    def parent(self, index):
        return index / 2

    def left(self, index):
        return 2 * index

    def right(self, index):
        return 2 * index + 1

    def swap(self, index1, index2):
        tmp = self.array[index2-1]
        self.array[index2-1] = self.array[index1-1]
        self.array[index1-1] = tmp

    def heapify(self, index):
        largest = index
        left = self.left(index)
        right = self.right(index)

        if left <= self.size and self[left] > self[largest]:
            largest = left

        if right <= self.size and self[right] > self[largest]:
            largest = right

        if largest != index:
            self.swap(index, largest)
            self.heapify(largest)

        return self

    def build_max_heap(self):
        self.size = len(self.array)
        middle = int(floor(self.size/2))
        for i in range(middle, 0, -1):
            self.heapify(i)

        return self

