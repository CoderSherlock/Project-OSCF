package Reader;

public class Node {
	private int id;
	private long osmid;
	private double lon;
	private double lat;

	public Node(int id, long osmid, double lon, double lat) {
		this.setId(id);
		this.setOsmid(osmid);
		this.setLon(lon);
		this.setLat(lat);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getOsmid() {
		return osmid;
	}

	public void setOsmid(long osmid) {
		this.osmid = osmid;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

}
