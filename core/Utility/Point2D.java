package Utility;

import Reader.Edges;
import Reader.Nodes;

public class Point2D {
	protected double lon;
	protected double lat;
	protected double tmplon;// Temporary Memorize the closest point's longitude
							// on edges
	protected double tmplat;// Temporary Memorize the closest point's latitude
							// on edges

	public Point2D(double lon, double lat) {
		this.lon = lon;
		this.lat = lat;
	}

	public Point2D() {
		// TODO Auto-generated constructor stub
	}

	public int getClosest(Nodes N, Edges E) {
		int id = 0;
		double dist = Double.MAX_VALUE;
		double x1, x2, y1, y2;
		for (int i = 0, len = E.size(); i < len; i++) {
			x1 = N.get(E.get(i).getSnode()).getLon();
			y1 = N.get(E.get(i).getSnode()).getLat();
			x2 = N.get(E.get(i).getEnode()).getLon();
			y2 = N.get(E.get(i).getEnode()).getLat();
			double d = p2lDistance(lon, lat, x1, y1, x2, y2);
			if (d < dist) {
				id = E.get(i).getId();
				dist = d;
				if (lon >= (x1 > x2 ? x1 : x2) || lon <= (x1 > x2 ? x2 : x1)
						|| lat >= (y1 > y2 ? y1 : y2)
						|| lat <= (y1 > y2 ? y2 : y1)) {
					tmplon = p2pDistance(lon, lat, x1, y1) > p2pDistance(lon,
							lat, x2, y2) ? x2 : x1;
					tmplat = p2pDistance(lon, lat, x1, y1) > p2pDistance(lon,
							lat, x2, y2) ? y2 : y1;
				} else {
					tmplon = p2lPoint(lon, lat, x1, y1, x2, y2).lon;
					tmplat = p2lPoint(lon, lat, x1, y1, x2, y2).lat;
				}
			}

		}
		return id;// Return Nearby Edge ID
	}

	public double p2pDistance(double lon1, double lat1, double lon2, double lat2) {
		return Math.sqrt(Math.pow((lon1 - lon2), 2)
				+ Math.pow((lat1 - lat2), 2));
	}

	public double p2lDistance(double lon, double lat, double llon1,
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

	public Point2D p2lPoint(double lon, double lat, double llon1, double llat1,
			double llon2, double llat2) {
		double x = llon1 - llon2, y = llat1 - llat2;
		double xout = 0, yout = 0;
		double a1 = 0, c1 = 0;
		double a2 = 0, c2 = 0;
		if (x != 0 && y != 0) {
			a1 = y / x;
			c1 = llat1 - a1 * llon1;
			a2 = -1 / a1;
			c2 = lat - a2 * lon;
			xout = (c2 - c1) / (a1 - a2);
			yout = a2 * xout + c2;
		} else if (x == 0) {
			xout = llon1;
			yout = lat;
		} else if (y == 0) {
			xout = lon;
			yout = llat1;
		}

		return new Point2D(xout, yout);
	}

	public String toString() {
		return "[" + tmplon + "," + tmplat + "]";
	}

}
