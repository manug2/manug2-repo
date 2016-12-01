import unittest

from clrs.c6.heap_sort import *


class TestHeapSort(unittest.TestCase):

    def setUp(self):
        pass

    def test_should_build_3_element_heap(self):
        self.assertEqual(
            [1, 2, 3],
            heap_sort(
                [3, 2, 1]))

    def test_should_build(self):
        self.assertEqual(
            [1, 2, 3, 4, 7, 8, 9, 10, 14, 16],
            heap_sort(
                [4, 1, 3, 2, 16, 9, 10, 14, 8, 7]))
