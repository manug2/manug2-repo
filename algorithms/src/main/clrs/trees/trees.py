

class BinSearchTree:
    def __init__(self, root=None):
        self.root = root

    def delete(self, z):
        if z.left is None:
            self._transplant(z, z.right)
        elif z.right is None:
            self._transplant(z, z.left)
        else:
            y = z.right.min()
            if y.parent != z:
                self._transplant(y, y.right)
                y.right = z.right
                y.right.parent = y
            self._transplant(z, y)
            y.left = z.left
            y.left.parent = y

        return z

    def insert(self, item=None, items=None):
        if items is not None:
            for item in items:
                self._insert(item)
        else:
            self._insert(item)
        return self.root

    def _insert(self, item):
        if self.root is None:
            self.root = TreeNode(item)
            return

        y = None
        x = self.root

        while x is not None:
            y = x
            if x.item is not None and item < x.item:
                x = x.left
            else:
                x = x.right

        z = TreeNode(item)
        z.parent = y

        if z.item < y.item:
            y.left = z
        else:
            y.right = z

    def _transplant(self, u, v):
        if u.parent is None:
            self.root = v
        elif u == u.parent.left:
            u.parent.left = v
        else:
            u.parent.right = v

        if v is not None:
            v.parent = u.parent


class TreeNode:

    def __init__(self, item=None):
        self.parent = None
        self.item = item
        self.left=None
        self.right=None

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

    def __eq__(self, other):
        return other.item == self.item
