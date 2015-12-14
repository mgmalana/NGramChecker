package revised.util;

public class ArrayToStringConverter {

	public static String convert(Object[] arr) {
		StringBuilder s = new StringBuilder();

		for (int i = 0; i < arr.length; i++) {
			s.append(arr[i]);
			if (i - 1 < arr.length)
				s.append(" ");
		}
		return s.toString();
	}
}
