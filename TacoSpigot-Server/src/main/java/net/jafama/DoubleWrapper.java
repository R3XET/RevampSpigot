package net.jafama;

public class DoubleWrapper {
    public double value;
    @Override
    public String toString() {
        return Double.toString(this.value);
    }
}