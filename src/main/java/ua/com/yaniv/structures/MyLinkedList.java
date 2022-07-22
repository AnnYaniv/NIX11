package ua.com.yaniv.structures;

import lombok.Getter;
import lombok.Setter;
import ua.com.yaniv.model.Product;
import ua.com.yaniv.model.VersionedProduct;

import java.util.Date;
import java.util.Iterator;
import java.util.Optional;

public class MyLinkedList<E extends Product> implements Iterable<VersionedProduct<E>> {

    private Node<E> head;
    private Node<E> tail;

    private int size = 0;

    public void add(E data, int version) {
        Node<E> node = new Node<>(data, version, head);

        if (head == null)
            tail = head = node;
        else {
            head.setPrevious(node);
            head = node;
        }

        size++;
    }

    public boolean deleteByVersion(int version) {
        boolean result = false;
        Node<E> node = head;
        while (node != null) {

            if (node.getData().getVersion() == version) {
                unlink(node);
                size--;
                result = true;
                break;
            }
            node = node.getNext();
        }
        return result;
    }

    public int getSize() {
        return size;
    }

    private void unlink(Node<E> node) {
        Node<E> previous = node.getPrevious();
        Node<E> next = node.getNext();

        if (node == head) {
            head = next;
            if (next != null) next.setPrevious(null);
            return;
        }
        if (node == tail) {
            tail = previous;
            if (previous != null) previous.setNext(null);
            return;
        }

        previous.setNext(next);
    }

    public boolean updateByVersion(int version, E product) {
        boolean result = false;
        Node<E> node = head;
        while (node != null) {
            if (node.getData().getVersion() == version) {
                node.getData().setProduct(product);
                result = true;
                break;
            }
            node = node.getNext();
        }
        return result;
    }

    public Optional<VersionedProduct<E>> findByVersion(int version) {
        Node<E> node = head;
        while (node != null) {
            if (node.getData().getVersion() == version) {
                return Optional.of(node.getData());
            }

            node = node.getNext();
        }
        return Optional.empty();
    }

    public int countVersionsById(String id) {
        int count = 0;
        Node<E> node = head;
        while (node != null) {
            if (node.getData().getId().equals(id)) {
                count++;
            }
            node = node.getNext();
        }
        return count;
    }

    public Optional<Date> firstVersionDateOfProductById(String id) {
        Date date = null;

        Node<E> node = head;
        while (node != null) {
            if (node.getData().getId().equals(id)) {
                if (date == null) date = node.getData().getDate();
                if (date.after(node.getData().getDate())) date = node.getData().getDate();
            }
            node = node.getNext();
        }
        return Optional.ofNullable(date);
    }

    public Optional<Date> lastVersionDateOfProductById(String id) {
        Date date = null;

        Node<E> node = head;
        while (node != null) {
            if (node.getData().getId().equals(id)) {
                if (date == null) date = node.getData().getDate();
                if (date.before(node.getData().getDate())) date = node.getData().getDate();
            }
            node = node.getNext();
        }
        return Optional.ofNullable(date);
    }

    public Node<E> getHead() {
        return head;
    }

    public Node<E> getTail() {
        return tail;
    }

    public Iterator<VersionedProduct<E>> iterator() {
        return new ListIterator<>(this);
    }


    class ListIterator<V extends Product> implements Iterator<VersionedProduct<V>> {

        private MyLinkedList<V>.Node<V> current;

        public ListIterator(MyLinkedList<V> list) {
            current = list.getHead();
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public VersionedProduct<V> next() {
            VersionedProduct<V> product = current.getData();
            current = current.getNext();
            return product;
        }
    }

    @Setter
    @Getter
    class Node<V extends Product> {
        private VersionedProduct<V> data;
        private Node<V> next;
        private Node<V> previous;

        public Node(V product, int version, Node<V> next) {
            this.data = new VersionedProduct<>(product, version);
            this.next = next;
        }
    }

}
