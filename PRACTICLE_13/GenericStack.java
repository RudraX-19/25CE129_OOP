// ============================================================
//  PRACTICLE 13 - Part A - Task 3: Generic Stack (Advanced)
//  A small generic stack backed by an array; reused for two
//  different types; empty case handled sensibly
//  File: PRACTICLE_13/GenericStack.java
// ============================================================

import java.util.EmptyStackException;

class GenericStack<T> {
    private Object[] data;
    private int      top;
    private int      capacity;

    public GenericStack(int capacity) {
        this.capacity = capacity;
        this.data     = new Object[capacity];
        this.top      = -1;
    }

    public void push(T item) {
        if (top == capacity - 1)
            throw new RuntimeException("Stack overflow (capacity=" + capacity + ")");
        data[++top] = item;
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        if (isEmpty()) throw new EmptyStackException();
        T item = (T) data[top];
        data[top--] = null;
        return item;
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) throw new EmptyStackException();
        return (T) data[top];
    }

    public boolean isEmpty() { return top == -1; }
    public int     size()    { return top + 1;   }

    @Override
    public String toString() {
        if (isEmpty()) return "GenericStack[]";
        StringBuilder sb = new StringBuilder("GenericStack[");
        for (int i = 0; i <= top; i++) {
            sb.append(data[i]);
            if (i < top) sb.append(", ");
        }
        return sb.append("] (top→left)").toString();
    }
}

public class GenericStackTest {
    public static void main(String[] args) {
        System.out.println("=== Generic Stack Demo ===\n");

        // --- Integer stack ---
        System.out.println("--- Integer Stack ---");
        GenericStack<Integer> intStack = new GenericStack<>(5);

        intStack.push(10);
        intStack.push(20);
        intStack.push(30);
        System.out.println("After 3 pushes : " + intStack);
        System.out.println("Peek           : " + intStack.peek());
        System.out.println("Pop            : " + intStack.pop());
        System.out.println("Pop            : " + intStack.pop());
        System.out.println("After 2 pops   : " + intStack);
        System.out.println("Size           : " + intStack.size());

        // --- String stack ---
        System.out.println("\n--- String Stack ---");
        GenericStack<String> strStack = new GenericStack<>(4);

        strStack.push("Java");
        strStack.push("Python");
        strStack.push("C++");
        System.out.println("After 3 pushes : " + strStack);
        System.out.println("Pop            : " + strStack.pop());
        System.out.println("After 1 pop    : " + strStack);

        // --- Empty stack handling ---
        System.out.println("\n--- Empty Stack Handling ---");
        GenericStack<Double> emptyStack = new GenericStack<>(3);
        System.out.println("Is empty: " + emptyStack.isEmpty());

        try {
            emptyStack.pop();
        } catch (EmptyStackException e) {
            System.out.println("Caught EmptyStackException on pop() of empty stack — handled!");
        }

        try {
            emptyStack.peek();
        } catch (EmptyStackException e) {
            System.out.println("Caught EmptyStackException on peek() of empty stack — handled!");
        }

        // --- Overflow handling ---
        System.out.println("\n--- Overflow Handling ---");
        GenericStack<Integer> smallStack = new GenericStack<>(2);
        smallStack.push(1);
        smallStack.push(2);
        try {
            smallStack.push(3);
        } catch (RuntimeException e) {
            System.out.println("Caught: " + e.getMessage());
        }
    }
}
