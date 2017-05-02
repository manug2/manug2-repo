from unittest import TestCase
from clrs.graphs.graph import Graph


class TestGraph(TestCase):

    def setUp(self):
        pass

    def test_create(self):
        Graph()

    def test_contains_source_node(self):
        g = Graph().add("s")
        self.assertTrue(g.contains("s"))

    def test_add_edge(self):
        g = Graph().add("s", "u")
        self.assertTrue(g.contains("s"))
        self.assertTrue(g.contains("u"))

    def test_list_containing_source_node_is_same_as_vertices(self):
        g = Graph().add("s")
        self.assertListEqual(["s"], g.vertices())

    def test_list_containing_the_only_2_nodes_is_same_as_vertices(self):
        g = Graph().add("s", "u")
        self.assertListEqual(["s", "u"], g.vertices())
