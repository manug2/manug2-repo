from unittest import TestCase

from clrs.graphs.dfs import *
from clrs.graphs.graph import Graph

class TestDFS(TestCase):
    def setUp(self):
        pass

    def test_should_be_able_to_visit_a_single_node_graph(self):
        g = Graph().add("s")
        DFS().visit(g)

    def test_should_be_able_to_visit_a_2_node_single_edge_graph(self):
        g = Graph().add("s", "u")
        DFS().visit(g)

    def test_should_have_visited_single_node_in_a_single_node_graph(self):
        g = Graph().add("s")
        forest = DFS().visit(g)
        self.assertTrue(forest.is_visited("s"))

    def test_parent_of_source_node_is_None(self):
        g = Graph().add("s")
        forest = DFS().visit(g)
        self.assertIsNone(forest.parent("s"))

    def test_parent_of_edge_node_is_source_in_a_2_node_1_edge_graph(self):
        g = Graph().add("s", "u")
        forest = DFS().visit(g)
        self.assertEqual("s", forest.parent("u"))

    def test_should_topological_sort_a_DFS_visited_graph_with_1_vertex(self):
        g = Graph().add("s")
        forest = DFS().visit(g)
        self.assertListEqual(["s"], forest.topo_sort())

    def test_parent_of_edge_node_is_source_in_a_4_node_3_edge_flat_graph(self):
        g = Graph().add("s", "u").add("u", "t").add("t", "z")
        forest = DFS().visit(g)
        self.assertEqual("s", forest.parent("u"))
        self.assertEqual("u", forest.parent("t"))
        self.assertEqual("t", forest.parent("z"))

    def test_parent_of_edge_node_is_source_in_a_4_node_3_edge_graph(self):
        g = Graph().add("s", "u").add("u", "t").add("s", "z")
        forest = DFS().visit(g)
        self.assertEqual("s", forest.parent("u"))
        self.assertEqual("u", forest.parent("t"))
        self.assertEqual("s", forest.parent("z"))

    def test_topo_sort_in_a_4_node_3_edge_graph(self):
        g = Graph().add("s", "u").add("u", "t").add("s", "z")
        forest = DFS().visit(g)
        self.assertListEqual(["s", "u", "t", "z"], forest.topo_sort())

    def test_topo_sort_in_a_3_node_2_edge_graph(self):
        g = Graph().add("s", "u").add("u", "t")
        forest = DFS().visit(g)
        self.assertListEqual(["s", "u", "t"], forest.topo_sort())
