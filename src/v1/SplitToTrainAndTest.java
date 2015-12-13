package v1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SplitToTrainAndTest {
	// 434
	static File sourceLemmas = new File("lemmas.txt");
	static File sourceSentences = new File("sentences.txt");
	static File sourceTags = new File("tags.txt");
	static File trainLemmas = new File("train_lemmas.txt");
	static File trainSentences = new File("train_sentences.txt");
	static File trainTags = new File("train_tags.txt");
	static File testLemmas = new File("test_lemmas.txt");
	static File testSentences = new File("test_sentences.txt");
	static File testTags = new File("test_tags.txt");

	public static void main(String[] args) throws IOException {
		createFile(trainLemmas);
		createFile(trainSentences);
		createFile(trainTags);
		createFile(testLemmas);
		createFile(testSentences);
		createFile(testTags);

		FileWriter trainLemmasWriter = new FileWriter(trainLemmas.getName(), true);
		BufferedWriter trainLemmasBWriter = new BufferedWriter(trainLemmasWriter);

		FileWriter trainSentencesWriter = new FileWriter(trainSentences.getName(), true);
		BufferedWriter trainSentencesBWriter = new BufferedWriter(trainSentencesWriter);

		FileWriter trainTagsWriter = new FileWriter(trainTags.getName(), true);
		BufferedWriter trainTagsBWriter = new BufferedWriter(trainTagsWriter);

		FileWriter testLemmasWriter = new FileWriter(testLemmas.getName(), true);
		BufferedWriter testLemmasBWriter = new BufferedWriter(testLemmasWriter);

		FileWriter testSentencesWriter = new FileWriter(testSentences.getName(), true);
		BufferedWriter testSentencesBWriter = new BufferedWriter(testSentencesWriter);

		FileWriter testTagsWriter = new FileWriter(testTags.getName(), true);
		BufferedWriter testTagsBWriter = new BufferedWriter(testTagsWriter);

		String lemma, sentence, tag;

		BufferedReader sourceLemmasReader = new BufferedReader(new FileReader(sourceLemmas.getName()));
		BufferedReader sourceSentencesReader = new BufferedReader(new FileReader(sourceSentences.getName()));
		BufferedReader sourceTagsReader = new BufferedReader(new FileReader(sourceTags.getName()));

		int i = 0;
		while ((lemma = sourceLemmasReader.readLine()) != null) {
			sentence = sourceSentencesReader.readLine();
			tag = sourceTagsReader.readLine();
			lemma = lemma.replace("   ", " ").trim();
			sentence = sentence.replace("   ", " ").trim();
			tag = tag.replace("   ", " ").trim();

			if (i % 5 == 0) {
				System.out.println("Test");
				testLemmasBWriter.write(lemma + "\n");
				testSentencesBWriter.write(sentence + "\n");
				testTagsBWriter.write(tag + "\n");
			} else {
				System.out.println("Train");
				trainLemmasBWriter.write(lemma + "\n");
				trainSentencesBWriter.write(sentence + "\n");
				trainTagsBWriter.write(tag + "\n");
			}
			i++;
		}

		trainLemmasBWriter.close();
		trainSentencesBWriter.close();
		trainTagsBWriter.close();
		testLemmasBWriter.close();
		testSentencesBWriter.close();
		testTagsBWriter.close();
	}

	private static void createFile(File file) throws IOException {
		if (file.exists())
			file.delete();
		file.createNewFile();
	}

}
