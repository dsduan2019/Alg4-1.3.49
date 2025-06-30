# Alg4-1.3.49 《算法（第四版）》1.3.49
Implement a deque with limited stacks so that each deque operation takes a constant number of stack operations in the worst case.
有限个栈实现一个队列，保证每个队列操作（在最坏情况下）都只需要常数次栈操作。

以下是主要代码具体介绍。

一、使用的栈、变量及其作用

Head栈（头栈）：用于出队，Head栈的栈顶是下一个出队的元素。

Tail栈（尾栈）：用于入队，通过将元素压入Tail栈进行入队。

Hbuf栈（头缓冲栈）：Head栈的缓冲。

Hrev栈（头逆序栈）：同Head栈中的元素顺序相反。

Tbuf栈（尾缓冲栈）：Tail栈的缓冲。

N：队列中的元素总数。

M：队列中除Tbuf栈中元素的个即，M = N - Tbuf.size()






References:

https://blog.csdn.net/MaaaMalik/article/details/94163051

https://www.cnblogs.com/dacc123/p/10574939.html
