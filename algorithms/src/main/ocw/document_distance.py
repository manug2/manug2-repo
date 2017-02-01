from math import acos, sqrt


class DocDistanceCalculator:

    def __init__(self, f_split_line):
        self.f_split = f_split_line

    def __call__(self, *args, **kwargs):
        return self.calculate_distance(*args)

    def calculate_distance(self, *args):
        """split_line: O(n)"""
        dict1 = self.parse_doc(args[0])
        dict2 = self.parse_doc(args[1])

        product = dot_product(dict1, dict2)
        d1 = dot_product(dict1, dict1)
        d2 = dot_product(dict2, dict2)

        dist = product / sqrt(d1 * d2)

        return acos(dist)

    def parse_doc(self, lines):
        """split_line: O(n)"""
        word_dict = dict()

        if type(lines) == list:
            for line in lines:
                self.parse_line(line, word_dict)
        elif type(lines) == str:
            self.parse_line(lines, word_dict)
        else:
            raise ValueError('Expecting only a line of list of lines')

        return word_dict

    def parse_line(self, line, word_dict):
        """split_line: O(n)"""
        words = self.f_split(line)

        for w in words:
            if w in word_dict:
                word_dict[w] += 1
            else:
                word_dict[w] = 1

        return None


def dot_product(dict1, dict2):
    """split_line: O(n)"""
    res = 0
    for w in dict1:
        if w in dict2:
            res += dict1[w] * dict2[w]
    return res
