package oy.interact.tira.student;

import oy.interact.tira.util.QueueInterface;

public class LinkedListQueue<E> implements QueueInterface<E> {
    /*
     * capacity(): O(1). (DONE)
     * 
     * enqueue(): O(1) paitsi kun/jos joudutaan reallokoimaan taulukkoratkaisussa:
     * O(n).
     * 
     * dequeue(): O(1).
     * 
     * element(): O(1).
     * 
     * size(): O(1).
     * 
     * isEmpty(): O(1).
     * 
     * clear(): O(1).
     * 
     * toString(): O(n).
     */

    // ******************
    // Attributes
    // ******************
    private class Node<T> {
        T data;
        Node<T> next;
        Node<T> previous;

        public Node(T data) {
            this.data = data;
        }
    }

    private Node<E> head;
    private Node<E> tail;
    private int size;

    // ******************
    // Contructors
    // ******************
    public LinkedListQueue() {
        this(0);
    }

    public LinkedListQueue(int parameterCapacity) {
        head = null; // Alustetaan pää null-arvoon (tyhjä jono).
        tail = null; // Alustetaan häntä null-arvoon (tyhjä jono).
        size = parameterCapacity; // Alustetaan koko arvoon 0 (tyhjä jono) tai valmiiksi.
    }

    // ******************
    // Methods
    // ******************

    @Override
    public int capacity() {
        return this.size;
    }

    @Override
    public void enqueue(E element) {
        if (element == null) {
            throw new NullPointerException("Element cannot be null");
        }

        Node<E> newNode = new Node<>(element);

        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public E dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot dequeue because the queue is empty");
        }

        Node<E> removedNode = head;

        if (head.next != null) {
            head.next.previous = null;
        } else {
            tail = null;
        }

        head = head.next;
        removedNode.next = null;

        size--;

        return removedNode.data;
    }

    @Override
    public E element() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot retrieve the element because the queue is empty");
        }

        return head.data;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }
}