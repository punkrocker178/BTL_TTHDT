package com.company;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Bellman_Ford {
	
	private Integer[][] G;
	private int V;
	private int[] P;
	private int[] D;

	public Bellman_Ford(String file_name) {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(file_name));
			String line = reader.readLine();
			this.V = Integer.parseInt(line);
			this.G = new Integer[this.V][this.V];
			for(int i = 0; i < this.V; i++) {
				line = reader.readLine();
				String[] edges = line.split(" ");
				for (int j = 0; j < edges.length; j++) {
					this.G[i][j] = Integer.parseInt(edges[j]);
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void print() {
		System.out.println(this.V);

		for (int i = 0; i < this.V; i++) {
			for (int j = 0; j < this.V; j++) {
				System.out.print(this.G[i][j] + " ");
			}
			System.out.println();
		}
	}

	public void find_bellmanford(int a) {
		int MAX_VALUE = 100000;

		for (int i = 0; i < V; i++) {
			for (int j = 0; j < V; j++) {
				if (i != j && G[i][j] == 0)
					G[i][j] = MAX_VALUE;
			}
		}

		D = new int[V];
		P = new int[V];
		for (int i = 0; i < V; i++) {
			D[i] = MAX_VALUE;
		}
		D[a] = 0;
		P[a] = a;
		for (int i = 0; i < V; i++) {
			if (G[a][i] < MAX_VALUE) {
				D[i] = G[a][i];
				P[i] = a;
			}
		}
		
		for(int i = 0; i < V-2; i++){
			for(int v = 0; v < V; v++){
				for(int u = 0; u < V; u++){
					if(D[v] > D[u] + G[u][v]){
						D[v] = D[u] + G[u][v];
						P[v] = u;
					}
				}
			}
		}
	}
	
	void print_path(int t){
		if(t == 0){
			System.out.print(t);
			return;
		}
		print_path(P[t]);
		System.out.print("->" + t);
	}

	void print_D(){
		for (int i = 0; i < this.V; i++) 
				System.out.print(this.D[i] + " ");
			System.out.println();
	}
	
	void print_P(){
		for (int i = 0; i < this.V; i++) 
				System.out.print(this.P[i] + " ");
			System.out.println();
	}
	

	public static void main(String[] args) {
		Bellman_Ford graph = new Bellman_Ford("graph.txt");
		graph.find_bellmanford(0);
		//graph.print();
		graph.print_D();
		graph.print_path(3);
	}
}