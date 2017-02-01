import string

translation_table = str.maketrans(string.punctuation + string.ascii_uppercase,
                                     " " * len(string.punctuation) + string.ascii_lowercase)


def split_line(line):
    line = line.translate(translation_table)
    words = str(line).split(' ')
    return words
