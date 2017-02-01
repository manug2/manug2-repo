from unittest import TestCase
from ocw.document_distance import DocDistanceCalculator

from math import pi


class TestDocDist(TestCase):

    def setUp(self):
        from ocw.split_line_optimal import split_line
        self.calc = DocDistanceCalculator(split_line)

    def test_should_match_for_same_input_of_one_word(self):
        self.assertEqual(0.0, self.calc("word", "word"))

    def test_should_mismatch_for_diff_input_of_one_word(self):
        self.assertEqual(pi/2, self.calc("word", "sword"))

    def test_should_match_for_same_input_of_two_words(self):
        self.assertEqual(0.0, self.calc("one two", "one two"))

    def test_should_yield_half_for_input_of_two_words_with_one_matching(self):
        dist = self.calc("one two", "one three")
        assert dist > 0
        assert dist < pi/2

    def test_should_match_two_lines_same(self):
        lines1 = list()
        lines1.append('this is first line')
        lines1.append('this being the second')
        from copy import deepcopy
        lines2 = deepcopy(lines1)

        self.assertEqual(0.0, self.calc(lines1, lines2))

    def test_should_match_for_same_input_of_two_words_ignoring_punctuation(self):
        self.assertEqual(0.0, self.calc("one ,two", "one! two"))

    def test_should_match_for_same_input_of_two_words_ignoring_case(self):
        self.assertEqual(0.0, self.calc("one TWO", "ONE two"))
