package Reader;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Edges {
	protected List<Edge> list = new ArrayList<Edge>();
	protected List<Edgeindex> index = new ArrayList<Edgeindex>();

	public void add(int snode, int enode, int id, float dist, long osmid) {
		list.add(new Edge(snode, enode, id, dist, osmid));
	}

	public void add(int snode, int enode, int nxs, int nxe, int id, float dist,
			long osmid) {
		list.add(new Edge(snode, enode, nxs, nxe, id, dist, osmid));
	}

	public void clearUnjointedNode() {
		for (int i = 0, len = index.size(); i < len; i++) {
			if (index.get(i).count == 0)
				;
		}
	}

	public final void BinaryWrite(String name) throws Exception {
		DataOutputStream indexstream = new DataOutputStream(
				new FileOutputStream(new File("./datas/" + name + "/index")));
		DataOutputStream waystream = new DataOutputStream(new FileOutputStream(
				new File("./datas/" + name + "/way")));
		indexstream.flush();
		for (int i = 0, len = index.size(); i < len; i++) {
			// indexstream.writeInt(index.get(i).node);
			indexstream.writeByte(index.get(i).count);
			indexstream.writeInt(index.get(i).p2Way);
		}
		indexstream.close();
		waystream.flush();
		for (int i = 0, len = list.size(); i < len; i++) {
			// waystream.writeInt(list.get(i).id);
			waystream.writeInt(list.get(i).getSnode());
			waystream.writeInt(list.get(i).getEnode());
			waystream.writeInt(list.get(i).getNxsnode());
			waystream.writeInt(list.get(i).getNxenode());
			waystream.writeFloat(list.get(i).getDist());
			waystream.writeLong(list.get(i).getOsmid());
		}
		waystream.close();
	}

	public final void BinaryRead(String name) throws Exception {
		DataInputStream indexstream = new DataInputStream(new FileInputStream(
				new File("./datas/" + name + "/index")));
		DataInputStream waystream = new DataInputStream(new FileInputStream(
				new File("./datas/" + name + "/way")));
		int node = 0;
		while (indexstream.available() > 0) {
			// int node = indexstream.readInt();
			byte count = indexstream.readByte();
			int p2Way = indexstream.readInt();
			index.add(new Edgeindex(node++, count, p2Way));
		}
		indexstream.close();
		int id = 0;
		while (waystream.available() > 0) {
			// int id = waystream.readInt();
			int snode = waystream.readInt();
			int enode = waystream.readInt();
			int nxsnode = waystream.readInt();
			int nxenode = waystream.readInt();
			Float dist = waystream.readFloat();
			long osmid = waystream.readLong();
			list.add(new Edge(snode, enode, nxsnode, nxenode, id++, dist, osmid));
		}
		waystream.close();
	}

	public int indexsize() {
		return index.size();
	}

	public int size() {
		return list.size();
	}

	public Edge get(int index) {
		return list.get(index);
	}
	
	public void clear(){
		list.clear();
		index.clear();
	}
}