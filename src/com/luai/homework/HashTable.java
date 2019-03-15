package com.luai.homework;

import java.util.HashMap;
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

    public void put(int key, E value) {
        if ((double) this.size / this.table.length >= this.loadFactor)
            resize();

        LinkedList<Node<E>> nodeList = this.table[key % this.table.length];

        if (nodeList == null)
            nodeList = new LinkedList<Node<E>>();

        nodeList.add(new Node<E>(key, value));

        this.table[key % this.table.length] = nodeList;
        this.size++;
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

    public int size() {
        return this.size;
    }

    private void resize() {
        LinkedList<Node<E>>[] newTable = (LinkedList<Node<E>>[]) new LinkedList[this.table.length * 2];

        for (LinkedList<Node<E>> nodeList: this.table) {
            if (nodeList == null)
                continue;

            for (Node<E> node : nodeList) {
                LinkedList<Node<E>> newNodeList = newTable[node.key % newTable.length];

                if (newNodeList == null)
                    newNodeList = new LinkedList<Node<E>>();

                newNodeList.add(new Node<E>(node.key, node.value));

                newTable[node.key % newTable.length] = newNodeList;
            }
        }

        this.table = newTable;
    }

}
