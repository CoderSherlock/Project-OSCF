package Reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class FileReader {
	static long a = System.currentTimeMillis();// TODO:Record Running Time
	public static Nodes nodelist = new Nodes();
	public static Edges waylist = new Edges();
	static Map<Integer, Integer> lastMap = new HashMap<Integer, Integer>();

	public static void XMLreader(InputStream input) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(input);
		Element element = document.getDocumentElement();// Stream Definition

		NodeList nodes = element.getElementsByTagName("node");
		for (int i = 0, len = nodes.getLength(); i < len; i++) {
			Element node = (Element) nodes.item(i);
			long osmid = Long.valueOf(node.getAttribute("id"));
			double lon = Double.valueOf(node.getAttribute("lon"));
			double lat = Double.valueOf(node.getAttribute("lat"));
			nodelist.add(i, osmid, lon, lat);
		}
		System.out.println("Node:" + nodes.getLength());// Nodes Finished:TODO

		for (int i = 0, len = Nodes.list.size(); i < len; i++) {
			Edges.index.add(new Edgeindex(i, (byte) 0, -1));
		}
		System.out.println("Index:" + Edges.index.size());// Index Finished:TODO

		NodeList ways = element.getElementsByTagName("way");

		int waylistCount = -1;// Way Id Counter:Provide a
		for (int i = 0, len = ways.getLength(); i < len; i++) {
			Element way = (Element) ways.item(i);
			Long osmid = Long.valueOf(way.getAttribute("id"));
			NodeList refs = way.getElementsByTagName("nd");
			NodeList tags = way.getElementsByTagName("tag");
			boolean oneway = false;// Flag of One way
			for (int j = 0, size = tags.getLength(); j < size; j++) {
				Element tag = (Element) tags.item(j);

				if (tag.getAttribute("k").equals("oneway")
						&& tag.getAttribute("v").equals("yes")) {
					oneway = true;
				}
			}
			for (int j = 0, size = refs.getLength() - 1; j < size; j++) {
				Element sref = (Element) refs.item(j);
				Element eref = (Element) refs.item(j + 1);
				int sosmid = nodelist.osmid2id(Long.valueOf(sref
						.getAttribute("ref")));
				int eosmid = nodelist.osmid2id(Long.valueOf(eref
						.getAttribute("ref")));
				Node s = nodelist.idget(sosmid);
				Node e = nodelist.idget(eosmid);
				// System.out.println(GetDistance(s.lat, s.lon, e.lat, e.lon));
				float dist = (float) GetDistance(s.lat, s.lon, e.lat, e.lon);

				if (oneway == false) {
					waylist.add(s.id, e.id, -1, -1, ++waylistCount, dist, osmid);
					linked(waylistCount, s, e);
					waylist.add(e.id, s.id, -1, -1, ++waylistCount, dist, osmid);
					linked(waylistCount, e, s);

				} else {// One way Start
					waylist.add(s.id, e.id, -1, -1, ++waylistCount, dist, osmid);
					linked(waylistCount, s, e);
				}
			}
		}
		System.out.println("Ways:" + Edges.list.size());// Edges Finished:TODO
	}

	public static void linked(int waylistCount, Node s, Node e) {
		boolean hass = false;
		boolean hase = false;

		if (lastMap.containsKey(s.id)) {
			int lastvalue = lastMap.get(s.id);
			if (Edges.list.get(lastvalue).snode == s.id) {
				Edges.list.get(lastvalue).nxsnode = waylistCount;
				lastMap.remove(s.id);
				lastMap.put(s.id, waylistCount);
				Edges.index.get(s.id).count++;
			} else if (Edges.list.get(lastvalue).enode == s.id) {
				Edges.list.get(lastvalue).nxenode = waylistCount;
				lastMap.remove(s.id);
				lastMap.put(s.id, waylistCount);
				Edges.index.get(s.id).count++;
			}
			hass = true;
		}
		if (lastMap.containsKey(e.id)) {
			int lastvalue = lastMap.get(e.id);
			if (Edges.list.get(lastvalue).snode == e.id) {
				Edges.list.get(lastvalue).nxsnode = waylistCount;
				lastMap.remove(e.id);
				lastMap.put(e.id, waylistCount);
				Edges.index.get(e.id).count++;
			} else if (Edges.list.get(lastvalue).enode == e.id) {
				Edges.list.get(lastvalue).nxenode = waylistCount;
				lastMap.remove(e.id);
				lastMap.put(e.id, waylistCount);
				Edges.index.get(e.id).count++;
			}
			hase = true;
		}
		if (hass == false) {
			lastMap.put(s.id, waylistCount);
			Edges.index.get(s.id).count++;
			Edges.index.get(s.id).p2Way = waylistCount;
		}
		if (hase == false) {
			lastMap.put(e.id, waylistCount);
			Edges.index.get(e.id).count++;
			Edges.index.get(e.id).p2Way = waylistCount;
		}
	}

	private static double EARTH_RADIUS = 6378137.0;// Radius of Earth

	private static final double GetDistance(double lat_a, double lng_a,
			double lat_b, double lng_b) {
		double radLat1 = (lat_a * Math.PI / 180.0);
		double radLat2 = (lat_b * Math.PI / 180.0);
		double a = radLat1 - radLat2;
		double b = (lng_a - lng_b) * Math.PI / 180.0;
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s / 1000;
	}

	public static void main(String[] args) throws Exception {
		File f = new File("./datas/map1.osm");
		FileInputStream input = new FileInputStream(f);
		XMLreader(input);
		input.close();
		System.out.println("Test Main Finished With time:"
				+ (System.currentTimeMillis() - a) / 1000f);
		a = System.currentTimeMillis();
		Nodes.BinaryWrite();
		Edges.BinaryWrite();
		System.out.println("Test Main Finished With time:"
				+ (System.currentTimeMillis() - a) / 1000f);
		a = System.currentTimeMillis();
		Nodes.BinaryRead();
		Edges.BinaryRead();
		System.out.println("Test Main Finished With time:"
				+ (System.currentTimeMillis() - a) / 1000f);
	}
}
