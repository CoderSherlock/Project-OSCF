package Reader;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Nodes {
	protected static List<Node> list = new ArrayList<Node>();

	public void add(int id, long osmid, double lon, double lat) {
		list.add(new Node(id, osmid, lon, lat));
	}

	public int osmid2id(long osmid) {
		for (Node item : list) {
			if (item.getOsmid() == osmid) {
				return item.getId();
			}
		}
		return -1;
	}

	public long id2osmid(int id) {
		return list.get(id).getOsmid();
	}

	public Node oidget(long osmid) {
		for (Node item : list) {
			if (item.getOsmid() == osmid) {
				return item;
			}
		}
		return null;
	}

	public Node idget(int id) {
		return list.get(id);
	}

	public static final void BinaryWrite(String name) throws Exception {
		File addr = new File("./datas/" + name);
		if (!addr.exists())
			addr.mkdir();
		DataOutputStream nodestream = new DataOutputStream(
				new FileOutputStream(new File("./datas/" + name + "/node")));
		nodestream.flush();
		for (int i = 0, len = list.size(); i < len; i++) {
			// nodestream.writeInt(list.get(i).id);
			nodestream.writeLong(list.get(i).getOsmid());
			nodestream.writeDouble(list.get(i).getLon());
			nodestream.writeDouble(list.get(i).getLat());
		}
		nodestream.close();
	}

	public static final void BinaryRead(String name) throws Exception {
		DataInputStream nodestream = new DataInputStream(new FileInputStream(
				new File("./datas/" + name + "/node")));
		int id = 0;
		while (nodestream.available() > 0) {
			// int id = nodestream.readInt();
			long osmid = nodestream.readLong();
			double lon = nodestream.readDouble();
			double lat = nodestream.readDouble();
			list.add(new Node(id++, osmid, lon, lat));
		}
		nodestream.close();
	}

	public final static int size() {
		return list.size();
	}
	
	public final static Node get(int index){
		return list.get(index);
	}
}
