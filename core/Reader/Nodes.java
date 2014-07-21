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
			if (item.osmid == osmid) {
				return item.id;
			}
		}
		return -1;
	}

	public long id2osmid(int id) {
		return list.get(id).osmid;
	}

	public Node oidget(long osmid) {
		for (Node item : list) {
			if (item.osmid == osmid) {
				return item;
			}
		}
		return null;
	}

	public Node idget(int id) {
		return list.get(id);
	}
	
	public static final void BinaryWrite() throws Exception {
		DataOutputStream nodestream = new DataOutputStream(
				new FileOutputStream(new File("./datas/save/node")));
		nodestream.flush();
		for (int i = 0, len = list.size(); i < len; i++) {
			nodestream.writeInt(list.get(i).id);
			nodestream.writeLong(list.get(i).osmid);
			nodestream.writeDouble(list.get(i).lon);
			nodestream.writeDouble(list.get(i).lat);
		}
		nodestream.close();
	}
	
	public static final void BinaryRead() throws Exception{
		DataInputStream nodestream = new DataInputStream(
				new FileInputStream(new File("./datas/save/node")));
		while(nodestream.available()>0){
			int id = nodestream.readInt();
			long osmid = nodestream.readLong();
			double lon = nodestream.readDouble();
			double lat = nodestream.readDouble();
			list.add(new Node(id, osmid, lon, lat));
		}
		nodestream.close();
	}
}
