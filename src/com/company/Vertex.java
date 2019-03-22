package com.company;

public class Vertex implements Comparable<Vertex>{
    private int vertexName;
    private int weight;

    public Vertex(int vertexName, int weight) {
        this.vertexName = vertexName;
        this.weight = weight;
    }

    public int getVertexName() {
        return vertexName;
    }

    public void setVertexName(int vertexName) {
        this.vertexName = vertexName;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "vertexName=" + vertexName +
                ", weight=" + weight +
                '}';
    }

    @Override
    public int compareTo(Vertex o) {
        if(this.getVertexName()>o.getVertexName()){
            return 1;
        }else if(this.getVertexName()<o.getVertexName()){
            return -1;
        }else{
            return 0;
        }
    }
}
