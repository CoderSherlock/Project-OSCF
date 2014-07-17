package Reader;

import java.util.ArrayList;
import java.util.List;

public class Wayindex {
	protected List<WayindexItem> list = new ArrayList<WayindexItem>();
	protected List<Edgeindex> index = new ArrayList<Edgeindex>();

	public void add(int snode, int enode, int id, float dist, long osmid) {
		list.add(new WayindexItem(snode, enode, id, dist, osmid));
	}

	public void add(int snode, int enode, int nxs, int nxe, int id, float dist,
			long osmid) {
		list.add(new WayindexItem(snode, enode, nxs, nxe, id, dist, osmid));
	}

	public void linked() {

	}
}
