package m0useclicker.f1standings;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class TeamTest {

    private final int position = 1;
    private final String name = "nameHere";
    private final int points = 2;
    private final Team team = new Team(position, name, points);

    @Test
    public void getPosition() throws Exception {
        assertEquals(position, team.getPosition());
    }

    @Test
    public void getName() throws Exception {
        assertEquals(name, team.getName());
    }

    @Test
    public void getPoints() throws Exception {
        assertEquals(points, team.getPoints());
    }
}