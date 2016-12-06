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
        return int(floor(index / 2))

    def left(self, index):
        return 2 * index

    def right(self, index):
        return 2 * index + 1

    def swap(self, index1, index2):
        tmp = self.array[index2-1]
        self.array[index2-1] = self.array[index1-1]
        self.array[index1-1] = tmp


class MaxHeap(Heap):

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

    def build(self):
        self.size = len(self.array)
        middle = int(floor(self.size/2))
        for i in range(middle, 0, -1):
            self.heapify(i)

        return self

    def get_max(self):
        return self.array[0]

    def extract_max(self):
        if self.size < 1:
            raise IndexError('Heap underflow')

        h_max = self.array[0]

        self.array[0] = self.array[self.size-1]
        self.size -= 1
        self.heapify(1)

        return h_max

    def increase_key(self, index, value):
        if value < self[index]:
            raise ValueError('new key is less than current value')

        self.array[index-1] = value
        i = index
        while i > 1 and self[self.parent(i)] < self[i]:
            self.swap(i, self.parent(i))
            i = self.parent(i)

        return self

    def insert(self, key):
        self.size += 1
        self.array.append(-10000)
        self.increase_key(self.size, key)
        return self

