import static org.junit.Assert.*;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

public class QAJobsSenderTest {

	@Rule
	public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

	@Test
	public void testReadInput() {
		systemInMock.provideLines("qa tester", "toronto", "john doe",
				"mockemail@coldmail.com", "111222333", "resumes/file.txt");
		List<String> expectedList = new ArrayList<String>(Arrays.asList(
				"qa tester", "toronto", "john doe", "mockemail@coldmail.com",
				"111222333", "resumes/file.txt"));
		assertEquals(expectedList,
				QAJobsSender.readInput(new ArrayList<String>()));
	}

	// @Test
	public void testFillOutFormAndSubmit() {
		fail("Not yet implemented");
	}

}
