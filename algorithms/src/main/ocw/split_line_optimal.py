
def split_line(line):
    """split_line: Theta(n)"""

    assert type(line) == str

    words = list()
    chars = list()

    for c in line:
        if c.isalnum():
            chars.append(c)
        else:
            word = "".join(chars)
            words.append(word.lower())
            chars = list()

    if len(chars) > 0:
        word = "".join(chars)
        words.append(word.lower())

    return words
