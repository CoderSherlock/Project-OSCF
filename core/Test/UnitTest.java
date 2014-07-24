package Test;

import Reader.Graph;
import Utility.Point2D;

public class UnitTest {
	public static void main(String[] args) throws Exception {
		Graph g = new Graph();
		long a = System.currentTimeMillis();// TODO:Record Running Time
		String name = "map1";

		//g.XMLreader(name);
		System.out.println("XML Prasing Finished With time:"
				+ (System.currentTimeMillis() - a) / 1000f);
		a = System.currentTimeMillis();
		//g.nodelist.BinaryWrite(name);
		//g.waylist.BinaryWrite(name);
		System.out.println("Binary Writing Finished With time:"
				+ (System.currentTimeMillis() - a) / 1000f);
		g.nodelist.clear();
		g.waylist.clear();
		
		a = System.currentTimeMillis();
		g.nodelist.BinaryRead(name);
		g.waylist.BinaryRead(name);
		System.out.println("Binary Reading Finished With time:"
				+ (System.currentTimeMillis() - a) / 1000f);
		a = System.currentTimeMillis();
		Point2D p = new Point2D(116.4269096, 39.9367070);
		p.getClosest(g.nodelist, g.waylist);
		System.out.println(p.toString());
		System.out.println("Find Closest Finished With time:"
				+ (System.currentTimeMillis() - a) / 1000f);
		System.out.println("Main test All FINISHED!");
	}
}
