package revised.datavalidator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import revised.util.FileManager;

public class ValidateData {

	public static void main(String[] args) throws IOException {
		checkEachLineOfFiles();
	}

	public static void createFiles() throws IOException {

		String path = "data/joey/";
		String[] filenames = { "250", "251", "252", "253to258", "259", "260", "261", "263", "264" };

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
						System.out.printf("%15s", l);
					System.out.println();
					for (String p : posSplit)
						System.out.printf("%15s", p);
					System.out.println();
					for (String w : wordSplit)
						System.out.printf("%15s", w);
					System.out.println();

				}

			}

		}
	}
}
