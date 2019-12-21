package dataStructure;

import java.util.Collection;
import java.util.HashMap;
import utils.Point3D;

public class DGraph implements graph {
	public HashMap<Integer, node_data> ver = new HashMap<Integer, node_data>();
	public HashMap<Integer, edge_data> ed = new HashMap<Integer, edge_data>();
	public HashMap<Integer, HashMap<Integer, edge_data>> nodeedges = new HashMap<Integer, HashMap<Integer, edge_data>>();

	public int counter=0;
	public int MC=0;
	public Edge edge[];
	public Node vertex[];

	public DGraph(Node vertex[] , Edge edge[]){
		ed = new HashMap<Integer, edge_data>();
		this.vertex=vertex;
		this.edge=edge;

		for(Node x: vertex) {
			ver.put(x.getKey(), x);
			this.MC++;
		}
		
		for(Edge y: edge) {
			if(nodeedges.containsKey(y.getSrc()) ) {
				ed = (HashMap<Integer, edge_data>) this.nodeedges.get(y.getSrc());
				ed.put(y.getDest(), y);	
				ed = new HashMap<Integer, edge_data>();
			}
			else {
				ed.put(y.getDest(), y);
				this.nodeedges.put(y.getSrc(), ed);
				ed = new HashMap<Integer, edge_data>();
			}	
			this.MC++;
			this.counter++;
		}

	}	

	public static void main(String[] argas) {
		Point3D p0 = new Point3D(-0.5,0.5,0);
		Point3D p1 = new Point3D(-6.5,0.5,0);
		Point3D p2 = new Point3D(-6.5,6.5,0);
		Point3D p3 = new Point3D(-0.5,6.5,0);
		Point3D p4 = new Point3D(-3.5,12.5,0);
		Point3D p5 = new Point3D(0.5,6.5,0);
		Point3D p6 = new Point3D(3.5,12.5,0);

		Node n0 = new Node(0,0,p0);
		Node n1 = new Node(1,0,p1);
		Node n2 = new Node(2,0,p2);
		Node n3 = new Node(3,0,p3);
		Node n4 = new Node(4,0,p4);
		Node n5 = new Node(5,0,p5);
		Node n6 = new Node(6,0,p6);

		Edge e1 = new Edge(n0, n1, 1);
		Edge e2 = new Edge(n1, n2, 3);
		Edge e3 = new Edge(n2, n3, 40);
		Edge e4 = new Edge(n3, n0, 2);
		Edge e5 = new Edge(n3, n4, 6);
		Edge e6 = new Edge(n4, n2, 1);
		Edge e7 = new Edge(n0, n5, 2);
		Edge e8 = new Edge(n5, n6, 6);
		Edge e9 = new Edge(n6, n3, 1);

		Edge edge[]= {e1,e2,e3,e4,e5,e6,e7,e8,e9};
		Node node[] = {n0,n1,n2,n3,n4,n5,n6};

		DGraph g = new DGraph(node , edge);
		System.out.println(g.nodeedges);



		System.out.println(g.getE(n4.getKey()));
		
	}

	@Override
	public node_data getNode(int key) {

		return  this.ver.get(key);
	}

	@Override
	public edge_data getEdge(int src, int dest) {
		counter++;
		return this.nodeedges.get(src).get(dest);
	}

	@Override
	public void addNode(node_data n) {
		this.ver.put(n.getKey(), (Node) n);
		this.MC++;
	}

	@Override
	public void connect(int src, int dest, double w) {
		ed = new HashMap<Integer, edge_data>();
		Node n = (Node)this.ver.get(src);
		Node n1 = (Node)this.ver.get(dest);
		Edge e = new Edge(n , n1 , w);

		if(this.nodeedges.get(e.getSrc()) != null) {ed=this.nodeedges.get(e.getSrc());}
		ed.put(n1.getKey(), e);
		this.nodeedges.put(e.getSrc(), ed);
		this.counter++;
		this.MC++;
	}

	@Override
	public Collection<node_data> getV() {	
		Collection<node_data> v = this.ver.values(); 

		return v;
	}

	@Override
	public Collection<edge_data> getE(int node_id){
		 if(this.nodeedges.get(node_id) == null) {
			 Collection<edge_data> f =null;
			 return f;
		 }
		return this.nodeedges.get(node_id).values();
	}

	@Override
	public node_data removeNode(int key) {
		Node n = (Node) this.ver.get(key);
		for(Edge x :this.edge) {
			if(x.getDest() == key) { 
				this.nodeedges.get(x.getSrc()).remove(x.getDest()); 
				this.counter--;
				this.MC++;
			}
			if(x.getSrc() == key) { 
				this.nodeedges.get(x.getSrc()).remove(x.getDest());
				this.counter--;
				this.MC++;
			}
		}
		this.ver.remove(key);
		this.MC++;
		this.nodeedges.remove(key);
		return n;
	}

	@Override
	public edge_data removeEdge(int src, int dest) {
		Edge e = (Edge) this.nodeedges.get(src).get(dest);
		this.nodeedges.get(src).remove(dest);
		if(this.nodeedges.get(src).isEmpty()) {this.nodeedges.remove(src);}
		this.counter--;
		this.MC++;
		return  e;
	}

	@Override
	public int nodeSize() {
		return this.ver.size();
	}

	@Override
	public int edgeSize() {
		return this.counter;
	}

	@Override
	public int getMC() {
		return this.MC;
	}

}
