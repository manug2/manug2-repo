import unittest

from clrs.c8.counting_sort import *


class TestCountingSort(unittest.TestCase):

    def setUp(self):
        pass

    def test_should_sort_book_page_195(self):
        self.assertEqual(
            [0, 0, 2, 2, 3, 3, 3, 5],
            counting_sort(
                [2, 5, 3, 0, 2, 3, 0, 3]))

    def test_should_sort_3_elements(self):
        self.assertEqual(
            [1, 2, 3],
            counting_sort(
                [3, 2, 1]))

    def test_should_sort_3_elements_when_repeated(self):
        self.assertEqual(
            [1, 1, 2],
            counting_sort(
                [1, 2, 1]))

    def test_should_sort(self):
        self.assertEqual(
            [1, 2, 3, 4, 7, 8, 9, 10, 14, 16],
            counting_sort(
                [4, 1, 3, 2, 16, 9, 10, 14, 8, 7]))

