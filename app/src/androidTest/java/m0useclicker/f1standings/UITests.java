package m0useclicker.f1standings;

import android.support.v4.view.ViewPager;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.View;
import android.widget.ListView;

import org.junit.Before;

public class UITests extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity activity;
    private ViewPager pager;

    public UITests() {
        super(MainActivity.class);
    }

    @Before
    protected void setUp() throws Exception {
        super.setUp();

        setActivityInitialTouchMode(true);

        activity = getActivity();
        pager = (ViewPager) activity.findViewById(R.id.pager);
    }

    @MediumTest
    public void testPagerIsVisible(){
        final View decorView = activity.getWindow().getDecorView();

        ViewAsserts.assertOnScreen(decorView, pager);
    }

    @MediumTest
    public void testPilotsListIsVisible(){
        final View decorView = activity.getWindow().getDecorView();

        ListView pilots = (ListView)pager.findViewById(R.id.pilotsList);

        ViewAsserts.assertOnScreen(decorView, pilots);
    }

    @MediumTest
    public void testTeamsListIsVisible(){
        final View decorView = activity.getWindow().getDecorView();

        ListView teams = (ListView)pager.findViewById(R.id.teamsList);

        ViewAsserts.assertOnScreen(decorView, teams);
    }

    @MediumTest
    public void testPilotsListIsShownOnStartUp(){
        ListView pilots = (ListView)pager.findViewById(R.id.pilotsList);
        assertTrue(pilots.isFocused());
    }

    @MediumTest
    public void testTeamsListIsNotShownOnStartUp(){
        ListView teams = (ListView)pager.findViewById(R.id.teamsList);
        assertFalse(teams.isFocused());
    }
}
