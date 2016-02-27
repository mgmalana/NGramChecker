package revised.datavalidator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import revised.util.FileManager;

public class ValidateData {

	public static void main(String[] args) throws IOException {
		// checkEachLineOfFiles();
		createFiles();
		// checkEachLinesAutoToFiles();
	}

	public static void createFiles() throws IOException {

		String path = "data/joey_uncleaned/";
		String[] filenames = { "170", "171", "172", "175", "176", "177", "178", "179", "180", "137", "138", "139",
				"140", "141", "142", "143", "144", "145", "146", "147", "148", "149", "150", "163", "164", "165", "166",
				"167", "168", "169" };

		for (String f : filenames) {
			File fileWord = new File(path + "a56_" + f + "_words.txt");
			File fileTag = new File(path + "a56_" + f + "_tags.txt");
			File fileLemm = new File(path + "a56_" + f + "_lemmas.txt");
			if (!fileWord.exists())
				fileWord.createNewFile();
			if (!fileTag.exists())
				fileTag.createNewFile();
			if (!fileLemm.exists())
				fileLemm.createNewFile();
		}
	}

	public static void checkEachLinesAutoToFiles() throws FileNotFoundException, IOException {
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
		String path = "data/joey/";
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
