package Routing;

import Reader.Graph;
import Utility.Point2D;

public class Routing {
	public Response Routing(Request req, Graph g) {
		Point2D sp = new Point2D(req.slon, req.slat);
		Point2D ep = new Point2D(req.elon, req.elat);
		int se = sp.getClosest(g.nodelist, g.waylist);
		int ee = ep.getClosest(g.nodelist, g.waylist);
		
		return new Response();
	}
}
