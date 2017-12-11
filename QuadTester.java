/*************************************************************************
 *  Compilation:  javac QuadTester.java
 *  Execution:    java QuadTester
 *  Dependencies: Quad.java StdDraw.java
 *
 *  Client program to test constructor and methods of Quad.java
 *  for the Barnes-Hut project in COS126.
 *
 *
 *  % java QuadTester
 *  Universe quadrant: midpoint (0.0,0.0), length 20.0
 *      NW quadrant: midpoint (-5.0,5.0), length 10.0
 *      NE quadrant: midpoint (5.0,5.0), length 10.0
 *      SW quadrant: midpoint (-5.0,-5.0), length 10.0
 *      SE quadrant: midpoint (5.0,-5.0), length 10.0
 *
 *  Testing interior points.
 *
 *  Testing border points.
 *
 *************************************************************************/

public class QuadTester {

    public static void main(String[] args) {
        double radius = 10.0;        // radius of universe

        // create and print universe quad to test constructor
        Quad universe = new Quad(0.0, 0.0, 2.0*radius);
        System.out.println("Universe quadrant: " + universe);

        // test Quad.draw
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.YELLOW);
        StdDraw.setXscale(-radius, +radius);
        StdDraw.setYscale(-radius, +radius);
        universe.draw();

        // create and print four subquadrants to test NW, NE, SW and SE methods
        Quad nw = universe.NW();
        Quad ne = universe.NE();
        Quad sw = universe.SW();
        Quad se = universe.SE();
        System.out.println("     NW quadrant: " + nw);
        System.out.println("     NE quadrant: " + ne);
        System.out.println("     SW quadrant: " + sw);
        System.out.println("     SE quadrant: " + se);

        // test Quad.draw
        nw.draw();
        ne.draw();
        sw.draw();
        se.draw();
        nw.NW().draw();
        nw.NE().draw();
        nw.SE().draw();
        nw.SW().draw();

        // use interior points to test contains method
        System.out.println();
        System.out.println("Testing interior points.");
        if (!universe.contains(0.0, 0.0))
            System.out.println("Error: (0,0) not in universe");

        if (!nw.contains(-5.0, 5.0))
            System.out.println("Error: (-5, 5) not in NW quadrant");
        if (ne.contains(-5.0, 5.0))
            System.out.println("Error: (-5, 5) found in NE quadrant");
        if (sw.contains(-5.0, 5.0))
            System.out.println("Error: (-5, 5) found in SW quadrant");
        if (se.contains(-5.0, 5.0))
            System.out.println("Error: (-5, 5) found in SE quadrant");

        if (!ne.contains(5.0, 5.0))
            System.out.println("Error: (5, 5) not in NE quadrant");
        if (nw.contains(5.0, 5.0))
            System.out.println("Error: (5, 5) found in NW quadrant");
        if (sw.contains(5.0, 5.0))
            System.out.println("Error: (5, 5) found in SW quadrant");
        if (se.contains(5.0, 5.0))
            System.out.println("Error: (5, 5) found in SE quadrant");

        if (!sw.contains(-5.0, -5.0))
            System.out.println("Error: (-5, -5) not in SW quadrant");
        if (nw.contains(-5.0, -5.0))
            System.out.println("Error: (-5, -5) found in NW quadrant");
        if (ne.contains(-5.0, -5.0))
            System.out.println("Error: (-5, -5) found in NE quadrant");
        if (se.contains(-5.0, -5.0))
            System.out.println("Error: (-5, -5) found in SE quadrant");

        if (!se.contains(5.0, -5.0))
            System.out.println("Error: (5, -5) not in SE quadrant");
        if (nw.contains(5.0, -5.0))
            System.out.println("Error: (5, -5) found in NW quadrant");
        if (ne.contains(5.0, -5.0))
            System.out.println("Error: (5, -5) found in NE quadrant");
        if (sw.contains(5.0, -5.0))
            System.out.println("Error: (5, -5) found in SW quadrant");

        // testing 9 border points
        System.out.println();
        System.out.println("Testing border points.");
        for (int x = -10; x <= 10; x = x + 10) {
            for (int y = -10; y <= 10; y = y + 10) {
                int count = 0;
                if (nw.contains(x, y)) count++;
                if (ne.contains(x, y)) count++;
                if (sw.contains(x, y)) count++;
                if (se.contains(x, y)) count++;
                if (count == 0)
                    System.out.println("(" + x + "," + y + ") not in any quadrant.");
                if (count > 1)
                    System.out.println("(" + x + "," + y + ") in " + count + " quadrants.");
            }
        }

        System.out.println();
        System.out.println("Nothing printed above means every point tested occurred");
        System.out.println("in exactly one sub-quadrant. This useful for testing,");
        System.out.println("but does not indicate an error in your code.");
    }
}
