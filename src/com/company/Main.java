package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    private static LinkedList<Vertex>[] vertexLinkedList;
    private static boolean[] visited;
    private static int[] parent;

    public static void main(String[] args) {
	// write your code here
        readGraph();
        System.out.println("The max flow is: "+maxFlow(0,5));

    }

    //Read graph from Graph.txt
    //First line is the number of vertices in graph
    //Each next line represent a vertex object.The vertex object contains vertextName and vertextWeight
    //vertextName is seperated by a " " character, vertexWeight is seperated by a ":" character

    public static void readGraph(){
        Scanner sc;
        String line = "";
        int index=0,vertexName=0,vertexWeight=0;
        String[] vertices,weight;

        try {
            sc = new Scanner(new File("resources/Graph.txt"));
            int numVertex = Integer.parseInt(sc.nextLine());
            initArrayLinkedList(numVertex);
            while (sc.hasNextLine()){
                line = sc.nextLine();
                vertices = line.split("[ :]");
                for(int v=0;v<vertices.length;v=v+2){
                    vertexName = Integer.parseInt(vertices[v].trim());
                    vertexWeight = Integer.parseInt(vertices[v+1].trim());
                    vertexLinkedList[index].add(new Vertex(vertexName,vertexWeight));
                }
                index++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static boolean BFS(LinkedList<Vertex>[] residualGraph,int source,int target,int[] parent){

        visited = new boolean[residualGraph.length];

        Queue queue = new PriorityQueue<Vertex>();
        queue.add(new Vertex(source,0));
        visited[source] = true;
        parent[source] = -1;
        while(!queue.isEmpty()){
            //System.out.println(Arrays.toString(visited));
            Vertex u = (Vertex)queue.poll();
            //System.out.print(u.getVertexName()+" ");
            for(Vertex v : residualGraph[u.getVertexName()]){
                if(visited[v.getVertexName()]==false&& v.getWeight()>0){
                    queue.add(v);
                    visited[v.getVertexName()] = true;
                    parent[v.getVertexName()] = u.getVertexName();

                }
            }
        }
        if(visited[target] == false){
            return false;
        }
        return true;
        //Trace path
//        System.out.println(Arrays.toString(parent));
//        String path = ""+(parent.length-1);
//        int value=0,index = parent[parent.length-1] ;
//            while(true){
//                value = index;
//                index = parent[value];
//                path = path+" "+value;
//
//                if(value==parent[0]){
//                    break;
//                }
//            }
//        System.out.print(path+" ");

    }

    //return the weight of vertex1 -> vertex2
    private static int getFlow(LinkedList<Vertex>[] residualGraph,int vertex1,int vertex2){
        int weight = 0;
        for(Vertex vertex : residualGraph[vertex1]){
            if(vertex.getVertexName() == vertex2){
                weight = vertex.getWeight();
            }
        }
        return weight;
    }

    private static int maxFlow(int source,int target){
        int u,v;
        //Initialize parent & residualGraph
        int[] parent = new int[vertexLinkedList.length];
        LinkedList<Vertex>[] residualGraph = new LinkedList[vertexLinkedList.length];
        for (int i = 0;i<vertexLinkedList.length;i++){
            residualGraph[i] = new LinkedList<Vertex>();
            residualGraph[i] = (LinkedList<Vertex>)vertexLinkedList[i].clone();
        }

        int max_flow = 0;
        //Run BFS to find all path from source to target
        while(BFS(residualGraph,source,target,parent)){
            int path_flow = Integer.MAX_VALUE;
            int weight =0;
            for(v = target;v!= source;v = parent[v]){
                u = parent[v];
                weight = getFlow(residualGraph,u,v);
                path_flow = Math.min(path_flow,weight);
            }

            //residualCap : Flow from u -> v
            //reverseCap : Flow backwards
            int residualCap = 0;
            int reverseCap = 0;
            for(v = target; v!= source; v = parent[v]){
                u = parent[v];

                residualCap = getFlow(residualGraph,u,v);
                reverseCap = getFlow(residualGraph,v,u);

                residualCap-= path_flow;

                //Update weight to residualGraph
                for(Vertex vertex : residualGraph[u]){
                    if(vertex.getVertexName() == v){
                        vertex.setWeight(residualCap);
                    }
                }

                reverseCap += path_flow;

                //Update reverse weight to residualGraph
                for(Vertex vertex: residualGraph[v]){
                    if(vertex.getVertexName() == u){
                        vertex.setWeight(reverseCap);
                    }
                }
            }
            max_flow += path_flow;
        }
        return max_flow;
    }

    private static void printGraph(LinkedList<Vertex>[] vertexLinkedList){
        for (int i=0;i<vertexLinkedList.length;i++){
            System.out.print("Vertex: "+i);
            for (Vertex v : vertexLinkedList[i]){
                System.out.print("->"+v.getVertexName());
            }
            System.out.println();
        }
    }


    private static void initArrayLinkedList(int numVertex) {
        vertexLinkedList = new LinkedList[numVertex];
        for(int i=0;i<vertexLinkedList.length;i++){
            vertexLinkedList[i] = new LinkedList<Vertex>();
        }
    }
}
