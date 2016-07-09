package util;

import java.util.List;

public class ArrayToStringConverter {

	public static String convert(List<String> arrList) {
		StringBuilder s = new StringBuilder();

		for (int i = 0; i < arrList.size(); i++) {
			s.append(arrList.get(i));
			if (i + 1 < arrList.size())
				s.append(" ");
		}
		return s.toString();
	}

	public static String convert(Object[] arr) {
		if (arr == null)
			return null;
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
