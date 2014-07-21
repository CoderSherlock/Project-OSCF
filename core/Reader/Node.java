package Reader;

public class Node {
	protected int id;
	protected long osmid;
	protected double lon;
	protected double lat;
	
	public Node(int id, long osmid, double lon, double lat){
		this.id = id;
		this.osmid = osmid;
		this.lon = lon;
		this.lat = lat;
	}
	
}
