package Reader;

public class Edge {
	private int id;
	private int snode;
	private int enode;
	private int nxsnode;
	private int nxenode;
	private float dist;
	private long osmid;

	public Edge(int snode, int enode, int id, float dist, long osmid) {
		this.setSnode(snode);
		this.setEnode(enode);
		this.setId(id);
		this.setOsmid(osmid);
		this.setDist(dist);
	}

	public Edge(int snode, int enode, int nxsnode, int nxenode, int id,
			float dist, long osmid) {
		this.setSnode(snode);
		this.setEnode(enode);
		this.setNxsnode(nxsnode);
		this.setNxenode(nxenode);
		this.setId(id);
		this.setOsmid(osmid);
		this.setDist(dist);
	}

	public int getSnode() {
		return snode;
	}

	public void setSnode(int snode) {
		this.snode = snode;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEnode() {
		return enode;
	}

	public void setEnode(int enode) {
		this.enode = enode;
	}

	public int getNxsnode() {
		return nxsnode;
	}

	public void setNxsnode(int nxsnode) {
		this.nxsnode = nxsnode;
	}

	public int getNxenode() {
		return nxenode;
	}

	public void setNxenode(int nxenode) {
		this.nxenode = nxenode;
	}

	public float getDist() {
		return dist;
	}

	public void setDist(float dist) {
		this.dist = dist;
	}

	public long getOsmid() {
		return osmid;
	}

	public void setOsmid(long osmid) {
		this.osmid = osmid;
	}

}
