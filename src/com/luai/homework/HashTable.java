package com.luai.homework;

import java.util.LinkedList;

public class HashTable<E> {

    class Node<E> {

        int key;
        E value;

        Node(int key, E value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public boolean equals(Object node) {
            return node instanceof Node && this.key == ((Node) node).key;
        }

        @Override
        public String toString() {
            return key + ": " + value.toString();
        }
    }

    private LinkedList<Node<E>>[] table;
    private int size;
    private float loadFactor;

    public HashTable(int initialCapacity, float loadFactor) {
        if (initialCapacity <= 0)
            throw new IllegalArgumentException("Illegal initial capacity " +
                    initialCapacity + ". Must be positive");
        if (loadFactor <= 0 || loadFactor > 1.0f)
            throw new IllegalArgumentException("Illegal load factor " +
                    loadFactor + ". Must be positive and less than or equal to 1.0");

        this.table = (LinkedList<Node<E>>[]) new LinkedList[initialCapacity];
        this.size = 0;
        this.loadFactor = loadFactor;
    }

    public E get(int key) {
        LinkedList<Node<E>> nodeList = this.table[key % this.table.length];

        if (nodeList == null)
            return null;

        for (Node<E> node: nodeList)
            if (node.key == key)
                return node.value;

        return null;
    }

    public void delete(int key) {
        LinkedList<Node<E>> nodeList = this.table[key % this.table.length];

        if (nodeList == null)
            return;

        if (nodeList.remove(new Node<E>(key, null)))
            this.size--;
    }

    public void put(int key, E value) {
        if ((double) this.size / this.table.length >= this.loadFactor)
            resize();

        put(key, value, this.table);
        this.size++;
    }

    public int size() {
        return this.size;
    }

    private void put(int key, E value, LinkedList<Node<E>>[] table) {
        LinkedList<Node<E>> nodeList = table[key % table.length];

        if (nodeList == null)
            table[key % table.length] = nodeList = new LinkedList<Node<E>>();

        nodeList.add(new Node<E>(key, value));
    }

    private void resize() {
        LinkedList<Node<E>>[] newTable = (LinkedList<Node<E>>[]) new LinkedList[this.table.length * 2];

        for (LinkedList<Node<E>> nodeList: this.table) {
            if (nodeList == null)
                continue;

            for (Node<E> node : nodeList)
                put(node.key, node.value, newTable);
        }

        this.table = newTable;
    }

}
