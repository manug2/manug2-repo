from clrs.graphs.graph import Graph
from math import floor

WHITE=0
GRAY=1
BLACK=2

class DFS:
    def __init__(self):
        pass

    def visit(self, g):
        forest = Forest()
        for u in g.vertices():
            if forest.is_white(u):
                self._visit(g, forest, u)

        return forest

    def _visit(self, g, forest, u):
        forest.start(u)
        if g.has_edges_for(u):
            for v in g.edges(u):
                if forest.is_white(v):
                    forest.parents[v] = u
                    self._visit(g, forest, v)
        forest.end(u)


class Forest:
    def __init__(self):
        self.visited = []
        self.parents = {}
        self.colors = {}
        self.starts = {}
        self.ends = {}
        self.ts=0
        self.topological = []

    def start(self, u):
        self.colors[u] = GRAY
        self.ts+=1
        self.starts[u] = self.ts

    def end(self, u):
        self.colors[u] = BLACK
        self.ts+=1
        self.ends[u] = self.ts
        self.topological.append(u)

    def is_white(self, v):
        return v not in self.colors

    def is_visited(self, v):
        return v in self.colors and self.colors[v] == BLACK

    def parent(self, v):
        if v in self.parents:
            return self.parents[v]
        return None

    def topo_sort(self):
        return self.topological

