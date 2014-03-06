package pl.sggw.util;

import org.junit.Test;
import pl.sggw.util.time.DateUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertTrue;

public class DateUtilTest {

	private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SSS");

	@Test
	public void shouldResetTime() {
		//given
		Date dateWithTime = null;
		Date dateWithOutTime = null;
		try {
			dateWithTime = dateFormat.parse("20/12/2005 12:43:20:412");
			dateWithOutTime = dateFormat.parse("20/12/2005 00:00:00:000");
		} catch (ParseException e) {
			e.printStackTrace();
			assertTrue(false);
		}

		//when
		Date dateWithResetTime = DateUtil.resetTime(dateWithTime);

		//then
		assertTrue(dateWithResetTime.equals(dateWithOutTime));
	}

}