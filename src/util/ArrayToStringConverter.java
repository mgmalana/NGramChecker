package util;

public class ArrayToStringConverter {

	public static String convert(Object[] arr) {
		StringBuilder s = new StringBuilder();

		for (int i = 0; i < arr.length; i++) {
			s.append(arr[i]);
			if (i + 1 < arr.length)
				s.append(" ");
		}
		return s.toString();
	}

	public static Boolean[] stringToBoolArr(String bArr) {
		if (bArr != null) {
			String[] split = bArr.split(" ");
			Boolean[] bools = new Boolean[split.length];
			for (int i = 0; i < split.length; i++) {
				bools[i] = Boolean.parseBoolean(split[i]);
			}
			return bools;
		}
		return null;
	}
}
