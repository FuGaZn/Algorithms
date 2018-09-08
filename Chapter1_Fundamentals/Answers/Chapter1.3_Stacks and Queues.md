1.3.3<br>
规律：打印出来的数字顺序都是“小中大”或者“大中小”，不会出现“大小中”<br>
bfg序列是不可能产生的

1.3.4<br>
[GitHub ValidParenthess](https://github.com/FuGaZn/LeetCode/blob/master/Algorithms/20_ValidParentheses.java)
> 用一个栈来存储左括号，每次遇到右括号就出栈一个字符，如果不是匹配的左括号，就返回false。
如果最后栈内还有字符，说明存在左括号无匹配，返回false

1.3.9

```
public void printPostfix(String s){
    String[] strs = s.split(" ");
    Stack<String> vals = new Stack();
    Stack<String> ops = new Stack();
    for(int i=0; i<strs.length; i++){
        if(strs[i].equals(")")){
            String tmp = ")";
            tmp = vals.pop()+ " "+ tmp;
            tmp = ops.pop() + " "+ tmp;
            tmp = vals.pop()+ " "+ tmp;
            tmp = "( " + tmp;
            vals.push(tmp);
        } else if(isOps(strs[i])){
            ops.push(strs[i]);
        } else {
            vals.push(strs[i]);
        }
    }
    System.out.println(vals.pop());
}
public boolean isOps(String s){
    //judging string is or not is operators
}
```
1.3.10 InfixToPostfix<br>
①  运算数：直接输出；<br>
②  左括号：压入堆栈；<br>
③  右括号：将栈顶的运算符弹出并输出，直到遇到左括号(出栈，不输出)；<br>
④  运算符：<br>
- 若优先级大于栈顶运算符时，则把它压栈；<br>
- 若优先级小于等于栈顶运算符时，将栈顶运算符弹出并输出；再比
较新的栈顶运算符，直到该运算符大于栈顶运算符优先级为止，然
后将该运算符压栈；

⑤  若各对象处理完毕，则把堆栈中存留的运算符一并输出

1.3.29 **环形链表实现队列**

```
public class CircularLinkedListQueue<Item> {
    private Node last;

    private class Node {
        public Item item;
        public Node next;
    }

    public CircularLinkedListQueue() {
        last = null;
    }

    public boolean isEmpty() {
        return last == null;
    }

    public Item dequeue() {
        if (isEmpty())
            return null;
        Item item = last.next.item;
        if (last.next == last) {
            last = null;
        } else {
            last.next = last.next.next;
        }
        return item;
    }

    public void enqueue(Item item) {
        Node node = new Node();
        node.item = item;
        if (last == null) {
            last = node;
            node.next = node;
        } else {
            node.next = last.next;
            last.next = node;
            last = node;
        }
    }
}
```
1.3.30 翻转链表

```
public ListNode reverseList(ListNode head) {
    ListNode node = head;
    ListNode pre = null;
    while(node!=null){
        ListNode tmp = node.next;
        node.next = pre;
        pre = node;
        node = tmp;
    }
    return pre;
}
```

1.3.34 随机背包<br>
随机迭代的实现思想：使用Fisher-Yates随机置乱算法(高纳得置乱算法)，生成有限集合的一个随机排列。<br>
算法实现：

```
public void fisherYates(int[] nums){
    for(int i=nums.length-1; i>0; i--){
        int rand = (new Random()).nextInt(i+1);
        int tmp = nums[i];
        nums[i] = nums[rand];
        nums[rand] = tmp;
    }
}
```

