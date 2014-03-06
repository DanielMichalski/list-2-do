package pl.sggw.util;

/**
 * @author Daniel
 * @date 31.10.12
 */
public class StringUtils {

	public static boolean isBlank(String s) {
		return s == null || s.trim().length() == 0;
	}

	public static boolean isNotBlank(String s) {
		return !isBlank(s);
	}
}
