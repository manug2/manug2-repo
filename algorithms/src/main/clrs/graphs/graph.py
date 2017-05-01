
class Graph:
    def __init__(self):
        self.V = []
        self.E = {}

    def contains(self, node):
        return node in self.V

    def add(self, source, dest=None):
        if source not in self.V:
            self.V.append(source)

        if dest is not None:
            if dest not in self.V:
                self.V.append(dest)

            if self.has_edges_for(source) and dest in self.E[source]:
                pass
            else:
                self.add_edge(source, dest)

        return self

    def add_edge(self, source, dest):
        if source not in self.E:
            self.E[source] = []
        adj = self.E[source]

        if dest not in adj:
            adj.append(dest)

    def edges(self, node):
        if node in self.E:
            return self.E[node]
        else:
            return None

    def has_edges_for(self, node):
        return node in self.E
