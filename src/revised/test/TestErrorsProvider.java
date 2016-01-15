package revised.test;

import java.util.ArrayList;
import java.util.List;

import revised.model.Input;

public class TestErrorsProvider {

	List<Input> testErrors;

	public TestErrorsProvider() {
		testErrors = new ArrayList<>();
		// Correct is QOT VBW CCB NNC PMC = 'ng' instead of 'nang'
		// Substitution
		testErrors.add(new Input("QOT sabi nang hari ,", "QOT sabi nang hari ,", "QOT VBW CCP NNC PMC"));

		// Correct is QOT VBW NNC PMC = add 'ng'
		// Addition
		testErrors.add(new Input("QOT sabi hari ,", "QOT sabi hari ,", "QOT VBW NNC PMC"));

		// Correct is QOT VBW CCB NNC PMC = delete 'na'
		// Deletion
		testErrors.add(new Input("QOT sabi na ng hari ,", "QOT sabi na ng hari ,", "QOT VBW CCP CCB NNC PMC"));

		// Correct - replace luto with niluto (suggestion 'luto should be past
		// tense')
		testErrors.add(new Input("Nasagi ng aso ang ulam na luto ko .", "sagi ng aso ang ulam na luto ko .",
				"VBTS CCB NNC DTC NNC CCP VBW PRS PMP"));

		testErrors.add(new Input("gamiit ng mga bata", "? ng mga bata", "? CCB DTCP NNC"));

		testErrors.add(new Input("kikunsinti ang babae", "? ang babae", "? DTC NNC"));

		testErrors.add(new Input("subalit naketa ku", "pero ? ?", "CCT ? ?"));

		testErrors.add(new Input("kana ang damit", "? ang damit", "? DTC NNC"));

		// Correct nanalo ng premyo - VBTS CCB NNC
		testErrors.add(new Input("nanalo premyo", "panalo premyo", "VBTS NNC"));

		// Correct materyal para sa - NNC CCT CCT
		testErrors.add(new Input("materyal para na sa", "materyal para na sa", "NNC CCT CCP CCT"));

		testErrors.add(new Input("tinaka ko sa", "taka ko sa", "? PRS CCT"));

		// Correct para sa bata
		testErrors.add(new Input("para kay bata", "para sa bata", "RBB RBB NNC"));
	}

	public List<Input> getTestErrors() {
		return testErrors;
	}
}
