package org.byfc;

public class Counter {
    private int count;

    public Counter() {
        this.count = 0;
    }

    public void increment() {
        count++;
    }

    public void incrementBy(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Increment value cannot be negative");
        }
        count += n;
    }

    public void reset() {
        count = 0;
    }

    public int getCount() {
        return count;
    }
}
