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

	public TestErrorsProvider() {
		testErrors = new ArrayList<>();
		// Correct is QOT VBW CCB NNC PMC = 'ng' instead of 'nang'
		// Substitution
		testErrors.add(new Input("QOT sabi nang hari ,", "QOT VBW CCP NNC PMC", "QOT sabi nang hari ,"));

		// Correct is QOT VBW NNC PMC = add 'ng'
		// Addition
		testErrors.add(new Input("QOT sabi hari ,", "QOT VBW NNC PMC", "QOT sabi hari ,"));

		// Correct is QOT VBW CCB NNC PMC = delete 'na'
		// Deletion
		testErrors.add(new Input("QOT sabi na ng hari ,", "QOT VBW CCP CCB NNC PMC", "QOT sabi na ng hari ,"));

		// Correct - replace luto with niluto (suggestion 'luto should be past
		// tense')
		testErrors.add(new Input("Nasagi ng aso ang ulam na luto ko .", "VBTS CCB NNC DTC NNC CCP VBW PRS PMP",
				"sagi ng aso ang ulam na luto ko ."));

		testErrors.add(new Input("gamiit ng mga bata", "? CCB DTCP NNC", "? ng mga bata"));

		testErrors.add(new Input("kikunsinti ang babae", "? DTC NNC", "? ang babae"));

		testErrors.add(new Input("subalit naketa ku", "CCT ? ?", "pero ? ?"));

		testErrors.add(new Input("kana ang damit", "? DTC NNC", "? ang damit"));

		// Correct nanalo ng premyo - VBTS CCB NNC
		testErrors.add(new Input("nanalo premyo", "VBTS NNC", "panalo premyo"));

		// Correct materyal para sa - NNC CCT CCT
		testErrors.add(new Input("materyal para na sa", "NNC CCT CCP CCT", "materyal para na sa"));

		testErrors.add(new Input("tinaka ko sa", "? PRS CCT", "taka ko sa"));

		// Correct para sa bata
		testErrors.add(new Input("para kay bata", "RBB RBB NNC", "para sa bata"));
	}

	@SuppressWarnings("resource")
	private List<Input> getTestErrorsFromFile() throws IOException {
		List<Input> testErrors = new ArrayList<>();
		BufferedReader sourceLemmasReader;
		BufferedReader sourceSentencesReader;
		BufferedReader sourceTagsReader;
		sourceSentencesReader = new BufferedReader(new FileReader(Constants.TEST_SENTENCES));
		sourceLemmasReader = new BufferedReader(new FileReader(Constants.TEST_LEMMAS));
		sourceTagsReader = new BufferedReader(new FileReader(Constants.TEST_TAGS));

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
