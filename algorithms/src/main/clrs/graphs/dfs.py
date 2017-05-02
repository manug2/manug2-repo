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

    def start(self, u):
        self.colors[u] = GRAY
        self.ts+=1
        self.starts[u] = self.ts

    def end(self, u):
        self.colors[u] = BLACK
        self.ts+=1
        self.ends[u] = self.ts

    def is_white(self, v):
        return v not in self.colors

    def is_visited(self, v):
        return v in self.colors and self.colors[v] == BLACK

    def parent(self, v):
        if v in self.parents:
            return self.parents[v]
        return None

    def topo_sort(self):
        sorter = TopoSort(self.ends)
        return sorter.sort()


class TopoSort:
    def __init__(self, ends):
        print (ends)
        self.ends = {}
        self.series = []

        for v in ends:
            end = ends[v]
            self.series.append(end)
            self.ends[end] = v
        print (self.ends)
        self.result = [0]*len(self.ends)

    def sort(self):
        result = self._sort(0, len(self.ends))
        result_v = []
        for i in result:
            result_v.append(self.ends[i])
        return result_v

    def _sort(self, lo, hi):
        if lo >= hi-1:
            #self.result[lo] = self.series[lo]
            return [self.series[lo]]

        mid = int(floor((lo + hi)/2))
        left = self._sort(lo, mid)
        right = self._sort(mid, hi)
        #return self._merge(lo, mid, hi, left, right)
        return self.__merge(left, right)

    def __merge(self, left, right):
        i=0
        j=0
        k=0
        total_len = len(left) + len(right)
        result = [0]*(total_len)

        while k < total_len:
            if i < len(left):
                if j==len(right) or left[i] > right[j]:
                    result[k] = left[i]
                    i+=1
                else:
                    result[k] = right[j]
                    j += 1
            else:
                result[k] = right[j]
                j+=1
            k+=1
        return result


    def _merge(self, lo, mid, hi, left, right):
        i=lo
        j=mid
        k=lo
        result = [0]*(hi-lo)
        while k < hi:
            if i <= mid:
                if self.series[i] < self.series[j] or j==hi:
                    result[k] = self.series[i]
                    i+=1
                else:
                    result[k] = self.series[j]
                    j += 1
            else:
                result[k] = self.series[j]
                j+=1
            k+=1
        return result
