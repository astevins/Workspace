package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WorkTimerTest {
    private static final int DELAY = 200;
    WorkTimer timer;

    @BeforeEach
    public void setUp() {
        timer = new WorkTimer(10, Thread.currentThread());
        timer.setDelayForTesting(DELAY);
    }

    @Test
    public void testConstructor() {
        assertEquals("0:10:00", timer.getTime());
        assertEquals(10, timer.getMinutes());
        assertEquals(0, timer.getSeconds());
    }

    @Test
    public void testRunTimer() {
        timer.run();
        try {
            Thread.sleep(DELAY * 2 + 100);
        } catch (InterruptedException e) {
            fail();
        }
        int minutes = timer.getMinutes();
        int seconds = timer.getSeconds();
        assertEquals("0:09:58", timer.getTime());
        assertEquals(9, minutes);
        assertEquals(58, seconds);
    }

    @Test
    public void testCancelTimer() {
        timer.run();
        timer.cancelTimer();
        try {
            Thread.sleep(DELAY * 2);
        } catch (InterruptedException e) {
            fail();
        }
        assertEquals("0:10:00", timer.getTime());
    }

    @Test
    public void testTimeUp() {
        timer = new WorkTimer(0, Thread.currentThread());
        boolean interrupted = false;
        timer.run();

        try {
            Thread.sleep(DELAY + 1000);
        } catch (InterruptedException e) {
            interrupted = true;
        } finally {
            assertTrue(interrupted);
            assertEquals("0:00:00", timer.getTime());
        }
    }

    @Test
    public void testAddTime() {
        timer.run();
        timer.addTime(5);
        assertEquals(15, timer.getMinutes());
    }
}
