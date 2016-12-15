import unittest

from clrs.c8.bucket_sort import *


class TestBucketSort(unittest.TestCase):

    def setUp(self):
        pass

    def test_should_sort_1_element(self):
        self.assertEqual(
            [0.1],
            bucket_sort(
                [0.1]))

    def test_should_sort_2_elements(self):
        self.assertEqual(
            [0.1, 0.2],
            bucket_sort(
                [0.2, 0.1]))

    def test_should_sort_3_elements(self):
        self.assertEqual(
            [0.1, 0.2, 0.3],
            bucket_sort(
                [0.3, 0.2, 0.1]))

    def test_should_sort_3_elements_when_repeated(self):
        self.assertEqual(
            [0.1, 0.1, 0.2],
            bucket_sort(
                [0.1, 0.2, 0.1]))

    def test_should_sort(self):
        self.assertEqual(
            [0.01, 0.02, 0.03, 0.04, 0.07, 0.08, 0.09, 0.10, 0.14, 0.16],
            bucket_sort(
                [0.04, 0.01, 0.03, 0.02, 0.16, 0.09, 0.10, 0.14, 0.08, 0.07]))

