package Reader;

import java.util.ArrayList;
import java.util.List;

public class Nodeindex {
	protected List<NodeindexItem> list = new ArrayList<NodeindexItem>();

	public void add(int id, long osmid, double lon, double lat) {
		list.add(new NodeindexItem(id, osmid, lon, lat));
	}

	public int osmid2id(long osmid) {
		for (NodeindexItem item : list) {
			if (item.osmid == osmid) {
				return item.id;
			}
		}
		return -1;
	}

	public long id2osmid(int id) {
		return list.get(id).osmid;
	}

	public NodeindexItem oidget(long osmid) {
		for (NodeindexItem item : list) {
			if (item.osmid == osmid) {
				return item;
			}
		}
		return null;
	}

	public NodeindexItem idget(int id) {
		return list.get(id);
	}
}
