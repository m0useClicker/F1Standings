package m0useclicker.f1standings;

import junit.framework.TestCase;

public class TeamTest extends TestCase {

    private final int position = 1;
    private final String name = "nameHere";
    private final int points = 2;
    private final Team team = new Team(position, name, points);

    public void testGetPosition() throws Exception {
        assertEquals(position, team.getPosition());
    }

    public void testGetName() throws Exception {
        assertEquals(name, team.getName());
    }

    public void testGetPoints() throws Exception {
        assertEquals(points, team.getPoints());
    }
}