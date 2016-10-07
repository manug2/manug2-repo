
import unittest
from clrs.c4.maxSubArray_n import find_max_sub_array as find_max_sub_array_n
from clrs.c4.maxSubArray_n2 import find_max_sub_array as find_max_sub_array_n2
from clrs.c4.maxSubArray_dnc import find_max_sub_array as find_max_sub_array_dnc


class TestN2(unittest.TestCase):

    def setUp(self):
        #self.subject = find_max_sub_array_n2
        #self.subject = find_max_sub_array_dnc
        self.subject = find_max_sub_array_n

    def test_should_run(self):
        self.assertEqual(0, self.subject([]))

    def test_should_run_1_element(self):
        self.assertEqual(5, self.subject([5]))

    def test_should_run_2_element(self):
        self.assertEqual(5, self.subject([2, 3]))

    def test_should_cal_when_1_element_is_negative(self):
        self.assertEqual(3, self.subject([-2, 3]))

    def test_should_run_3_element(self):
        self.assertEqual(15, self.subject([10, 2, 3]))

    def test_should_calc(self):
        self.assertEqual(34, self.subject([1, 2, 3, 4, 5, -1, 10, 3, 3, -1, 5]))

    def test_should_run_2_negative_elements(self):
        self.assertEqual(-2, self.subject([-2, -3]))

    def test_should_run_3_negative_elements(self):
        self.assertEqual(-2, self.subject([-2, -3, -4]))

    def test_should_calc2(self):
        self.assertEqual(16, self.subject([1, 3, 4, 5, -20, 5, 3, 3, 5]))

    def test_should_calc3(self):
        self.assertEqual(16, self.subject([1, 3, 4, 5, -10, -10, 5, 3, 3, 5]))

    def test_should_calc4(self):
        self.assertEqual(16, self.subject([1, 3, 4, 5, -5, -5, -5, -5, 5, 3, 3, 5]))
