package m0useclicker.f1standings;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class PilotTest {
    private final int position = 1;
    private final String name = "nameHere";
    private final String nationality = "nationalityHere";
    private final String team = "teamHere";
    private final int points = 2;
    private final Pilot pilot = new Pilot(position, name, nationality, team, points);

    @Test
    public void getPosition() throws Exception {
        assertEquals(position,pilot.getPosition());
    }

    @Test
    public void getName() throws Exception {
        assertEquals(name,pilot.getName());
    }

    @Test
    public void getNationality() throws Exception {
        assertEquals(nationality,pilot.getNationality());
    }

    @Test
    public void getTeam() throws Exception {
        assertEquals(team,pilot.getTeam());
    }

    @Test
    public void getPoints() throws Exception {
        assertEquals(points,pilot.getPoints());
    }
}