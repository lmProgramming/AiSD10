package DisjointSet;

public interface IDisjointSetStructure<T> {
    T findSet(T item) throws ItemOutOfRangeException;
    void union(T item1, T item2) throws ItemOutOfRangeException;
}
