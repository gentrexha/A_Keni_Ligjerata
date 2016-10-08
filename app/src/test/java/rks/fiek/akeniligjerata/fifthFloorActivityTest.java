package rks.fiek.akeniligjerata;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;


@RunWith(JUnit4.class)
public class fifthFloorActivityTest {

    fifthFloorActivity myTest;
    @Before
    public void setUp() throws Exception {
        myTest = new fifthFloorActivity();
    }

    @Test
    public void closeMatch() throws Exception {
        boolean res = myTest.closeMatch(125, 150, 150);
        assertEquals(true, res);
    }

}