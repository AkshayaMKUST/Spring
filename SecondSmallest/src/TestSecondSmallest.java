

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * @author 208995
 *
 */
/**
 * @author 208995
 *
 */



@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestSecondSmallest {
    private static SecondSmallest obj = null;

    /**
     *
     */
    @BeforeClass
    public static void init() {
        System.out.println("init method");
        obj = new SecondSmallest();

    }

    /**
     *
     */
    @AfterClass
    public static void distroy() {
        System.out.println("destroy method");
        obj = null;

    }

    /**
     * positive test case
     */
    @Test
    public void testSecondSmallest() {
        int[] input = { 10, -10, 10, -2, 3, 4, 5 };
        try {
            int output = obj.secondSmallest(input);
            assertEquals(-2, output);

        } catch (NullPointerException e) {
            fail();
        }

    }

    /**
     * negative test case
     */
    @Test
    public void testSecondSmallest1() {
        int[] input = {};
        try {
            obj.secondSmallest(input);

        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
        }

    }

    /**
     * NullpointerException test case
     */
    @Test(expected = NullPointerException.class)
    public void testSecondSmallest2() {
        int[] input = null;

        obj.secondSmallest(input);

    }

}