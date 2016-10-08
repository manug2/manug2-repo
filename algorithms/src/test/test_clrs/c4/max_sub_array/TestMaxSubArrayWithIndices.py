import unittest


class TestMaxSubArray(unittest.TestCase):

    def setUp(self):
        from clrs.c4.max_sub_array.max_sub_array_brute_force import find_max_sub_array_tuple_brute_force
        self.algorithm = find_max_sub_array_tuple_brute_force
        print("using algo->", self.algorithm)

    def test_should_run(self):
        self.assertIsNone(self.algorithm([]))

    def test_should_run_1_element(self):
        self.assertEqual((0, 0, 5), self.algorithm([5]))

    def test_should_run_2_element(self):
        self.assertEqual((0, 1, 5), self.algorithm([2, 3]))

    def test_should_cal_when_1_element_is_negative(self):
        self.assertEqual((1, 1, 3), self.algorithm([-2, 3]))

    def test_should_run_3_element(self):
        self.assertEqual((0, 2, 15), self.algorithm([10, 2, 3]))

    def test_should_calc(self):
        self.assertEqual((0, 10, 34), self.algorithm([1, 2, 3, 4, 5, -1, 10, 3, 3, -1, 5]))

    def test_should_run_2_negative_elements(self):
        self.assertEqual((0, 0, -2), self.algorithm([-2, -3]))

    def test_should_run_3_negative_elements(self):
        self.assertEqual((0, 0, -2), self.algorithm([-2, -3, -4]))

    def test_should_calc2(self):
        self.assertEqual((5, 8, 16), self.algorithm([1, 3, 4, 5, -20, 5, 3, 3, 5]))

    def test_should_calc3(self):
        self.assertEqual((6, 9, 16), self.algorithm([1, 3, 4, 5, -10, -10, 5, 3, 3, 5]))

    def test_should_calc4(self):
        self.assertEqual((8, 11, 16), self.algorithm([1, 3, 4, 5, -5, -5, -5, -5, 5, 3, 3, 5]))


class TestDivideAndConquer(TestMaxSubArray):

    def setUp(self):
        from clrs.c4.max_sub_array.max_sub_array_dnc import find_max_sub_array_tuple_dnc
        self.algorithm = find_max_sub_array_tuple_dnc
        print("using algo->", self.algorithm)

"""
class TestLinear(TestMaxSubArray):

    def setUp(self):
        from clrs.c4.max_sub_array.max_sub_array_linear import find_max_sub_array_tuple_linear
        self.algorithm = find_max_sub_array_tuple_linear
        print("using algo->", self.algorithm)
"""