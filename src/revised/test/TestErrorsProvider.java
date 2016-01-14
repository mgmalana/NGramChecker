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

	}

	public List<Input> getTestErrors() {
		return testErrors;
	}
}
