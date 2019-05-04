package com.company;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Graph_MST {
	public class Edge implements Comparable<Edge>{
		int src, dest, weight;
		public int compareTo(Edge e){
			return this.weight - e.weight;
		}
		
		public Edge(){
			
		}
		
		public Edge(int src, int dest, int weight){
			this.src = src;
			this.dest = dest;
			this.weight = weight;
		}
	}
	
	class Compare
    {
        int root, rank;
    }
	
	int V, E;
	Edge[] edges;
	
	public Graph_MST(String file){
		BufferedReader reader;
		try{
			reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			String[] str = line.split(" ");
			this.V = Integer.parseInt(str[0]);
			this.E = Integer.parseInt(str[1]);
			edges = new Edge[this.E];
			for(int i=0; i<this.E; i++){
				line = reader.readLine();
				String[] str1 = line.split(" ");
				edges[i] = new Edge(Integer.parseInt(str1[0]), Integer.parseInt(str1[1]), Integer.parseInt(str1[2]));
			}
			reader.close();
		} catch (IOException e){	
		}
	}
	
	int find(Compare compares[], int i)
    {
        if (compares[i].root != i)
        	compares[i].root = find(compares, compares[i].root);
 
        return compares[i].root;
    }
 
    
    void update(Compare compares[], int x, int y)
    {
        int xroot = find(compares, x);
        int yroot = find(compares, y);
 
        // Attach smaller rank tree under root of high rank tree
        // (Union by Rank)
        if (compares[xroot].rank < compares[yroot].rank)
        	compares[xroot].root = yroot;
        else if (compares[xroot].rank > compares[yroot].rank)
        	compares[yroot].root = xroot;
 
        // If ranks are same, then make one as root and increment
        // its rank by one
        else
        {
        	compares[yroot].root = xroot;
        	compares[xroot].rank++;
        }
    }
 
    // The main function to construct MST using Kruskal's algorithm
    void KruskalMST()
    {
        Edge result[] = new Edge[V];
        int e = 0;  // An index variable, used for result[]
        int i = 0;  // An index variable, used for sorted edges
        for (i=0; i<V; ++i)
            result[i] = new Edge();
 
      
        Arrays.sort(edges);
 
        Compare compares[] = new Compare[V];
        for(i=0; i<V; ++i)
        	compares[i]=new Compare();
 
        // Create V subsets with single elements
        for (int v = 0; v < V; ++v)
        {
        	compares[v].root = v;
        	compares[v].rank = 0;
        }
 
        i = 0;  // Index used to pick next edge
 
        // Number of edges to be taken is equal to V-1
        while (e < V - 1)
        {
            Edge next_edge = new Edge();
            next_edge = edges[i++];
 
            int x = find(compares, next_edge.src);
            int y = find(compares, next_edge.dest);
 
            if (x != y)
            {
                result[e++] = next_edge;
                update(compares, x, y);
            }
            
        }
 
        // print the contents of result[] to display
        // the built MST
        System.out.println("Kruskal's MST: ");
        for (i = 0; i < e; ++i)
            System.out.println(result[i].src+" -- " + 
                   result[i].dest+" == " + result[i].weight);
    }
	
	public void print(){
		System.out.println("Vertices: "+V + " Edges: " + E);
		for(int i=0; i<E; i++)
			System.out.println(this.edges[i].src + " " + this.edges[i].dest + " " + this.edges[i].weight);
	}
	
	public static void main(String[] args){
		Graph_MST graph = new Graph_MST("resources/graph.txt");
		graph.print();
		graph.KruskalMST();

	}
}	