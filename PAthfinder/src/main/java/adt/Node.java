package adt;


import java.util.Objects;
public class Node {
    private float x,y;
    private int index = 0;

    public Node(float x,float y){
        this.x= convert(x);
        this.y=convert(y);

    }

    public int getPosition() {
        return index;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Node)) return false;
        Node other = (Node) obj;
        return this.x == other.x && this.y == other.y;
    }

    public void setPosition(int index) {
        this.index = index;
    }
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public Float x() {
        return this.x / (float) Math.pow(10, 2);
    }

    public float y() {
        return this.y /(float) Math.pow(10, 2);
    }

    private int convert(float x) {
        return (int) Math.round(x*Math.pow(10, 2));
    }
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }



}
