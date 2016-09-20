package datavalidator;

import java.io.File;
import java.io.IOException;

public class CreateFilesForNewWorksheets {

	public static void main(String[] args) throws IOException {
		createFiles();
	}

	public static void createFiles() throws IOException {

		String path = "data/joey_uncleaned/t_EW_";
		String[] filenames = { "14", "15", "16", "283", "289", "19", "20", "21", "22", "23", "295", "25", "26", "27",
				"22", "28", "301" };

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
