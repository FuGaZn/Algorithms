# 第一章 基础
//这章讲的是Java基础和java里基本数据结构的实现
## 1.1
问：Java表达式1/0和1.0/0.0的值是什么？<br>
答：第一个表达式会产生一个除零异常；第二个表达式的值是Infinity<br>

问：负数的除法和余数的结果是什么？<br>
答：a/b的商会向零取整；a%b的定义是 **(a/b) * b + a %  b**恒等于a。例如，-14/3和14/-3的商都是-4，但是-14%3的值是-2，而14%-3的值是2
<br>

System.out.println('b');    //输出b<br>
System.out.println('a' + 'c');  //输出196

## 1.2
对象是能够承载数据类型的值的实体。所有对象都有三大重要特性：状态、标识、行为。<br>
状态就是数据类型的值，标识能够将一个对象区别于另一个对象（对象的标识就是它在内存中的位置），行为就是数据类型的操作。<br>

原始数据类型和引用类型<br>
byte、int、double、char、float等就是原始数据类型，原始数据类型更接近计算机硬件所支持的数据类型。原始数据类型保存在栈中，保存的是实际值。<br>
Byte、Integer、Double、Character等就是引用类型。引用类型也保存在栈中，保存的是一个实际对象的地址。<br>
使用原始数据类型比使用引用类型运行得快。<br>

问：对于java，创建对象时忘记使用new会怎么样?<br>
答：Counter c = Counter("test"); 这种代码看起来像是希望调用一个静态方法，其返回值是一个对象类型。因为你没有定义这样一个方法，所以会得到以下的错误信息：<br>
cannot find symbol<br>
symbol  :method Counter(String)<br>

## 1.3 Bag Queue and Stack
背包：一种不支持从中删除元素的集合数据类型，使用bag就说明元素的处理顺序不重要。

Dijkstra双栈算术表达式求值算法：

```
public class Evalute{
    /*
     * 假设没有省略任何一个括号，并且数字和字符之间均以空格隔开。
     */
    public static void main(String[] args){
        Stack<String> ops = new Stack<String>();
        Stack<Double> vals = new Stack<Double>();
        Scanner scan = new Scanner(System.in);
        String[] str = scan.nextLine().split(" ");
        int i = 0;
        while(i < str.length){
            String s = str[i];
            if(s.equals("("));
            else if(s.equals("+"))  ops.push(s);
            else if(s.equals("-"))  ops.push(s);
            else if(s.equals("*"))  ops.push(s);
            else if(s.equals("/"))  ops.push(s);
            else if(s.equals(")")){
                String op = ops.pop();
                double v = vals.pop();
                if(op.equals("+"))  v = vals.pop() + v;
                if(op.equals("-"))  v = vals.pop() - v;
                if(op.equals("*"))  v = vals.pop() * v;
                if(op.equals("/"))  v = vals.pop() / v;
                vals.push(v);
            }
            else vals.push(Double.parseDouble(s));
            i++;
        }
        System.out.println(vals.pop());
    }
}
```

定容栈的实现（泛型）：

```
public class FixedCapacityStack{
    private Item[] a;
    private int N;
    public FixedCapacityStack(int cap){
        a = new Item[cap];
    }
    public boolean isEmpty(){
        return N == 0;
    }
    public int size(){
        return N;
    }
    public void push(Item item){
        a[N++] = item;
    }
    public Item pop(){
        return a[--N];
    }
}
```
对象游离：在上述代码中，被弹出的对象在数组中依旧是存在的，但是它们已经不会再被调用到了。但是垃圾回收器却不能回收这些内存。<br>
解决方法：把不用的对象设为null

可调整大小的下压栈的实现；略
<br>

链表栈的实现：

```
public class Stack<Item> implements Iterable<Item> {
    private Node first;
    private int N;
    private class Node {
        Item item;
        Node next;
    }
    public boolean isEmpty() {
        return first == null;
    }
    public int size() {
        return N;
    }
    public void push(Item item) {
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        N++;
    }
    public Item pop(){
        int item = first.item;
        first = first.next;
        N--;
        return item;
    }
    @Override
    public Iterator<Item> iterator() {
        return null;
    }
}
```
***链表队列的实现***

```
public class Queue<Item> implements Iterable<Item> {
    private Node first; //最早添加的结点
    private Node last; //最近添加的结点
    private int N;
    private class Node {
        Item item;
        Node next;
    }
    public boolean isEmpty() {
        return first == null;
    }
    public int size(){
        return N;
    }
    public void enqueue(Item item){
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if(isEmpty())   first = last;
        else    oldLast.next = last;
        N++;
    }
    public Item dequeue(){
        Item item = first.item;
        first = first.next;
        if(isEmpty())   last = null;
        N--;
        return item;
    }
    @Override
    public Iterator<Item> iterator() {
        return null;
    }
}
```
如果想使用迭代（foreach）功能，就必须:
- 实现iterator方法，并返回一个Iterator对象<br>
- Iterator类必须包含两个方法：hasNext() 和 next()


**实现iterator()**<br>

```
public Iterator<Item> iterator() {
    return new ListIterator();
}
private class ListIterator implements Iterator<Item> {
    private Node current = first;
    public boolean hasNext() {
        return current != null;
    }
    public Item next() {
        Item item = current.item;
        current = current.next;
        return item;
    }
}
```

## 1.4

一个程序运行的总时间主要和两点有关：<br>
执行每条语句的耗时<br>执行每条语句的频率<br>
前者取决于计算机、编译器和操作系统，后者取决于程序本身和输入。
<br>

## 1.5
动态连通性问题<br>
算法:<br>
1. quick-find
2. 