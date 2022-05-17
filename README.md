# Kruskals Algorithm

## Implementation

This is a maze generator using the Kruskals algorithm.

The algorithm is based on the idea that the maze is a graph, and the edges are the walls.
The pseudocode for this algorithm is as follows:

```{.pseudocode}
function kruskal(graph)
  let forest = []
  for each vertex v in graph.vertices
    forest.add(v)
  let edges = graph.edges
  edges.sort(by: <)
  for each edge e in edges
    let u = e.u
    let v = e.v
    if u.parent != v.parent
      forest.remove(u.parent)
      forest.remove(v.parent)
      u.parent = v.parent
      forest.add(u.parent)
  return forest
```