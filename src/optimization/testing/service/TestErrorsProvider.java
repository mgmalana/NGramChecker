package optimization.testing.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import optimization.models.Input;
import util.Constants;

public class TestErrorsProvider {

	List<Input> testErrors;

	@SuppressWarnings("resource")
	private List<Input> getTestErrorsFromFile() throws IOException {
		List<Input> testErrors = new ArrayList<>();
		BufferedReader sourceLemmasReader;
		BufferedReader sourceSentencesReader;
		BufferedReader sourceTagsReader;
		sourceSentencesReader = new BufferedReader(new FileReader(Constants.TEST2_SENTENCES));
		sourceLemmasReader = new BufferedReader(new FileReader(Constants.TEST2_LEMMAS));
		sourceTagsReader = new BufferedReader(new FileReader(Constants.TEST2_TAGS));

		String l, s, p;
		while ((l = sourceLemmasReader.readLine()) != null) {
			s = sourceSentencesReader.readLine();
			p = sourceTagsReader.readLine();
			testErrors.add(new Input(s, p, l));
		}

		return testErrors;
	}

	public List<Input> getTestErrors() throws IOException {
		return getTestErrorsFromFile();
	}
}
