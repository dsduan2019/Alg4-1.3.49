# Alg4-1.3.49 《算法（第四版）》1.3.49
Implement a deque with limited stacks so that each deque operation takes a constant number of stack operations in the worst case.
有限个栈实现一个队列，保证每个队列操作（在最坏情况下）都只需要常数次栈操作。

以下是主要代码具体介绍。

## 一、使用的栈及变量

Head栈（头栈）：用于出队，Head栈的栈顶是下一个出队的元素。

Tail栈（尾栈）：用于入队，通过将元素压入Tail栈进行入队。

Hbuf栈（头缓冲栈）：Head栈的缓冲。

Hrev栈（头逆序栈）：同Head栈中的元素顺序相反。

Tbuf栈（尾缓冲栈）：Tail栈的缓冲。

N：队列中的元素总数。

M：队列中除Tbuf栈中元素的个数，即M = N - Tbuf.size()

## 二、入队操作介绍

第一步: 入队首先需要将元素压入尾栈，可能是Tail栈（尾栈）或Tbuf栈（尾栈的缓冲），根据Hbuf栈（头缓冲栈）是否为空来确定是压入Tail栈还是压入Tbuf栈，因为Hbuf栈是作为头缓冲栈，其后续会通过交换会成为Head栈，所以其栈顶元素可能需要先出队，而后面Tail栈会往Hbuf栈压入元素，如果Hbuf栈不为空，就先把入队元素暂存在Tbuf中，因为放入Tail栈也会面临无法压入Hbuf的局面。入队的第一步代码如下，其中当压入Tail栈时需要对M进行自增，而压入Tbuf栈时无需对M进行自增（原因见上述M的定义）。

        if(Hbuf.isempty()){

            Tail.push(item);
            
            M++;
            
        }else{
        
            Tbuf.push(item);
            
        }

第二步：只要Tail不为空，则将Tail栈中的元素转移到Hbuf，其中有一个常数次(SPEED)的for循环，该循环是为了加速Tail栈中的元素向Hbuf的转移，因为Hbuf是Head的缓冲，所以终是为了加速Tail到Head的转移，将元素从Tail往Head转移是该算法的一个重要的设计原则，能够有效保证出队时通过简单的弹出Head顶层元素即可完成，虽然此处有一个for循环，但次数是有限的且固定的，因此保证入队时栈操作仍然是常数次。入队的第二步代码如下。

        for(int i = 0; i < SPEED; i ++){
        
            if(!Tail.isempty())
            
                poppush(Tail, Hbuf);
                
        }

第三步：将Head中的元素转移到Hrev，此处转移并不实际弹出Head中的元素，只是通过游标(cursor)将head栈中的元素逐个读取而不弹出(peak)再压入Hrev，因此在栈的实现中引入cursor变量和peak方法，并且引入变量L用于记录peak后剩余的元素个数，并通过栈的left方法返回私有变量L的值。因此当Head中还存在未peak的值，即Head.left()>0，则将Head中的元素peak并压入（push）到Hrev，即以下代码。

        if(Head.left() > 0){
        
            peakpush(Head, Hrev);
            
        }

第四步：将Hrev中的元素转移到Hbuf，这里需要满足几个条件，首先Head中的元素全部转移到Hrev中（Head.left()==0），否则后续Hbuf元素顺序错乱，其次Tail为空（Tail.isempty()），否则按照第二步，Tail也会将元素转移到Hbuf，从而造成Hbuf中元素顺序错乱，最后Hrev不为空，这个比较直观，Hrev为空则没有任何元素需要转移。代码如下：

        if(Head.left() == 0 && Tail.isempty() && !Hrev.isempty()){
        
                poppush(Hrev, Hbuf);
                
        }

第五步：栈交换，用Head缓冲栈替换Head栈，并清空Head缓冲栈，用Tbuf缓冲栈替换Tail栈，并清空Tbuf栈，在进行栈交换时使用的临时栈Temp，在两种情况下需要进行栈交换。一种情况是Head栈和Tail栈均为空，这时Head没有可出栈的，Hbuf和Head交换, 一但Hbuf为空，Tbuf和Tail交换，这时Tail也可以继续往Hbuf转移元素。另一种情况是Hbuf中存放了队列中除Tbuf以外的所有元素（Hbuf.size() == M），因些将过将Hbuf和Head交换，Tbuf和Tail交换，可以保证Head和Tail刚好保存了队列中所有的元素，并且顺序正确。代码如下：

        if(Head.isempty() && Tail.isempty() || (Hbuf.size() == M)){
        
            swap();
            
        }

## 二、出队操作

第一步：如果Head栈为空，则表明队列为空，因此返回null，否则弹出Head栈顶元素进行出队操作，出队后M需要自减。代码如下：

        if(Head.isempty())
        
            return null;
            
        Item item = Head.pop();
        
        M--;

第二步：栈交换，出队提前进行栈交换，是因为M通过自减变小后，Hbuf中存放了队列中除Tbuf以外的所有元素（Hbuf.size() == M）可能会满足，此时需要立即进行栈交换，防止元素顺序出现错乱。代码如下：
        
        if(Hbuf.size() == M){
        
            swap();
            
        }





References:

https://blog.csdn.net/MaaaMalik/article/details/94163051

https://www.cnblogs.com/dacc123/p/10574939.html
