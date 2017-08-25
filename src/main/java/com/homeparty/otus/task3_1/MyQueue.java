package main.java.com.homeparty.otus.task3_1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

/**
 * Created by Глеб Скалацкий
 * Очередь может увеличивать размер, в отличие от стандортной задумки интерфейса java.util.Queue
 */
public class MyQueue<T> implements Queue<T> {
    private T[] elements;
    private int total, first, next;
    public MyQueue() {
        this.elements = (T[]) new Object[5];
    }

    @Override
    public int size() {
        return total;
    }

    @Override
    public boolean isEmpty() {

        if (size() == 0) {
            return true;
        } else {

            return false;
        }
    }

    @Override
    public boolean contains(Object o) {
        for (T element : elements){
            if (element.equals(o)){
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
//        return new Object[0];
        throw new UnsupportedOperationException();
    }

    @Override
    public T[] toArray(Object[] a) {
//        return (T[])new Object[1];
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(Object o) {
        if (elements.length == total) throw new IllegalStateException();
        elements[next++] = (T) o;
        if (next == elements.length) next = 0;
        total++;
        return false;
//        return true;
    }

    private void resize(int capacity)
    {
        T[] tmp = (T[]) new Object[capacity];

        for (int i = 0; i < total; i++)
            tmp[i] = elements[(first + i) % elements.length];

        elements = tmp;
        first = 0;
        next = total;
    }
    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection c) {
        //return false;
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection c) {
        //return false;
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection c) {
//        return false;
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection c) {
//        return false;
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean offer(Object o) {
        if (elements.length == total) resize(elements.length * 2);
        elements[next++] = (T) o;
        if (next == elements.length) next = 0;
        total++;
        return false;
    }

    @Override
    public T remove() {
        if (total == 0) throw new java.util.NoSuchElementException();
        T ele = elements[first];
        elements[first] = null;
        if (++first == elements.length) first = 0;
        if (--total > 0 && total == elements.length / 4) resize(elements.length / 2);
        return ele;
    }

    @Override
    public T poll() {
        if (total == 0) return null;
        T ele = elements[first];
        elements[first] = null;
        if (++first == elements.length) first = 0;
        if (--total > 0 && total == elements.length / 4) resize(elements.length / 2);
        return ele;
    }

    @Override
    public T element() {
        if (total == 0) throw new java.util.NoSuchElementException();
        T ele = elements[first];
        return ele;
    }

    @Override
    public T peek() {
        if (total == 0) return null;
        T ele = elements[first];
        return ele;
    }

}
