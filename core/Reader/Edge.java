package Reader;

public class Edge {
	protected int id;
	protected int snode;
	protected int enode;
	protected int nxsnode;
	protected int nxenode;
	protected float dist;
	protected long osmid;

	public Edge(int snode, int enode, int id, float dist, long osmid) {
		this.snode = snode;
		this.enode = enode;
		this.id = id;
		this.osmid = osmid;
		this.dist = dist;
	}

	public Edge(int snode, int enode, int nxsnode, int nxenode, int id,
			float dist, long osmid) {
		this.snode = snode;
		this.enode = enode;
		this.nxsnode = nxsnode;
		this.nxenode = nxenode;
		this.id = id;
		this.osmid = osmid;
		this.dist = dist;
	}
}
