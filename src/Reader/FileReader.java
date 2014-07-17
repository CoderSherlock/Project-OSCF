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
	public static Nodeindex nodelist = new Nodeindex();
	public static Wayindex waylist = new Wayindex();
	static Map<Integer, Integer> lastMap = new HashMap<Integer, Integer>();

	public static void XMLreader(InputStream input) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(input);
		Element element = document.getDocumentElement();// Stream Definition

		NodeList nodes = element.getElementsByTagName("node");
		for (int i = 0; i < nodes.getLength(); i++) {
			Element node = (Element) nodes.item(i);
			long osmid = Long.valueOf(node.getAttribute("id"));
			double lon = Double.valueOf(node.getAttribute("lon"));
			double lat = Double.valueOf(node.getAttribute("lat"));
			nodelist.add(i, osmid, lon, lat);
		}
		System.out.println("Node:" + nodes.getLength());// Nodes Finished

		for (int i = 0; i < nodelist.list.size(); i++) {
			waylist.index.add(new Edgeindex(i, (short) 0, -1));
		}
		System.out.println("Index:" + waylist.index.size());// Index Finished

		NodeList ways = element.getElementsByTagName("way");

		int waylistCount = -1;// Way Id Counter:Provide a
		for (int i = 0; i < ways.getLength(); i++) {
			Element way = (Element) ways.item(i);
			Long osmid = Long.valueOf(way.getAttribute("id"));
			NodeList refs = way.getElementsByTagName("nd");
			NodeList tags = way.getElementsByTagName("tag");
			boolean oneway = false;// Flag of One way
			for (int j = 0; j < tags.getLength(); j++) {
				Element tag = (Element) tags.item(j);

				if (tag.getAttribute("k").equals("oneway")
						&& tag.getAttribute("v").equals("yes")) {
					oneway = true;
				}
			}
			for (int j = 0; j < refs.getLength() - 1; j++) {
				Element sref = (Element) refs.item(j);
				Element eref = (Element) refs.item(j + 1);
				int sosmid = nodelist.osmid2id(Long.valueOf(sref
						.getAttribute("ref")));
				int eosmid = nodelist.osmid2id(Long.valueOf(eref
						.getAttribute("ref")));
				NodeindexItem s = nodelist.idget(sosmid);
				NodeindexItem e = nodelist.idget(eosmid);
				float dist = (float) GetDistance(s.lat, s.lon, e.lat, e.lon);

				if (oneway == false) {
					waylist.add(s.id, e.id, ++waylistCount, dist, osmid);
					linked(waylistCount, s, e);
					waylist.add(e.id, s.id, ++waylistCount, dist, osmid);
					linked(waylistCount, e, s);

				} else {// One way Start
					waylist.add(s.id, e.id, ++waylistCount, dist, osmid);
					linked(waylistCount, s, e);
				}
			}
		}
		System.out.println("Ways:" + waylist.list.size());// Way Finished
	}

	public static void linked(int waylistCount, NodeindexItem s, NodeindexItem e) {
		boolean hass = false;
		boolean hase = false;

		if (lastMap.containsKey(s.id)) {
			int lastvalue = lastMap.get(s.id);
			if (waylist.list.get(lastvalue).snode == s.id) {
				waylist.list.get(lastvalue).nxsnode = waylistCount;
				lastMap.remove(s.id);
				lastMap.put(s.id, waylistCount);
				waylist.index.get(s.id).count++;
			} else if (waylist.list.get(lastvalue).enode == s.id) {
				waylist.list.get(lastvalue).nxenode = waylistCount;
				lastMap.remove(s.id);
				lastMap.put(s.id, waylistCount);
				waylist.index.get(s.id).count++;
			}
			hass = true;
		}
		if (lastMap.containsKey(e.id)) {
			int lastvalue = lastMap.get(e.id);
			if (waylist.list.get(lastvalue).snode == e.id) {
				waylist.list.get(lastvalue).nxsnode = waylistCount;
				lastMap.remove(e.id);
				lastMap.put(e.id, waylistCount);
				waylist.index.get(e.id).count++;
			} else if (waylist.list.get(lastvalue).enode == e.id) {
				waylist.list.get(lastvalue).nxenode = waylistCount;
				lastMap.remove(e.id);
				lastMap.put(e.id, waylistCount);
				waylist.index.get(e.id).count++;
			}
			hase = true;
		}
		if (hass == false) {
			lastMap.put(s.id, waylistCount);
			waylist.index.get(s.id).count++;
		}
		if (hase == false) {
			lastMap.put(e.id, waylistCount);
			waylist.index.get(e.id).count++;
		}
	}

	private static double EARTH_RADIUS = 6378.137;// Radius of Earth

	public static double GetDistance(double lat1, double lng1, double lat2,
			double lng2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);

		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	public static void main(String args[]) throws Exception {
		File f = new File("./datas/map.osm");
		FileInputStream input = new FileInputStream(f);
		XMLreader(input);
		input.close();
	}
}
