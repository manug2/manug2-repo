from queue import Queue


WHITE = 0
GRAY = 1
BLACK = 2


class BFS:

    def __init__(self):
        pass

    def visit(self, g, s):
        queue = Queue()

        queue.put(s)
        result = ResultBFS().append(s)
        while not queue.empty():
            u = queue.get()
            if g.has_edges_for(u):
                for v in g.edges(u):
                    if result.is_white(v):
                        result.append(v, u)
                        queue.put(v)
            result.finish(u)

        result.colors[s] = BLACK
        return result


class ResultBFS:
    def __init__(self):
        self.parent = {}
        self.colors = {}
        self.D = {}

    def distance(self, node):
        return self.D[node]

    def is_visited(self, node):
        return node in self.colors and self.colors[node] == BLACK

    def is_white(self, node):
        return node not in self.colors or self.colors[node] == WHITE

    def append(self, child, parent=None):
        self.colors[child] = GRAY
        if parent is None or parent not in self.D:
            self.D[child] = 0
        else:
            self.D[child] = self.D[parent] + 1

        self.parent[child] = parent
        return self

    def finish(self, u):
        self.colors[u] = BLACK


