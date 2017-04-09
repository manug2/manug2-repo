

class BinSearchTree:

    def __init__(self):
        self.root=None

    def insert(self, item=None, items=None):
        items_left_to_add = items

        if self.root is None:
            if item is not None:
                self.root = Node(item)
            elif items is not None:
                self.root = Node(items[0])
                items_left_to_add = items[1:]

        if items_left_to_add is None:
            return self

        for i in items_left_to_add:
            node = Node(i)
            node.parent = self.root
            if i < self.root.item:
                self.root.left = node
            else:
                self.root.right = node
        return self

    def in_order(self):
        if self.root is None:
            return ""
        else:
            return self.root.in_order().strip()

    def find(self, key):
        if self.root is None:
            return None
        else:
            return self.root.find(key)


class Node:
    def __init__(self, item):
        self.item = item
        self.left=None
        self.right=None

    def in_order(self):
        result = ""
        if self.left is not None:
            result = self.left.in_order()
        result += str(self.item) + " "
        if self.right is not None:
            result += self.right.in_order()
        return result

    def find(self, key):
        if self.item == key:
            return self.item
        elif key < self.item and self.left is not None:
            return self.left.find(key)
        elif self.right is not None:
            return self.right.find(key)
        return None
