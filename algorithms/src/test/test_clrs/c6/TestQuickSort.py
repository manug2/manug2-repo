import unittest

from clrs.c6.quick_sort import *


class TestQuickSort(unittest.TestCase):

    def setUp(self):
        pass

    def test_should_sort_3_elements(self):
        self.assertEqual(
            [1, 2, 3],
            quick_sort(
                [3, 2, 1]))

    def test_should_sort(self):
        self.assertEqual(
            [1, 2, 3, 4, 7, 8, 9, 10, 14, 16],
            quick_sort(
                [4, 1, 3, 2, 16, 9, 10, 14, 8, 7]))

    def test_should_sort_3_element_min(self):
        self.assertEqual(
            [3, 2, 1],
            quick_sort_min(
                [1, 2, 3]))

    def test_should_sort_min(self):
        self.assertEqual(
            [21, 17, 11, 9, 8, 6, 4, 3, 2, 1],
            quick_sort_min(
                [11, 21, 3, 4, 17, 8, 9, 2, 1, 6]))


from clrs.c6.quick_sort_random import quick_sort_randomized


class TestRandomizedQuickSort(unittest.TestCase):

    def setUp(self):
        pass

    def test_should_sort_3_elements(self):
        self.assertEqual(
            [1, 2, 3],
            quick_sort_randomized(
                [3, 2, 1]))

    def test_should_sort_2_elements(self):
        self.assertEqual(
            [2, 3],
            quick_sort_randomized(
                [3, 2]))

    def test_should_sort(self):
        self.assertEqual(
            [1, 2, 3, 4, 7, 8, 9, 10, 14, 16],
            quick_sort_randomized(
                [4, 1, 3, 2, 16, 9, 10, 14, 8, 7]))

