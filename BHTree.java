
public class BHTree {
	private Body body; // body or aggregate body stored in this node
	private Quad quad; // square region that the tree represents
	private BHTree NW; // tree representing northwest quadrant
	private BHTree NE; // tree representing northeast quadrant
	private BHTree SW; // tree representing southwest quadrant
	private BHTree SE; // tree representing southeast quadrant
	static double theta = 0.5; // threshold

	public BHTree(Quad q) {
		quad = q;
		
	}

	private boolean isExternal() {
		return NW == null && NE == null && SE == null && SW == null;
	}

	public void insert(Body b) {
		// First time no body is in tree
		if (this.body == null) {
			body = b;
			return;
		}
		// Internal Node
		else if (!isExternal()) {
			// Update COM
			this.body = Body.add(this.body, b);

			// Recursively insert
			if (b.in(quad.NW()))
				NW.insert(b);
			else if (b.in(quad.NE()))
				NE.insert(b);
			else if (b.in(quad.SE()))
				SE.insert(b);
			else if (b.in(quad.SW()))
				SW.insert(b);
		}
		// External Node
		else {
			Body c = this.body;
			NW = new BHTree(quad.NW());
			NE = new BHTree(quad.NE());
			SE = new BHTree(quad.SE());
			SW = new BHTree(quad.SW());

			// Recursively insert c first
			if (c.in(quad.NW()))
				NW.insert(c);
			else if (c.in(quad.NE()))
				NE.insert(c);
			else if (c.in(quad.SE()))
				SE.insert(c);
			else if (c.in(quad.SW()))
				SW.insert(c);
			// else System.out.println(c);
			// Then b
			if (b.in(quad.NW()))
				NW.insert(b);
			else if (b.in(quad.NE()))
				NE.insert(b);
			else if (b.in(quad.SE()))
				SE.insert(b);
			else if (b.in(quad.SW()))
				SW.insert(b);
			// else System.out.println(b);
			// Update COM
			this.body = Body.add(this.body, b);

		}
	}
	

	public void updateForce(Body b) {
		if (body == null || b.equals(body))
			return;

		// if single body, compute force
		if (isExternal()) {
			b.addForce(body);
		}
		// If body is 'sufficiently' far enough treat them as a single body
		else {
			if (this.quad.length() / this.body.distanceTo(b) < theta) {
				b.addForce(this.body);
			}
			// recursively compute the force of the children nodes
			else {
				if (this.NE != null)
					this.NE.updateForce(b);
				if (this.NW != null)
					this.NW.updateForce(b);
				if (this.SE != null)
					this.SE.updateForce(b);
				if (this.SW != null)
					this.SW.updateForce(b);
			}
		}
	}
	
	public String toString() {
        if (isExternal()) 
            return " " + body + "\n";
        else
            return "*" + body + "\n" + NW + NE + SW + SE;
    }
}
