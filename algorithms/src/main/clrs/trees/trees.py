

class BinSearchTree:
    def __init__(self, root):
        self.root = root


class TreeNode:

    def __init__(self, item=None):
        self.parent = None
        self.item = item
        self.left=None
        self.right=None

    def insert(self, item=None, items=None):
        items_left_to_add = items

        if self.item is None:
            if item is not None:
                self.item = item
            elif items is not None:
                self.item = items[0]
                items_left_to_add = items[1:]

        if items_left_to_add is None:
            return self

        for i in items_left_to_add:
            node = TreeNode(i)
            node.parent = self
            if i < self.item:
                self.left = node
            else:
                self.right = node
        return self

    def in_order(self):
        if self.item is None:
            return ""
        else:
            return self._in_order().strip()

    def min(self):
        x = self
        while x.left is not None:
            x = x.left
        return x

    def max(self):
        x = self
        while x.right is not None:
            x = x.right
        return x

    def _in_order(self):
        result = ""
        if self.left is not None:
            result = self.left._in_order()
        result += str(self.item) + " "
        if self.right is not None:
            result += self.right._in_order()
        return result

    def find(self, key):
        if self.item == key:
            return self
        elif key < self.item and self.left is not None:
            return self.left.find(key)
        elif self.right is not None:
            return self.right.find(key)
        return None

    def successor(self):
        if self.right is not None:
            return self.right.min()

        x = self
        y = x.parent
        while y is not None and x == y.right:
            x = y
            y = y.parent
        return y
