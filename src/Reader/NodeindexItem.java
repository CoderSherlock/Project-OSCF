package Reader;

public class NodeindexItem {
	protected int id;
	protected long osmid;
	protected double lon;
	protected double lat;
	
	public NodeindexItem(int id, long osmid, double lon, double lat){
		this.id = id;
		this.osmid = osmid;
		this.lon = lon;
		this.lat = lat;
	}
	
}
