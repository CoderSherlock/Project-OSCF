package Utility;

import Reader.Edges;
import Reader.Nodes;

public class Point2D {
	protected double lon;
	protected double lat;

	public Point2D(double lon, double lat) {
		this.lon = lon;
		this.lat = lat;
	}

	public int getClosest() {
		int id = 0;
		double dist = Double.MAX_VALUE;
		for (int i = 0, len = Edges.size(); i < len; i++) {
			double d = p2lDistance(lon, lat, Nodes.get(Edges.get(i).getSnode())
					.getLon(), Nodes.get(Edges.get(i).getSnode()).getLat(),
					Nodes.get(Edges.get(i).getEnode()).getLon(),
					Nodes.get(Edges.get(i).getEnode()).getLat());
			if (d < dist) {
				dist = d;
				id = Edges.get(i).getId();
			}
		}
		return id;// Edge ID
	}

	public static double p2pDistance(double lon1, double lat1, double lon2,
			double lat2) {
		return Math.sqrt(Math.pow((lon1 - lon2), 2)
				+ Math.pow((lat1 - lat2), 2));
	}

	public static double p2lDistance(double lon, double lat, double llon1,
			double llat1, double llon2, double llat2) {
		double x = llon1 - llon2, y = llat1 - llat2;
		double a = 0, b = 0, c = 0;
		if (x != 0 && y != 0) {
			a = y / x;
			b = -1;
			c = llat1 - a * llon1;
		} else if (x == 0) {
			a = 1;
			c = -llon1;
		} else if (y == 0) {
			b = -1;
			c = llat1;
		}
		return Math.abs(a * lon + b * lat + c)
				/ Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
	}
}
