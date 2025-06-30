package ch01.ex1349;

import edu.princeton.cs.algs4.StdOut;

public class StackQueue<Item>{
    private Stack<Item> Head = new Stack<Item>();
    private Stack<Item> Tail = new Stack<Item>();
    private Stack<Item> Hbuf = new Stack<Item>();
    private Stack<Item> Hrev = new Stack<Item>();
    private Stack<Item> Tbuf = new Stack<Item>();
    private Stack<Item> temp;

    // the number of elements in the queue
    private int N = 0;
    // the number of elements except in Tbuf
    private int M = 0;
    // speed up the process of transferring elements from tail to head in constant ops each step
    private final int SPEED = 2;

    private void swap() {
        Head.clear(); temp = Head; Head = Hbuf; Hbuf = temp;
        Tail.clear(); temp = Tail; Tail = Tbuf; Tbuf = temp;
        M = Head.size() + Tail.size();
        Head.resetCursor();
        Hrev.clear();
    }

    private void peakpush(Stack<Item> s1, Stack<Item> s2) {
        if(!s1.isempty() && s1.left() > 0) {
            s2.push(s1.peak());
        }
    }

    public void poppush(Stack<Item> s1, Stack<Item> s2){
        s2.push(s1.pop());
    }

    public void enqueue(Item item){
        if(Hbuf.isempty()){
            Tail.push(item);
            M++;
        }else{
            Tbuf.push(item);
        }
        for(int i = 0; i < SPEED; i ++){
            if(!Tail.isempty())
                poppush(Tail, Hbuf);
        }
        if(Head.left() > 0){
            peakpush(Head, Hrev);
        }
        if(Head.left() == 0 && Tail.isempty() && !Hrev.isempty()){
                poppush(Hrev, Hbuf);
        }
        if(Head.isempty() && Tail.isempty() || (Hbuf.size() == M)){
            swap();
        }
        N ++;
    }

    public Item dequeue() {
        if(Head.isempty() && N == 0)
            return null;
        Item item = Head.pop();
        M--;
        if(Hbuf.size() == M){
            swap();
        }
        for(int i = 0; i < SPEED; i ++){
            if(!Tail.isempty())
                poppush(Tail, Hbuf);
        }
        if(Head.left() > 0){
            peakpush(Head, Hrev);
        }
        if(Head.left() == 0 && Tail.isempty() && !Hrev.isempty()){
                poppush(Hrev, Hbuf);
        }
        if(Tail.isempty() && Head.isempty() || (Hbuf.size() == M)){
            swap();
        }
        N --;
        return item;
    }

    public void print(){
        StdOut.print("Head: ");
        Head.print();
        StdOut.print("Tail: ");
        Tail.print();
        StdOut.print("Hbuf: ");
        Hbuf.print();
        StdOut.print("Hrev: ");
        Hrev.print();
        StdOut.print("Tbuf: ");
        Tbuf.print();
        StdOut.print("N: ");
        StdOut.println(N);
        StdOut.print("M: ");
        StdOut.println(M);
        StdOut.println("**********************");
    }

    public static void main(String[] args){
        // unit test
        StackQueue<Integer> s = new StackQueue<Integer>();
        final int E = 3;
        final int M = 800;
        final int N = 400;
        int R = 0;
        int epoch;
        for(epoch = 0; epoch < E; epoch ++){
            int i;
            for(i = M * epoch; i < (epoch + 1) * M; i ++){
                s.enqueue(i + 1);
                s.print();
            }
            for(i = 0; i < (epoch + 1) * N; i ++){
                int e = s.dequeue();
                R++;
                StdOut.println("dequeue: " + e);
                s.print();
                if( R != e ) break;
            }
            if( i < (epoch + 1) * N ) break;
        }
        if( epoch < E ){
            StdOut.print("Failed Test!!!");
        }else{
            StdOut.print("Passed Test!!!");
        }
    }

}
