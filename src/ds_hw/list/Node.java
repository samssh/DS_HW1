package ds_hw.list;

class Node<T> {
    int index;
    T value;
    Node<T> next, perv;

    public Node(int index, T value) {
        this.index = index;
        this.value = value;
    }
}
