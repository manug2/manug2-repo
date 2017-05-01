from clrs.graphs.bfs import BFS, ResultBFS
from clrs.graphs.graph import Graph
from unittest import TestCase


class TestBFS(TestCase):

    def setUp(self):
        pass

    def test_invoke_BFS(self):
        g = Graph()
        BFS().visit(g, "s")

    def test_invoking_BFS_makes_source_node_visited(self):
        g = Graph()
        result = BFS().visit(g, "s")
        self.assertTrue(result.is_visited("s"))

    def test_invoking_BFS_makes_source_and_node_visited(self):
        g = Graph().add("s", "u")
        result = BFS().visit(g, "s")
        self.assertTrue(result.is_visited("s"))
        self.assertTrue(result.is_visited("u"))

    def test_invoking_BFS_makes_source_distance_0(self):
        g = Graph()
        result = BFS().visit(g, "s")
        self.assertEqual(0, result.distance("s"))

    def test_invoking_BFS_makes_source_and_node_distances_0_and_1_respectively(self):
        g = Graph().add("s", "u")
        result = BFS().visit(g, "s")
        self.assertEqual(0, result.distance("s"))
        self.assertEqual(1, result.distance("u"))

    def test_invoking_BFS_makes_source_and_edge_nodes_distances_0_and_1_respectively(self):
        g = Graph().add("s", "u").add("s", "t")
        result = BFS().visit(g, "s")
        self.assertEqual(0, result.distance("s"))
        self.assertEqual(1, result.distance("u"))
        self.assertEqual(1, result.distance("t"))

    def test_invoking_BFS_makes_nested_node_distances_2(self):
        g = Graph().add("s", "u").add("u", "t")
        result = BFS().visit(g, "s")
        self.assertEqual(2, result.distance("t"))

    def test_invoking_BFS_does_not_visit_unconnected_node(self):
        g = Graph().add("s", "u").add("t")
        result = BFS().visit(g, "s")
        self.assertFalse(result.is_visited("t"))
