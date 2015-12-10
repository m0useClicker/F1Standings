package m0useclicker.f1standings;

import junit.framework.TestCase;

public class PilotTest extends TestCase {
    private final int position = 1;
    private final String name = "nameHere";
    private final String nationality = "nationalityHere";
    private final String team = "teamHere";
    private final int points = 2;
    private final Pilot pilot = new Pilot(position, name, nationality, team, points);

    public void testGetPosition() throws Exception {
        assertEquals(position,pilot.getPosition());
    }

    public void testGetName() throws Exception {
        assertEquals(name,pilot.getName());
    }

    public void testGetNationality() throws Exception {
        assertEquals(nationality,pilot.getNationality());
    }

    public void testGetTeam() throws Exception {
        assertEquals(team,pilot.getTeam());
    }

    public void testGetPoints() throws Exception {
        assertEquals(points,pilot.getPoints());
    }
}