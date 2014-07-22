package Test;

import Reader.Edges;
import Reader.Nodes;
import Utility.Point2D;

public class UnitTest {
	public static void main(String[] args) throws Exception {
		long a = System.currentTimeMillis();// TODO:Record Running Time
		String name = "map1";
		/*
		 * XMLreader(name); System.out.println("XML Prasing Finished With time:"
		 * + (System.currentTimeMillis() - a) / 1000f); a =
		 * System.currentTimeMillis(); Nodes.BinaryWrite(name);
		 * Edges.BinaryWrite(name);
		 * System.out.println("Binary Writing Finished With time:" +
		 * (System.currentTimeMillis() - a) / 1000f); Nodes.list.clear();
		 * Edges.list.clear(); Edges.index.clear();
		 */
		a = System.currentTimeMillis();
		Nodes.BinaryRead(name);
		Edges.BinaryRead(name);
		System.out.println("Binary Reading Finished With time:"
				+ (System.currentTimeMillis() - a) / 1000f);
		a = System.currentTimeMillis();
		Point2D p = new Point2D(116.4269096, 39.9367071);
		System.out.println(p.getClosest());
		System.out.println("Find Closest Finished With time:"
				+ (System.currentTimeMillis() - a) / 1000f);
		System.out.println("Main test All FINISHED!");
	}
}
