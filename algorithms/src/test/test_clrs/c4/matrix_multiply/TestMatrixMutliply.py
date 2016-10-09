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

    def test_2x2(self):
        self.assertEqual(
            [[6, 6],
             [4, 4]],
            self.algorithm(
                    [[2, 1],
                    [1, 1]],
                        [[2, 2],
                        [2, 2]]))

    def test_2x4x2(self):
        self.assertEqual(
            [[8, 8],
             [8, 7]],
            self.algorithm(
                    [[2, 1, 1, 1],
                    [1, 1, 2, 1]],
                                [[2, 2],
                                 [1, 2],
                                 [2, 1],
                                 [1, 1]]))

    def test_4x4(self):
        self.assertEqual(
            [[9, 6, 8, 5],
             [9, 6, 7, 5],
             [8, 7, 7, 5],
             [10, 8, 8, 6]],
            self.algorithm(
                        [[1, 2, 1, 1],
                         [1, 1, 2, 1],
                         [2, 1, 1, 1],
                         [2, 1, 2, 1]],
                                    [[1, 2, 1, 1],
                                     [2, 1, 2, 1],
                                     [2, 1, 1, 1],
                                     [2, 1, 2, 1]]))

    def test_4x2x4(self):
        self.assertEqual(
            [[5, 4, 5, 3],
             [3, 3, 3, 2],
             [4, 5, 4, 3],
             [6, 6, 6, 4]],
            self.algorithm(
                        [[1, 2],
                         [1, 1],
                         [2, 1],
                         [2, 2]],
                                [[1, 2, 1, 1],
                                 [2, 1, 2, 1]]))


class TestDivideAndConquer(TestMatrixMultiply):

    def setUp(self):
        from clrs.c4.matrix_multiply.matrix_multiply_dnc import matrix_multiply_dnc
        self.algorithm = matrix_multiply_dnc
        print("using algo->", self.algorithm)
