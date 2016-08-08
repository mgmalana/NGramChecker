package datavalidator;

import java.io.File;
import java.io.IOException;

public class CreateFilesForNewWorksheets {

	public static void main(String[] args) throws IOException {
		createFiles();
	}

	public static void createFiles() throws IOException {

		String path = "data/joey_uncleaned/t_EW_";
		String[] filenames = { "1", "2", "3", "4", "5", "271", "8", "9", "10", "277", "12", "13", "14", "15", "16",
				"283" };

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
