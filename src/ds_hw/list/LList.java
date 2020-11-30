package ds_hw.list;


import java.util.Comparator;
import java.util.Iterator;
import java.util.function.BiPredicate;

public class LList<T> implements Iterable<T> {
    private Node<T> first, last;
    private int size;

    public LList() {
        size = 0;
        first = last = null;
    }

    public int size() {
        return size;
    }

    public void addLast(T t) {
        Node<T> node = new Node<>(size, t);
        if (size == 0) {
            first = last = node;
        } else {
            node.perv = last;
            last = last.next = node;
        }
        size++;
    }

    void remove(Node<T> node) {
        if (node.next == null) {
            last = node.perv;
        } else {
            node.next.perv = node.perv;
        }
        if (node.perv == null) {
            first = node.next;
        } else {
            node.perv.next = node.next;
        }
        size--;
    }

    public T first() {
        return first.value;
    }

    public T get(int i) {
        if (i < 0 || size <= i) throw new IndexOutOfBoundsException();
        Iterator<T> iterator = iterator();
        for (int j = 0; j < i; j++) {
            iterator.next();
        }
        return iterator.next();
    }

    public <R> boolean contains(BiPredicate<T, R> predicate, R r) {
        for (T t : this) {
            if (predicate.test(t, r)) return true;
        }
        return false;
    }

    @Override
    public MyIterator<T> iterator() {
        return new MyIterator<>(first, this);
    }

    public void addAll(LList<T> lList) {
        for (T t : lList) this.addLast(t);
    }

    public void sort(Comparator<T> comparator) {
        first = sort(first, comparator);
        size = 0;
        for (Node<T> i = first; i != null; i = i.next) {
            last = i;
            i.index = size++;
        }
    }

    Node<T> sort(Node<T> first, Comparator<T> comparator) {
        if (first == null) return null;
        if (first.next == null) return first;
        Node<T> middle = split(first);
        first = sort(first, comparator);
        middle = sort(middle, comparator);
        first = merge(first, middle, comparator);
        return first;
    }

    private Node<T> split(Node<T> first) {
        assert first != null;
        Node<T> middle = first;
        boolean a = false;
        for (Node<T> last = first; last != null; last = last.next) {
            if (a) {
                middle = middle.next;
            }
            a = !a;
        }
        middle.perv = middle.perv.next = null;
        return middle;
    }

    private Node<T> merge(Node<T> first, Node<T> middle, Comparator<T> comparator) {
        Node<T> i = first, j = middle;
        while (j != null) {
            Node<T> temp = j.next;
            if (comparator.compare(i.value, j.value) > 0) {
                if (i.perv != null) {
                    i.perv.next = j;
                } else {
                    first = j;
                }
                j.perv = i.perv;
                j.next = i;
                i.perv = j;
            } else {
                if (i.next != null) {
                    i = i.next;
                    continue;
                } else {
                    j.next = null;
                    j.perv = i;
                    i.next = j;
                }
            }
            j = temp;
        }
        return first;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (T t : this) {
            builder.append(t);
            builder.append(",\n");
        }
        if (builder.length() > 1)
            builder.delete(builder.length() - 2, builder.length());
        builder.append("}");
        return builder.toString();
    }
}