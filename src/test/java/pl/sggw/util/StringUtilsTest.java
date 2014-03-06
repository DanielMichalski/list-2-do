package pl.sggw.util;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class StringUtilsTest {

	@Test
	public void shouldReturnTrueWithBlank() {
		//given
		String string = "";

		//when
		boolean isBlank = StringUtils.isBlank(string);

		//then
		assertTrue(isBlank);
	}

	@Test
	public void shouldReturnTrueWithNull() {
		//given
		String string = null;

		//when
		boolean isBlink = StringUtils.isBlank(string);

		//then
		assertTrue(isBlink);
	}

}