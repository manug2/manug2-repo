from unittest import TestCase
from clrs.trees.trees import BinSearchTree


class TestTreeNodeAndBinSearchTree(TestCase):

    def setUp(self):
        self.tree = BinSearchTree()

    def test_should_yield_elements_in_order_when_only_1(self):
        self.assertEqual("1", self.tree.insert(1).in_order())

    def test_should_yield_elements_in_order_when_only_2(self):
        self.assertEqual("1 2 3", self.tree.insert(items=[2, 1, 3]).in_order())

    def test_should_find_one_and_only_1(self):
        self.assertEqual(1, self.tree.insert(1).find(1).item)

    def test_should_not_find_2_in_one_and_only_1(self):
        self.assertEqual(None, self.tree.insert(1).find(2))

    def test_should_find_element_on_left(self):
        self.assertEqual(1, self.tree.insert(items=[2, 1, 3]).find(1).item)

    def test_should_find_element_at_root(self):
        self.assertEqual(2, self.tree.insert(items=[2, 1, 3]).find(2).item)

    def test_should_not_find_missing_item_on_right_of_root(self):
        self.assertEqual(None, self.tree.insert(items=[2, 1, 3]).find(4))

    def test_should_not_find_missing_item_on_left_of_root(self):
        self.assertEqual(None, self.tree.insert(items=[2, 1, 3]).find(0))

    def test_should_get_min_when_only_1(self):
        self.assertEqual(1, self.tree.insert(1).min().item)

    def test_should_get_max_when_only_1(self):
        self.assertEqual(1, self.tree.insert(1).max().item)

    def test_should_get_min(self):
        self.assertEqual(1, self.tree.insert(items=[2, 1, 3]).min().item)

    def test_should_get_max(self):
        self.assertEqual(3, self.tree.insert(items=[2, 1, 3]).max().item)

    def test_should_not_get_successor_when_only_1(self):
        self.assertEqual(None, self.tree.insert(1).successor())

    def test_should_not_get_successor_of_max(self):
        self.assertEqual(None, self.tree.insert(items=[2, 1, 3]).right.successor())

    def test_should_get_successor_of_root(self):
        self.assertEqual(3, self.tree.insert(items=[2, 1, 3]).successor().item)

    def test_should_get_successor_of_min(self):
        self.assertEqual(2, self.tree.insert(items=[2, 1, 3]).min().successor().item)

    def test_should_walk_in_order_sequence1(self):
        self.assertEqual("1 2 3 4", self.tree.insert(items=[2, 1, 3, 4]).in_order())

    def test_should_walk_in_order_sequence2(self):
        self.assertEqual("1 2 3 4", self.tree.insert(items=[2, 3, 1, 4]).in_order())

    def test_should_walk_in_order_sequence3(self):
        self.assertEqual("1 2 3 4", self.tree.insert(items=[4, 1, 3, 2]).in_order())

    def test_should_walk_in_order_sequence4(self):
        self.assertEqual("1 2 3 4", self.tree.insert(items=[4, 3, 2, 1]).in_order())

    def test_should_delete_one_and_only_1(self):
        node = self.tree.insert(1).find(1)
        self.assertEquals(node, self.tree.delete(node))

    def test_should_delete_from_left_when_only_3_nodes_in_two_levels(self):
        node = self.tree.insert(items=[2, 1, 3]).find(1)
        self.assertEquals(node, self.tree.delete(node))

    def test_should_delete_from_right_when_only_3_nodes_in_two_levels(self):
        node = self.tree.insert(items=[2, 1, 3]).find(3)
        self.assertEquals(node, self.tree.delete(node))

    def test_should_delete_from_right_subtree(self):
        node = self.tree.insert(items=[2, 1, 3, 4]).find(4)
        self.assertEquals(node, self.tree.delete(node))

    def test_should_delete_from_right_middle_of_subtree(self):
        node = self.tree.insert(items=[2, 1, 3, 4]).find(3)
        self.assertEquals(node, self.tree.delete(node))

