package datavalidator.old;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import util.FileManager;

public class ValidateData {

	public static void main(String[] args) throws IOException {
		// checkEachLineOfFiles();
		// createFiles();
		checkEachLinesAutoToFiles();
		// checkAlignmentWordsAndPOS();
	}

	public static void createFiles() throws IOException {

		String path = "data/joey_uncleaned/s14_";
		String[] filenames = { "guzman", "ley", "manaysay", "meija", "merle", "obuchi", "perez", "realica", "surema",
				"uy", "uy-baretta", "vasquez", "boracay", "velasco" };

		for (String f : filenames) {
			File fileWord = new File(path + f + "_words.txt");
			File fileTag = new File(path + f + "_tags.txt");
			File fileLemm = new File(path + f + "_lemmas.txt");
			if (!fileWord.exists())
				fileWord.createNewFile();
			if (!fileTag.exists())
				fileTag.createNewFile();
			if (!fileLemm.exists())
				fileLemm.createNewFile();
		}
	}

	public static void checkAlignmentWordsAndPOS() throws FileNotFoundException, IOException {
		String path = "data/joey_with_alignment_errors/";
		File folder = new File(path);
		File[] files = folder.listFiles();

		List<File> posFiles = new ArrayList<>();
		List<File> wordFiles = new ArrayList<>();

		for (File f : files) {
			if (f.getName().contains("Tags"))
				posFiles.add(f);
			else if (f.getName().contains("Words"))
				wordFiles.add(f);
		}

		for (int i = 0; i < posFiles.size(); i++) {
			File pos = posFiles.get(i);
			File word = wordFiles.get(i);

			List<String> posLines = FileManager.readFile(pos);
			List<String> wordLines = FileManager.readFile(word);
			System.out.println("Yo");
			if (posLines.size() != wordLines.size()) {
				System.out.println(
						pos.getName() + "  Number of lines in files are not equal. Please restart the program");
			} else {
				String errorPath = pos.getParent() + "/errors/"
						+ pos.getName().substring(0, pos.getName().length() - 10) + "errors.txt";
				System.out.println(errorPath);
				File outputFile = new File(errorPath);
				if (outputFile.exists()) {
					outputFile.delete();
					outputFile.createNewFile();
				}
				PrintWriter outFile = new PrintWriter(new FileWriter(outputFile.getAbsolutePath(), true));

				for (int j = 0; j < posLines.size(); j++) {
					String[] twoSplit = posLines.get(j).split(" ");
					String[] threeSplit = wordLines.get(j).split(" ");

					if (twoSplit.length != threeSplit.length) {
						outFile.println(pos.getName() + "   Line: " + (j + 1) + " | Lengths : " + twoSplit.length + ","
								+ threeSplit.length);

						for (String b : twoSplit)
							outFile.printf("%20s", b);
						outFile.println();
						for (String c : threeSplit)
							outFile.printf("%20s", c);
						outFile.println();
					}
				}
				outFile.close();
			}
		}
	}

	public static void checkEachLinesAutoToFiles() throws FileNotFoundException, IOException {
		String path = "data/joey_with_alignment_errors/";
		File folder = new File(path);
		File[] files = folder.listFiles();

		List<File> lemmaFiles = new ArrayList<>();
		List<File> posFiles = new ArrayList<>();
		List<File> wordFiles = new ArrayList<>();

		for (File f : files) {
			if (f.getName().contains("lemmas"))
				lemmaFiles.add(f);
			else if (f.getName().contains("tags"))
				posFiles.add(f);
			else if (f.getName().contains("words"))
				wordFiles.add(f);
		}

		for (int i = 0; i < lemmaFiles.size(); i++) {
			File lem = lemmaFiles.get(i);
			File pos = posFiles.get(i);
			File word = wordFiles.get(i);

			List<String> lemLines = FileManager.readFile(lem);
			List<String> posLines = FileManager.readFile(pos);
			List<String> wordLines = FileManager.readFile(word);
			System.out.println("Yo");
			if (lemLines.size() != posLines.size() || posLines.size() != wordLines.size()) {
				System.out.println(
						lem.getName() + "  Number of lines in files are not equal. Please restart the program");
			} else {
				String errorPath = lem.getParent() + "/errors/"
						+ lem.getName().substring(0, lem.getName().length() - 10) + "errors.txt";
				System.out.println(errorPath);
				File outputFile = new File(errorPath);
				if (outputFile.exists()) {
					outputFile.delete();
					outputFile.createNewFile();
				}
				PrintWriter outFile = new PrintWriter(new FileWriter(outputFile.getAbsolutePath(), true));

				for (int j = 0; j < lemLines.size(); j++) {
					String[] oneSplit = lemLines.get(j).split(" ");
					String[] twoSplit = posLines.get(j).split(" ");
					String[] threeSplit = wordLines.get(j).split(" ");

					if (oneSplit.length != twoSplit.length || oneSplit.length != threeSplit.length
							|| twoSplit.length != threeSplit.length) {
						outFile.println(lem.getName() + "   Line: " + (j + 1) + " | Lengths : " + oneSplit.length + ","
								+ twoSplit.length + "," + threeSplit.length);

						for (String a : oneSplit)
							outFile.printf("%20s", a);
						outFile.println();
						for (String b : twoSplit)
							outFile.printf("%20s", b);
						outFile.println();
						for (String c : threeSplit)
							outFile.printf("%20s", c);
						outFile.println();
					}
				}
				outFile.close();
			}
		}

	}

	public static void checkEachLineOfFiles() throws FileNotFoundException, IOException {
		String path = "data/joey_cleaned_with_slight_errors/";
		File folder = new File(path);
		File[] files = folder.listFiles();

		List<File> lemmaFiles = new ArrayList<>();
		List<File> posFiles = new ArrayList<>();
		List<File> wordFiles = new ArrayList<>();

		for (File f : files) {
			if (f.getName().contains("lemmas"))
				lemmaFiles.add(f);
			else if (f.getName().contains("tags"))
				posFiles.add(f);
			else if (f.getName().contains("words"))
				wordFiles.add(f);
		}

		for (int i = 0; i < lemmaFiles.size(); i++) {
			File lem = lemmaFiles.get(i);
			File pos = posFiles.get(i);
			File word = wordFiles.get(i);

			List<String> lemLines = FileManager.readFile(lem);
			List<String> posLines = FileManager.readFile(pos);
			List<String> wordLines = FileManager.readFile(word);

			for (int j = 0; j < lemLines.size(); j++) {
				String[] lemSplit = lemLines.get(j).split(" ");
				String[] posSplit = posLines.get(j).split(" ");
				String[] wordSplit = wordLines.get(j).split(" ");

				if (lemSplit.length != posSplit.length || lemSplit.length != wordSplit.length
						|| posSplit.length != wordSplit.length) {

					System.out.println(word.getName() + "   Line: " + j + " " + lemSplit.length + " " + posSplit.length
							+ " " + wordSplit.length);
					for (String l : lemSplit)
						System.out.printf("%30s", l);
					System.out.println();
					for (String p : posSplit)
						System.out.printf("%30s", p);
					System.out.println();
					for (String w : wordSplit)
						System.out.printf("%30s", w);
					System.out.println();

				}

			}

		}
	}
}
