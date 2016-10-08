package rks.fiek.akeniligjerata;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class fourthFloorActivityTest {

    fourthFloorActivity myTest;
    @Before
    public void setUp() throws Exception
    {
        myTest = new fourthFloorActivity();
    }

    @Test
    public void closeMatch() throws Exception
    {
        boolean test = myTest.closeMatch(225, 251, 178);
        assertEquals(true, test);
    }

}