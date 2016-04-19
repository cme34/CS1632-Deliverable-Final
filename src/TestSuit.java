import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test Suit To Run All Unit Tests In The Program
 */
@RunWith(Suite.class)
@SuiteClasses({ GameTest.class, CardTest.class, BettingSystemTest.class, OutcomesTest.class})
public class TestSuit {
	
}
