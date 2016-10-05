
import unittest
from clrs.c4.maxSubArray_n2 import find_max_sub_array as find_max_sub_array_n2
from clrs.c4.maxSubArray_dnc import find_max_sub_array as find_max_sub_array_dnc


class TestN2(unittest.TestCase):

    def setUp(self):
        #self.subject = find_max_sub_array_n2
        self.subject = find_max_sub_array_dnc

    def test_should_run(self):
        self.assertEqual(0, self.subject([]));

    def test_should_run_1_element(self):
        self.assertEqual(5, self.subject([5]));

    def test_should_run_2_element(self):
        self.assertEqual(5, self.subject([2, 3]));

    def test_should_run_2_element_with_negative(self):
        self.assertEqual(3, self.subject([-2, 3]));

    def test_should_run_3_element(self):
        self.assertEqual(15, self.subject([10, 2, 3]));
