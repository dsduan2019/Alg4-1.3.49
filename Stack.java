package ch01.ex1349;

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Stack<Item> implements Iterable<Item>{

    @Override
    public Iterator<Item> iterator() {
        return new StackIterator(first);
    }

    public void clear() {
        cursor = first = null;
        L = N = 0;
    }

    private class StackIterator implements Iterator<Item>{

        Node<Item> current;
        int n_ops;

        public StackIterator(Node<Item> first){
            current = first;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
        }
    }

    private static class Node<Item>{
        Item item;
        Node<Item> next;
        Node(Node<Item> n){
            this.item = n.item;
            this.next = n.next;
        }
        Node(){
            item = null;
            next = null;
        }
    }

    Node<Item> first;
    Node<Item> cursor;
    int N;
    int L;

    public Stack(){
        cursor = first = null;
        L = N = 0;
    }

    public void push(Item item){
        Node<Item> oldfirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldfirst;
        cursor = first;
        N ++;
        L ++;
    }

    public Item pop(){
        if(isempty())
            return null;
        Item item = first.item;
        first = first.next;
        N --;
        if( L > N ) {
            cursor = first;
            L = N;
        }
        return item;
    }

    public Item peak(){
        if(cursor == null)
            return null;
        Item item = cursor.item;
        cursor = cursor.next;
        L --;
        return item;
    }

    public void resetCursor(){
        this.cursor = first;
    }

    public boolean isempty(){
        return first == null;
    }

    public int size(){
        return this.N;
    }

    public int left(){
        return this.L;
    }

    public void print(){
        for(Item item: this){
            StdOut.print(item + " ");
        }
        StdOut.println("] ");
    }

    public static void main(String[] args){
        // unit test
        Stack<Integer> s = new Stack<>();
        int M = 10;
        for(int i = 0; i < M; i ++ ){
            s.push(i + 1);
            s.print();
        }
        for(int i = 0; i < M; i ++ ){
            StdOut.println(s.left());
            s.peak();
            StdOut.println(s.left());
            s.print();
            s.pop();
            s.print();
        }
    }
}
