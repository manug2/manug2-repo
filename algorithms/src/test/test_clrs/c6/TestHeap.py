import unittest

from clrs.c6.heap import *


class TestHeapify(unittest.TestCase):

    def setUp(self):
        pass

    def test_should_heapify_1_element_array(self):
        self.assertEqual(
            [1],
            Heap([1], 1).heapify(1).array)

    def test_should_heapify_2_element_array_trivial(self):
        self.assertEqual(
            [2, 1],
            Heap([2, 1], 2).heapify(1).array)

    def test_should_heapify_2_element_array_trivial_2(self):
        self.assertEqual(
            [2, 1],
            Heap([2, 1], 2).heapify(2).array)

    def test_should_heapify_2_element_array(self):
        self.assertEqual(
            [2, 1],
            Heap([1, 2], 2).heapify(1).array)


class TestBuildMaxHeap(unittest.TestCase):

    def setUp(self):
        pass

    def test_should_build_1_element_heap(self):
        self.assertEqual(
            [1],
            Heap([1]).build_max_heap().array)

    def test_should_heapify_2_element_heap_trivial(self):
        self.assertEqual(
            [2, 1],
            Heap([2, 1]).build_max_heap().array)

    def test_should_heapify_2_element_heap(self):
        self.assertEqual(
            [2, 1],
            Heap([1, 2]).build_max_heap().array)

    def test_should_heapify_3_element_heap(self):
        self.assertEqual(
            [3, 2, 1],
            Heap([1, 2, 3]).build_max_heap().array)
