package optimization.training.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import util.ArrayToStringConverter;
import util.Constants;

public class GrammaticallyCorrectTestDataGenerator {

	public static void main(String[] args) throws InterruptedException {
		GrammaticallyCorrectTestDataGenerator tdg = new GrammaticallyCorrectTestDataGenerator();
		tdg.readFiles();
	}

	public void readFiles() throws InterruptedException {
		File folder = new File(Constants.FOR_INPUT_TEST_DATA);
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

		List<FileNGramGenerator> ts = new ArrayList<>();
		for (int ngramSize = 2; ngramSize <= 7; ngramSize++) {

			FileNGramGenerator t = new FileNGramGenerator(wordFiles, posFiles, lemmaFiles, ngramSize);
			ts.add(t);
			t.start();
		}
	}

}

class FileNGramGenerator extends Thread {

	List<File> wordFiles;
	List<File> posFiles;
	List<File> lemmaFiles;
	private int ngramSize;

	public FileNGramGenerator(List<File> wordFiles, List<File> posFiles, List<File> lemmaFiles, int ngramSize) {
		this.wordFiles = wordFiles;
		this.posFiles = posFiles;
		this.lemmaFiles = lemmaFiles;
		this.ngramSize = ngramSize;
	}

	@Override
	public void run() {
		BufferedReader sourceLemmasReader;
		BufferedReader sourceSentencesReader;
		BufferedReader sourceTagsReader;

		try {
			for (int n = 0; n < lemmaFiles.size(); n++) {
				File wordFile = wordFiles.get(n);
				File lemmaFile = lemmaFiles.get(n);
				File posFile = posFiles.get(n);

				sourceSentencesReader = new BufferedReader(new FileReader(wordFile.getAbsolutePath()));
				sourceLemmasReader = new BufferedReader(new FileReader(lemmaFile.getAbsolutePath()));
				sourceTagsReader = new BufferedReader(new FileReader(posFile.getAbsolutePath()));

				System.out.println(
						ngramSize + " " + wordFile.getName() + " " + lemmaFile.getName() + " " + posFile.getName());

				String l, s, p;

				while ((l = sourceLemmasReader.readLine()) != null) {
					s = sourceSentencesReader.readLine();
					p = sourceTagsReader.readLine();

					s = s.trim();
					p = p.trim();
					l = l.trim();

					String[] sArr = s.split(" ");

					String[] lArr = l.split(" ");

					String[] pArr = p.split(" ");

					for (int i = 0; i + ngramSize - 1 < sArr.length; i++) {
						String[] ngramWords = Arrays.copyOfRange(sArr, i, i + ngramSize);
						String words = ArrayToStringConverter.convert(ngramWords);
						String[] ngramLemmas = Arrays.copyOfRange(lArr, i, i + ngramSize);
						String lemmas = ArrayToStringConverter.convert(ngramLemmas);
						String[] ngramPos = Arrays.copyOfRange(pArr, i, i + ngramSize);
						String pos = ArrayToStringConverter.convert(ngramPos);

						writeToFile(words, lemmas, pos, ngramSize);
					}
				}
			}
			System.out.println(ngramSize + " Done");
		} catch (IOException e)

		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void writeToFile(String words, String lemmas, String pos, int ngramSize) throws IOException {
		File folder = new File(Constants.FOR_NGRAM_TEST_DATA);
		File fileWord = new File(folder.getPath() + "/" + ngramSize + "_words.txt");
		File fileTag = new File(folder.getPath() + "/" + ngramSize + "_tags.txt");
		File fileLemm = new File(folder.getPath() + "/" + ngramSize + "_lemmas.txt");
		System.out.println(fileWord.getPath());
		if (!fileWord.exists())
			fileWord.createNewFile();
		if (!fileTag.exists())
			fileTag.createNewFile();
		if (!fileLemm.exists())
			fileLemm.createNewFile();
		words = words + "\n";
		lemmas = lemmas + "\n";
		pos = pos + "\n";
		try {
			Files.write(Paths.get(fileWord.getPath()), words.getBytes(), StandardOpenOption.APPEND);
			Files.write(Paths.get(fileTag.getPath()), pos.getBytes(), StandardOpenOption.APPEND);
			Files.write(Paths.get(fileLemm.getPath()), lemmas.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			// exception handling left as an exercise for the reader
		}
	}
}
