package rks.fiek.akeniligjerata;



import junit.framework.Assert;

import rks.fiek.akeniligjerata.mapsActivity;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class mapActivityTest extends
        ActivityInstrumentationTestCase2<mapsActivity> {

    public mapActivityTest()
    {
        super("rks.fiek.akeniligjerata",mapsActivity.class);
    }

    private mapsActivity mapTest;
    @Before
    public void setUp() throws Exception {
        mapTest = new mapsActivity();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void meterDistanceBetweenPoints() throws Exception {
        assertEquals(6.0d,mapTest.meterDistanceBetweenPoints(4d, 5d, 6d, 7d),0);
    }

}