package Reader;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Wayindex {
	protected static List<WayindexItem> list = new ArrayList<WayindexItem>();
	protected static List<Edgeindex> index = new ArrayList<Edgeindex>();

	public void add(int snode, int enode, int id, float dist, long osmid) {
		list.add(new WayindexItem(snode, enode, id, dist, osmid));
	}

	public void add(int snode, int enode, int nxs, int nxe, int id, float dist,
			long osmid) {
		list.add(new WayindexItem(snode, enode, nxs, nxe, id, dist, osmid));
	}

	public void clearUnjointedNode() {
		for (int i = 0, len = index.size(); i < len; i++) {
			if (index.get(i).count == 0)
				;
		}
	}

	public static void BinaryWrite() throws Exception {
		DataOutputStream indexstream = new DataOutputStream(
				new FileOutputStream(new File("./datas/save/index")));
		DataOutputStream waystream = new DataOutputStream(new FileOutputStream(
				new File("./datas/save/way")));
		indexstream.flush();
		for (int i = 0, len = index.size(); i < len; i++) {
			indexstream.writeInt(index.get(i).node);
			indexstream.writeShort(index.get(i).count);
			indexstream.writeInt(index.get(i).p2Way);
		}
		indexstream.close();
		waystream.flush();
		for (int i = 0, len = list.size(); i < len; i++) {
			waystream.writeInt(list.get(i).id);
			waystream.writeInt(list.get(i).snode);
			waystream.writeInt(list.get(i).enode);
			waystream.writeInt(list.get(i).nxsnode);
			waystream.writeInt(list.get(i).nxenode);
			waystream.writeFloat(list.get(i).dist);
			waystream.writeLong(list.get(i).osmid);
		}
		waystream.close();
	}

	public static void BinaryRead() throws Exception {
		DataInputStream indexstream = new DataInputStream(new FileInputStream(
				new File("./datas/save/index")));
		DataInputStream waystream = new DataInputStream(new FileInputStream(
				new File("./datas/save/way")));
		while (indexstream.available() > 0) {
			int node = indexstream.readInt();
			short count = indexstream.readShort();
			int p2Way = indexstream.readInt();
			index.add(new Edgeindex(node, count, p2Way));
		}
		indexstream.close();
		while (waystream.available() > 0) {
			int id = waystream.readInt();
			int snode = waystream.readInt();
			int enode = waystream.readInt();
			int nxsnode = waystream.readInt();
			int nxenode = waystream.readInt();
			Float dist = waystream.readFloat();
			long osmid = waystream.readLong();
			list.add(new WayindexItem(id, snode, enode, nxsnode, nxenode, dist,
					osmid));
		}
		waystream.close();
	}
}
