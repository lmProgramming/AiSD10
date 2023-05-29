package DisjointSet;

import java.util.ArrayList;

public class DisjointSetForest<T> implements IDisjointSetStructure<T>
{
    private final ArrayList<Node<T>> nodes;
    private int numSets;

    public DisjointSetForest()
    {
        nodes = new ArrayList<>();
        numSets = 0;
    }

    public void makeSet(T item)
    {
        if (getNode(item) == null)
        {
            Node<T> node = new Node<>(item);
            node.parent = node;
            node.rank = 0;
            nodes.add(node);
            numSets++;
        }
    }

    @Override
    public T findSet(T item)
    {
        Node<T> node = findNode(getNode(item));
        return node.item;
    }

    private Node<T> findNode(Node<T> node)
    {
        if (node.parent != node)
        {
            node.parent = findNode(node.parent);
        }
        return node.parent;
    }

    public Node<T> getNode(T item)
    {
        for (Node<T> node : nodes)
        {
            if (node.item == item)
            {
                return node;
            }
        }
        return null;
    }

    @Override
    public void union(T item1, T item2) throws ItemOutOfRangeException
    {
        Node<T> root1 = findNode(getNode(item1));
        Node<T> root2 = findNode(getNode(item2));

        if (root1 == null || root2 == null) {
            throw new ItemOutOfRangeException();
        }

        if (root1 != root2)
        {
            if (root1.rank < root2.rank)
            {
                root1.parent = root2;
            }
            else if (root1.rank > root2.rank)
            {
                root2.parent = root1;
            }
            else
            {
                root2.parent = root1;
                root1.rank++;
            }
            numSets--;
        }
    }

    public int getNumberOfSets()
    {
        return numSets;
    }

    private static class Node<T>
    {
        T item;
        Node<T> parent;
        int rank;

        public Node(T item)
        {
            this.item = item;
            this.parent = this;
            this.rank = 0;
        }
    }
}