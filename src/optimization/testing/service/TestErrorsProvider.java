package optimization.testing.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import optimization.models.Input;

public class TestErrorsProvider {

	List<Input> testErrors;

	@SuppressWarnings("resource")
	private List<Input> getTestErrorsFromFile(String filename_words, String filename_lemmas, String filename_tags)
			throws IOException {
		List<Input> testErrors = new ArrayList<>();
		BufferedReader sourceLemmasReader;
		BufferedReader sourceSentencesReader;
		BufferedReader sourceTagsReader;
		sourceSentencesReader = new BufferedReader(new FileReader(filename_words));
		sourceLemmasReader = new BufferedReader(new FileReader(filename_lemmas));
		sourceTagsReader = new BufferedReader(new FileReader(filename_tags));

		String l, s, p;
		while ((l = sourceLemmasReader.readLine()) != null) {
			s = sourceSentencesReader.readLine();
			p = sourceTagsReader.readLine();
			System.out.println(s + " " + p + " " + l);
			testErrors.add(new Input(s, p, l));
		}

		return testErrors;
	}

	public List<Input> getTestErrors(String filename_words, String filename_lemmas, String filename_tags)
			throws IOException {
		return getTestErrorsFromFile(filename_words, filename_lemmas, filename_tags);
	}
}
