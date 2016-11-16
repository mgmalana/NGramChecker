package datavalidator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import util.Constants;
import util.FileManager;

public class LaurenzLemmaTestDataAlignment {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		checkAlignment(Constants.TEST_JOEY_CORRECT_SENTENCES_TAGS, Constants.TEST_JOEY_CORRECT_SENTENCES_TAGS_HPOST);
	}

	private static void checkAlignment(String goldStandard, String input) throws FileNotFoundException, IOException {

		List<String> goldList = FileManager.readFile(new File(goldStandard));
		List<String> inputList = FileManager.readFile(new File(input));

		double incorrectLemmas = 0;
		double totalLemmas = 0;
		for (int i = 0; i < inputList.size(); i++) {
			if (inputList.get(i).length() > 0 && inputList.get(i).charAt(inputList.get(i).length() - 1) == ' ') {
				inputList.set(i, inputList.get(i).substring(0, inputList.get(i).length() - 1));
			}
			if (goldList.get(i).length() > 0 && goldList.get(i).charAt(goldList.get(i).length() - 1) == ' ') {
				goldList.set(i, goldList.get(i).substring(0, goldList.get(i).length() - 1));
			}

			String[] inputs = inputList.get(i).split(" ");
			String[] golds = goldList.get(i).split(" ");
			if (inputs.length != golds.length)
				System.out.println(
						"Line #" + (i + 1) + " Inputs' Length: " + inputs.length + " Golds' Length: " + golds.length);

			totalLemmas += inputs.length;

			boolean isEqual = true;
			for (int j = 0; j < inputs.length; j++) {
				if (!inputs[j].equalsIgnoreCase(golds[j])) {
					incorrectLemmas += 1;
					isEqual = false;
				}
			}
			if (isEqual == false) {
				System.out.println("Line #" + (i + 1) + " Input: " + inputList.get(i));
				System.out.println("Line #" + (i + 1) + " Gold: " + goldList.get(i));
			}
		}
		double score = (totalLemmas - incorrectLemmas) / totalLemmas;
		System.out.println("Incorrect- " + incorrectLemmas + " Total- " + totalLemmas + " Correct- "
				+ (totalLemmas - incorrectLemmas));
		System.out.println("Tagging Accuracy: " + score);
	}

}
