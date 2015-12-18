package m0useclicker.f1standings;

import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.junit.Before;

public class UITests extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity activity;

    public UITests() {
        super(MainActivity.class);
    }

    @Before
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(true);
        activity = getActivity();
    }

    private ViewPager getPager() {
        return (ViewPager) activity.findViewById(R.id.pager);
    }

    private PagerTitleStrip getPagerTitleStrip() {
        return (PagerTitleStrip) getPager().findViewById(R.id.pager_title_strip);
    }

    private ListView getPilotsListView() {
        return (ListView) getPager().findViewById(R.id.pilotsList);
    }

    private ListView getTeamsListView() {
        return (ListView) getPager().findViewById(R.id.teamsList);
    }

    @MediumTest
    public void testPagerIsVisible() {
        final View decorView = activity.getWindow().getDecorView();
        ViewAsserts.assertOnScreen(decorView, getPager());
    }

    @MediumTest
    public void testPilotsListIsVisible() {
        final View decorView = activity.getWindow().getDecorView();
        ListView pilots = getPilotsListView();
        ViewAsserts.assertOnScreen(decorView, pilots);
    }

    @MediumTest
    public void testTeamsListIsVisible() {
        final View decorView = activity.getWindow().getDecorView();
        ListView teams = getTeamsListView();
        ViewAsserts.assertOnScreen(decorView, teams);
    }

    @MediumTest
    public void testPilotsListIsShownOnStartUp() {
        ListView pilots = getPilotsListView();
        assertTrue(pilots.isFocused());
    }

    @MediumTest
    public void testTeamsListIsNotShownOnStartUp() {
        ListView teams = getTeamsListView();
        assertFalse(teams.isFocused());
    }

    @MediumTest
    public void testPilotsPageTitle() {
        TextView pilotsTitle = (TextView) getPagerTitleStrip().getChildAt(1);
        assertEquals("Pilots", pilotsTitle.getText());
    }

    @MediumTest
    public void testTeamsPageTitle() {
        TextView pilotsTitle = (TextView) getPagerTitleStrip().getChildAt(2);
        assertEquals("Teams", pilotsTitle.getText());
    }

    @MediumTest
    public void testPilotsListHasItems() {
        ListView pilotsList = getPilotsListView();
        assertTrue(pilotsList.getChildCount() > 0);
    }

    @MediumTest
    public void testTeamsListHasItems() {
        int childCount = getTeamsListView().getChildCount();
        assertTrue("There is no items in teams list.", childCount > 0);
    }

    @MediumTest
    public void testAdapterPilotsPageTitle() {
        assertEquals("Pilots", activity.standingsPageAdapter.getPageTitle(0));
    }

    @MediumTest
    public void testAdapterTeamsPageTitle() {
        assertEquals("Teams", activity.standingsPageAdapter.getPageTitle(1));
    }

    @MediumTest
    public void testNumberOfPages() {
        assertEquals(2, activity.standingsPageAdapter.getCount());
    }
}
