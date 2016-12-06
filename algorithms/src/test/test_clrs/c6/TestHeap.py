import unittest

from clrs.c6.heap import *


class TestMaxHeapify(unittest.TestCase):

    def setUp(self):
        pass

    def test_should_heapify_1_element_array(self):
        self.assertEqual(
            [1],
            Heap([1], 1).max_heapify(1).array)

    def test_should_heapify_2_element_array_trivial(self):
        self.assertEqual(
            [2, 1],
            Heap([2, 1], 2).max_heapify(1).array)

    def test_should_heapify_2_element_array_trivial_2(self):
        self.assertEqual(
            [2, 1],
            Heap([2, 1], 2).max_heapify(2).array)

    def test_should_heapify_2_element_array(self):
        self.assertEqual(
            [2, 1],
            Heap([1, 2], 2).max_heapify(1).array)


class TestBuildMaxHeap(unittest.TestCase):

    def setUp(self):
        pass

    def test_should_build_1_element_heap(self):
        self.assertEqual(
            [1],
            Heap([1]).build_max_heap().array)

    def test_should_build_2_element_heap_trivial(self):
        self.assertEqual(
            [2, 1],
            Heap([2, 1]).build_max_heap().array)

    def test_should_build_2_element_heap(self):
        self.assertEqual(
            [2, 1],
            Heap([1, 2]).build_max_heap().array)

    def test_should_build_3_element_heap(self):
        self.assertEqual(
            [3, 2, 1],
            Heap([1, 2, 3]).build_max_heap().array)

    def test_should_build(self):
        self.assertEqual(
            [16, 14, 10, 8, 7, 9, 3, 2, 4, 1],
            Heap([4, 1, 3, 2, 16, 9, 10, 14, 8, 7])
                .build_max_heap().array)


class TestMaxHeapOps(unittest.TestCase):
    def setUp(self):
        pass

    def test_should_get_max(self):
        heap = Heap([1, 2, 3]).build_max_heap()
        self.assertEqual(3, heap.get_max())

    def test_should_extract_max(self):
        heap = Heap([1, 2, 3]).build_max_heap()
        self.assertEqual(3, heap.extract_max())
        self.assertEqual(2, heap.get_max())

    def test_should_increase_key(self):
        heap = Heap([1, 2, 3]).build_max_heap()
        self.assertEqual(3, heap.get_max())
        heap.increase_key(2, 100)
        self.assertEqual(100, heap.get_max())
        self.assertEqual([100, 3, 1], heap.array)

    def test_should_insert(self):
        heap = Heap([1, 2, 3]).build_max_heap()
        heap.insert(100)
        self.assertEqual(
            [100, 3, 1, 2],
            heap.array)
