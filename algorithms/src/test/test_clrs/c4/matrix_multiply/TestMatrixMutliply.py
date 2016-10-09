import unittest


class TestMatrixMultiply(unittest.TestCase):

    def setUp(self):
        from clrs.c4.matrix_multiply.matrix_multiply_brute_force import multiply_brute_force
        self.algorithm = multiply_brute_force

    def test_1x1_not_None(self):
        self.assertIsNotNone(self.algorithm([[2]], [[3]]))

    def test_1x1(self):
        self.assertEqual([[6]], self.algorithm([[2]], [[3]]))

    def test_1x1_rev(self):
        self.assertEqual([[6]], self.algorithm([[3]], [[2]]))

    def test_1x2x1(self):
        self.assertEqual(
            [[6, 6],
             [4, 4]],
            self.algorithm(
                [[2, 1],
                [1, 1]],
                [[2, 2],
                [2, 2]]))


class TestDivideAndConquer(TestMatrixMultiply):

    def setUp(self):
        from clrs.c4.matrix_multiply.matrix_multiply_dnc import matrix_multiply_dnc
        self.algorithm = matrix_multiply_dnc
        print("using algo->", self.algorithm)
