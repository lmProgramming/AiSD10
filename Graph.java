import DisjointSet.DisjointSetForest;
import DisjointSet.ItemOutOfRangeException;

import java.lang.reflect.Array;
import java.util.*;

public class Graph<T>
{
    private int[][] adjacencyMatrix;
    private List<T> vertices;

    public Graph(List<Edge<T>> edges)
    {
        vertices = new ArrayList<>();
        for (Edge<T> edge : edges)
        {
            if (!vertices.contains(edge.getSource()))
            {
                vertices.add(edge.getSource());
            }
            if (!vertices.contains(edge.getDestination()))
            {
                vertices.add(edge.getDestination());
            }
        }

        int vertexCount = vertices.size();
        adjacencyMatrix = new int[vertexCount][vertexCount];

        for (Edge<T> edge : edges)
        {
            int sourceIndex = vertices.indexOf(edge.getSource());
            int destinationIndex = vertices.indexOf(edge.getDestination());
            adjacencyMatrix[sourceIndex][destinationIndex] = 1;
        }
    }

    public String depthFirst(T startNode) throws NoSuchElementException {
        int startIndex = vertices.indexOf(startNode);
        if (startIndex == -1)
        {
            throw new NoSuchElementException("Wierzchołek nie istnieje w grafie.");
        }

        boolean[] visited = new boolean[vertices.size()];

        ArrayList<T> result = new ArrayList<>();

        depthFirstTraversal(startIndex, visited, result);

        return String.join(", ", listToString(result));
    }

    private void depthFirstTraversal(int vertexIndex, boolean[] visited, ArrayList<T> result)
    {
        visited[vertexIndex] = true;
        result.add(vertices.get(vertexIndex));

        for (int i = 0; i < vertices.size(); i++)
        {
            if (adjacent(vertexIndex, i) && !visited[i])
            {
                depthFirstTraversal(i, visited, result);
            }
        }
    }

    public String breadthFirst(T startNode) throws NoSuchElementException
    {
        int startIndex = vertices.indexOf(startNode);
        if (startIndex == -1)
        {
            throw new NoSuchElementException();
        }

        boolean[] visited = new boolean[vertices.size()];
        ArrayList<T> result = new ArrayList<T>();

        Queue<Integer> queue = new LinkedList<>();
        queue.add(startIndex);
        visited[startIndex] = true;

        while (!queue.isEmpty())
        {
            int vertexIndex = queue.poll();
            result.add(vertices.get(vertexIndex));

            for (int i = 0; i < vertices.size(); i++)
            {
                if (adjacent(vertexIndex, i) && !visited[i])
                {
                    queue.add(i);
                    visited[i] = true;
                }
            }
        }

        return String.join(", ", listToString(result));
    }

    public boolean adjacent(int vertexIndex1, int vertexIndex2)
    {
        return adjacencyMatrix[vertexIndex1][vertexIndex2] == 1;
    }

    public int connectedComponents()
    {
        DisjointSetForest<T> disjointSet = new DisjointSetForest<>();

        for (T vertex : vertices)
        {
            disjointSet.makeSet(vertex);
        }

        for (int i = 0; i < vertices.size(); i++)
        {
            for (int j = 0; j < vertices.size(); j++)
            {
                if (!adjacent(i, j))
                {
                    try
                    {
                        disjointSet.union(vertices.get(i), vertices.get(j));
                    }
                    catch (ItemOutOfRangeException e)
                    {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        return disjointSet.getNumberOfSets();
    }

    public ArrayList<String> listToString(ArrayList<T> list)
    {
        ArrayList<String> strings = new ArrayList<>();
        for (T val : list)
        {
            strings.add(val.toString());
        }

        return strings;
    }
}
