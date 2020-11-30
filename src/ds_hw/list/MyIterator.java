package ds_hw.list;

public class MyIterator<T> implements java.util.Iterator<T> {
    final LList<T> list;
    private Node<T> node, last;

    public MyIterator(Node<T> node, LList<T> list) {
        this.node = node;
        this.list = list;
    }

    @Override
    public boolean hasNext() {
        return node != null;
    }

    @Override
    public T next() {
        T t = node.value;
        last = node;
        node = node.next;
        return t;
    }

    @Override
    public void remove() {
        list.remove(last);
    }

    public void set(T t) {
        last.perv.value = t;
    }
}
