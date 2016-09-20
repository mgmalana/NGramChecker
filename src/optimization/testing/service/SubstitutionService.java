package optimization.testing.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import optimization.dao.WordPOSLemmaMapDao;
import optimization.models.HybridNGram;
import optimization.models.Input;
import optimization.models.Suggestion;
import optimization.models.WordLemmaPOSMap;
import util.Constants;
import util.EditDistanceService;
import util.deepcopy.DeepCopy;
import v4.models.SuggestionType;

public class SubstitutionService {

	WordPOSLemmaMapDao wplmDao;
	List<WordLemmaPOSMap> dictionary;

	public SubstitutionService() throws SQLException {
		wplmDao = new WordPOSLemmaMapDao();
		dictionary = wplmDao.getWords();
	}

	public List<Suggestion> performTask(Input input, int indexOffset, int ngramSize)
			throws SQLException, CloneNotSupportedException {
		List<HybridNGram> candidatesHGrams = CandidateNGramService
				.getCandidateNGramsSubstitutionPermutation(input.getPos(), ngramSize);
		// check if may ka-equal na ito. If meron, stop and return null;
		List<Suggestion> suggestions = new ArrayList<>();
		if (candidatesHGrams == null)
			return suggestions;
		// List<Suggestion> spellSuggestions = spellCheck(input, indexOffset,
		// candidatesHGrams);
		// use suggestions to check if the formed result is grammatically
		// correct. If yes, output it. If not, ignore
		boolean isGrammaticallyCorrect = isGrammaticallyCorrect(input, candidatesHGrams, ngramSize);
		if (isGrammaticallyCorrect /*
									 * && spellSuggestions != null &&
									 * spellSuggestions.isEmpty()
									 */)
			return null;
		// if (isGrammaticallyCorrect && spellSuggestions != null &&
		// spellSuggestions.size() > 0)
		// return spellSuggestions;
		else {
			double min = Integer.MAX_VALUE;
			for (HybridNGram h : candidatesHGrams) {
				Suggestion s = computeSubstitutionEditDistance(input, indexOffset, h);
				if (s != null) {
					suggestions.add(s);
				}

			}
			sortSuggestions(suggestions);
			return suggestions;
		}
	}

	private List<Suggestion> sortSuggestions(List<Suggestion> suggestions) {
		if (suggestions.size() > 0) {
			Collections.sort(suggestions, new Comparator<Suggestion>() {
				@Override
				public int compare(final Suggestion object1, final Suggestion object2) {
					return object1.getEditDistance() < object2.getEditDistance() ? -1
							: object1.getEditDistance() > object2
									.getEditDistance()
											? 1
											: (object1.getEditDistance() == object2.getEditDistance()
													&& object1.getFrequency() > object2.getFrequency())
															? -1
															: (object1.getEditDistance() == object2.getEditDistance()
																	&& object1.getFrequency() < object2.getFrequency())
																			? 1 : 0;
				}
			});
		}
		return suggestions;
	}

	private Suggestion computeSubstitutionEditDistance(Input input, int indexOffset, HybridNGram h)
			throws SQLException {
		double editDistance = 0;
		Suggestion suggestion = null;
		// System.out.println(
		// ArrayToStringConverter.convert(h.getPosTags()) + " " +
		// ArrayToStringConverter.convert(h.getIsHybrid()));
		for (int i = 0; i < input.getNgramSize(); i++) {
			if (editDistance > Constants.EDIT_DISTANCE_THRESHOLD) {
				return null;
			}
			boolean isEqualPOS = h.getPosTags()[i].equalsIgnoreCase(input.getPos()[i]);
			if (isEqualPOS && h.getIsHybrid()[i] == true) {
				;
			} else if (isEqualPOS && h.getIsHybrid()[i] == false) {
				if (input.getWords()[i].equalsIgnoreCase(h.getNonHybridWords()[i]))
					;
				else if (withinSpellingEditDistance(input.getWords()[i], h.getNonHybridWords()[i])) {
					editDistance += Constants.EDIT_DISTANCE_SPELLING_ERROR;
					String[] tokenSuggestions = { h.getNonHybridWords()[i] };
					suggestion = new Suggestion(SuggestionType.SUBSTITUTION, tokenSuggestions, h.getIsHybrid()[i],
							h.getPosTags()[i], indexOffset + i, editDistance, h.getBaseNGramFrequency());
				} else {
					editDistance += Constants.EDIT_DISTANCE_WRONG_WORD_SAME_POS;
					String[] tokenSuggestions = { h.getNonHybridWords()[i] };
					suggestion = new Suggestion(SuggestionType.SUBSTITUTION, tokenSuggestions, h.getIsHybrid()[i],
							h.getPosTags()[i], indexOffset + i, editDistance, h.getBaseNGramFrequency());
				}
			} else {
				List<String> wordsWithSameLemmaAndPOS = wplmDao.getWordMappingWithLemmaAndPOS(input.getLemmas()[i],
						h.getPosIDs()[i]);
				if (h.getIsHybrid()[i] == true && wordsWithSameLemmaAndPOS.size() > 0) {
					editDistance += Constants.EDIT_DISTANCE_WRONG_WORD_FORM;
					String[] tokenSuggestions = wordsWithSameLemmaAndPOS
							.toArray(new String[wordsWithSameLemmaAndPOS.size()]);
					suggestion = new Suggestion(SuggestionType.SUBSTITUTION, tokenSuggestions, h.getIsHybrid()[i],
							h.getPosTags()[i], indexOffset + i, editDistance, h.getBaseNGramFrequency());

				} else {
					List<String> wordsGivenPOS = wplmDao.getWordsGivenPosID(h.getPosIDs()[i]);
					List<String> similarWords = getSimilarWords(input.getWords()[i], wordsGivenPOS);
					if (similarWords.size() > 0) {
						editDistance += Constants.EDIT_DISTANCE_SPELLING_ERROR;
						String[] tokenSuggestions = similarWords.toArray(new String[similarWords.size()]);
						suggestion = new Suggestion(SuggestionType.SUBSTITUTION, tokenSuggestions, h.getIsHybrid()[i],
								h.getPosTags()[i], indexOffset + i, editDistance, h.getBaseNGramFrequency());
					} else {
						editDistance += Constants.EDIT_DISTANCE_WRONG_WORD_DIFFERENT_POS;
						String[] tokenSuggestions = wordsGivenPOS.toArray(new String[wordsGivenPOS.size()]);
						suggestion = new Suggestion(SuggestionType.SUBSTITUTION, tokenSuggestions, h.getIsHybrid()[i],
								h.getPosTags()[i], indexOffset + i, editDistance, h.getBaseNGramFrequency());
					}
				}
			}
		}
		if (editDistance < Constants.EDIT_DISTANCE_THRESHOLD)
			return suggestion;

		return null;

	}

	private List<String> getSimilarWords(String input, List<String> wordsWithSamePOS) {
		List<String> suggestions = new ArrayList<>();
		for (String s : wordsWithSamePOS) {
			if (withinSpellingEditDistance(s, input)) {
				suggestions.add(s);
			}
		}
		return suggestions;
	}

	private List<Suggestion> spellCheck(Input input, int indexOffset, List<HybridNGram> candidatesHGrams)
			throws SQLException, CloneNotSupportedException {
		List<Suggestion> suggestions = new ArrayList<>();
		for (int i = 0; i < input.getWords().length; i++) {
			for (WordLemmaPOSMap dic : dictionary) {
				if (dic.getWord().toLowerCase().equals(input.getWords()[i])) {
					;
				} else if (withinOneSpellingEditDistance(dic.getWord(), input.getWords()[i])) {
					String[] suggestion = { dic.getWord() };

					Input inputClone = (Input) DeepCopy.copy(input);
					inputClone.setWord(dic.getWord(), i);
					inputClone.setPos(dic.getPosTag(), i);

					if (isGrammaticallyCorrect(inputClone, candidatesHGrams, input.getWords().length)) {
						suggestions.add(new Suggestion(SuggestionType.SUBSTITUTION, suggestion, false, dic.getPosTag(),
								indexOffset + i, Constants.EDIT_DISTANCE_SPELLING_ERROR, 0));
						System.out.println(
								"Within one SED: " + dic.getWord() + " " + dic.getPosTag() + " " + input.getWords()[i]);
					}
				}
			}
		}
		if (suggestions.size() == 0)
			return null;
		else
			return suggestions;
	}

	private boolean isGrammaticallyCorrect(Input input, List<HybridNGram> candidatesHGrams, int ngramSize)
			throws SQLException {

		for (HybridNGram h : candidatesHGrams) {
			if (Arrays.equals(h.getPosTags(), input.getPos())) {
				// System.out.println(ArrayToStringConverter.convert(input.getWords()));
				// System.out.println(ArrayToStringConverter.convert(h.getIsHybrid()));

				boolean isEqual = true;
				for (int i = 0; i < ngramSize; i++) {
					if (h.getIsHybrid()[i] == false
							&& !input.getWords()[i].equalsIgnoreCase(h.getNonHybridWords()[i])) {
						isEqual = false;
						break;
					}
				}
				if (isEqual == true) {
					return true;
				} else {
					break;
				}
			}
		}
		return false;
	}

	private boolean withinOneSpellingEditDistance(String corpusWord, String input) {
		corpusWord = corpusWord.toLowerCase();
		input = input.toLowerCase();
		if (input.equalsIgnoreCase(corpusWord))
			return false;

		int distance = EditDistanceService.computeLevenshteinDistance(corpusWord, input);

		if (input.length() <= 3)
			return false;
		else if (distance == 1)
			return true;
		return false;
	}

	private boolean withinSpellingEditDistance(String corpusWord, String input) {

		corpusWord = corpusWord.toLowerCase();
		input = input.toLowerCase();
		if (input.equalsIgnoreCase(corpusWord))
			return false;

		int distance = EditDistanceService.computeLevenshteinDistance(corpusWord, input);

		if (input.length() <= 3)
			return false;
		else if (distance <= 1 && input.length() <= 4)
			return true;
		else if (distance <= 2 && input.length() > 4 && input.length() <= 12)
			return true;
		else if (distance <= 3 && input.length() > 12)
			return true;
		else
			return false;
	}
}
