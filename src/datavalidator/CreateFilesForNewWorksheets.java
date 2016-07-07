package datavalidator;

import java.io.File;
import java.io.IOException;

public class CreateFilesForNewWorksheets {

	public static void main(String[] args) throws IOException {
		createFiles();
	}

	public static void createFiles() throws IOException {

		String path = "data/joey_uncleaned/s14_";
		String[] filenames = { "zapanta" };

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
}
