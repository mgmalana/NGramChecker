package spellchecker.spellCheck;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import com.BoxOfC.LevenshteinAutomaton.LevenshteinAutomaton;
import com.BoxOfC.MDAG.MDAG;

import spellchecker.corrector.MergeCorrection;
import spellchecker.corrector.SplitCorrection;
import spellchecker.detector.DictLookUp;
import spellchecker.detector.NGramStatistics;
import spellchecker.stemming.Stemmer;
import spellchecker.training.Train;
import spellchecker.utility.Tokenizer;

public class SpellChecker {

	private DictLookUp dictLookUp;
	private NGramStatistics nGramStats;
	private MergeCorrection MergeCorrection;
	private SplitCorrection splitCorrection;
	private MDAG filipinoDict;
	private Tokenizer tokenizer;

	private LinkedHashMap<String, LinkedHashSet<String>> wordSuggestion;
	private LinkedHashSet<String> candidateSuggestions;
	private Stemmer stemmer;

	/**
	 * Initialize all resources needed
	 */
	public SpellChecker() {
		Train train = new Train();
		train.initializeResource();

		// Initialized variables
		dictLookUp = train.getDictLookUp();
		nGramStats = train.getNGramStat();
		MergeCorrection = train.getMergeCorrection();
		splitCorrection = train.getSplitCorrection();
		filipinoDict = train.getFilipinoDict();
		stemmer = train.getStemmer();
		tokenizer = new Tokenizer();
	}

	/**
	 * Identify whether the word valid in the language or not.
	 * Include delimiters and Prounoun.
	 *
	 * @param inputWord
	 *            input word.
	 *
	 * @return True if the word is valid otherwise False.
	 */
	public boolean isWordValid(String inputWord) {
		// Skipping the word if it is a punctuation mark/ proper noun.
		if (tokenizer.isDelimeters(inputWord) || tokenizer.isProperNoun(inputWord)) {
			return true;
		}

		return isWord(inputWord);
	}

	/**
	 * Identify whether the word exists in the language or not.
	 *
	 * @param inputWord
	 *            input word.
	 *
	 * @return True if the word exists otherwise False.
	 */
	public boolean isWord(String inputWord) {

		// Skipping the word if it is a punctuation mark/ proper noun.
		if (tokenizer.isDelimeters(inputWord) || tokenizer.isProperNoun(inputWord)) {
			return false;
		}

		// ---------------------------------------------
		// ---------------------------------------------
		// ERROR DETECTION
		// ---------------------------------------------
		// ---------------------------------------------

		/***
		 * state(true/false) whether the word is present or absent in the
		 * dictionary.
		 */
		boolean inDictionary = dictLookUp.checkDict(inputWord);

		/***
		 * INFLECTED CASE: Stem the word and do a dictionary look-up
		 */
		if (!inDictionary) {
			// STEM OF THE FILIPINO WORD
			String word = stemmer.stemming(inputWord);
			inDictionary = dictLookUp.checkFiliDict(word);
		}

		/***
		 * CODE-SWITCHING: Check if the cWord is not in dictionary and contains
		 * hyphen
		 */
		if (!inDictionary && inputWord.contains("-")) {

			/*** prefix and borrowed word */
			String[] codeSwitchWord = inputWord.split("-");

			boolean isPrefix = stemmer.isPrefix(codeSwitchWord[0]);
			boolean isEngWord = dictLookUp.checkEngDict(codeSwitchWord[1]);

			if (isEngWord && !isPrefix) {
				return false;
			} else if (!isEngWord && isPrefix) {
				return false;
			}

			inDictionary = (isPrefix && isEngWord);
		}

		/***
		 * N-GRAM: Check the N-gram statistics if inputWord do not exists in the
		 * language.
		 */
		if (!inDictionary && !stemmer.isPrefix(inputWord)) {
			inDictionary = nGramStats.hasHighNGramStatistics(inputWord);
		}

		return inDictionary;
	}

	/**
	 * Detect and suggest correction of misspelled words in a sentence.
	 *
	 * @param sentence
	 *            input sentence.
	 *
	 * @return list of misspelled words and suggestions.
	 */
	public LinkedHashMap<String, LinkedHashSet<String>> checkSentence(String sentence) {
		wordSuggestion = new LinkedHashMap<>();

		/*** Array of words after tokenization. */
		String[] words = tokenizer.tokenize(sentence);

		// words.length-1 = remove the marker '*#'
		for (int wordCounter = 0; wordCounter < words.length - 1; wordCounter++) {

			/*** Current word in the sentence. */
			String cWord = words[wordCounter];
			candidateSuggestions = new LinkedHashSet<String>();

			// Skipping the word if it is a punctuation mark/ proper noun.
			if (tokenizer.isDelimeters(cWord) || tokenizer.isProperNoun(cWord)) {
				// candidateSuggestions.add("Delimeter / ProperNoun");
				// wordSuggestion.put(wordCounter + "_" + cWord,
				// candidateSuggestions);
				continue;
			}

			// ---------------------------------------------
			// ---------------------------------------------
			// ERROR DETECTION
			// ---------------------------------------------
			// ---------------------------------------------

			/***
			 * state(true/false) whether the word is present or absent in the
			 * dictionary.
			 */
			boolean inDictionary = dictLookUp.checkDict(cWord);

			/***
			 * INFLECTED CASE: Stem the word and do a dictionary look-up
			 */
			if (!inDictionary) {
				// STEM OF THE FILIPINO WORD
				String word = stemmer.stemming(cWord);
				inDictionary = dictLookUp.checkFiliDict(word);
			}

			/***
			 * CODE-SWITCHING: Check if the cWord is not in dictionary and
			 * contains hyphen
			 */
			if (!inDictionary && cWord.contains("-")) {

				/*** prefix and borrowed word */
				String[] codeSwitchWord = cWord.split("-");

				boolean isPrefix = stemmer.isPrefix(codeSwitchWord[0]);
				boolean isEngWord = dictLookUp.checkEngDict(codeSwitchWord[1]);

				if (isEngWord && !isPrefix) {
					candidateSuggestions.add("Wrong prefix");
					wordSuggestion.put(wordCounter + "_" + cWord, candidateSuggestions);
					continue;
				} else if (!isEngWord && isPrefix) {
					candidateSuggestions.add("Wrong English word");
					wordSuggestion.put(wordCounter + "_" + cWord, candidateSuggestions);
					continue;
				}

				inDictionary = (isPrefix && isEngWord);
			}

			/***
			 * N-GRAM: Check the N-gram statistics if cWord do not exists in the
			 * language.
			 */
			if (!inDictionary && !stemmer.isPrefix(cWord)) {
				inDictionary = nGramStats.hasHighNGramStatistics(cWord);
			}

			// ---------------------------------------------
			// ---------------------------------------------
			// ERROR CORRECTION FOR THE MISPPLED WORD
			// ---------------------------------------------
			// ---------------------------------------------

			/*** Next word in the sentence. */
			String nWord = words[wordCounter + 1];

			if (!inDictionary) {
				/*** Add split correction suggestions */
				candidateSuggestions = splitCorrection.splitSuggestion(cWord);

				/*** Add merge correction suggestions */
				String mergeCorrectionSuggestion = MergeCorrection.setConsecutiveWords(cWord, nWord);
				if (!mergeCorrectionSuggestion.equals("")) {
					// TO-DO: reconsider
					wordCounter++;
					candidateSuggestions.add(mergeCorrectionSuggestion);
				}

				/***
				 * Check if no suggestion produced by split-merge correction.
				 */
				if (candidateSuggestions.size() == 0) {
					/*** Add automaton correction suggestions */
					candidateSuggestions.addAll(LevenshteinAutomaton.iterativeFuzzySearch(2, cWord, filipinoDict));
				}
			}

			wordSuggestion.put(wordCounter + "_" + cWord, candidateSuggestions);

		}
		return wordSuggestion;
	}

	/**
	 * Detect and suggest correction of misspelled words for each sentence in a
	 * document.
	 *
	 * @param document
	 *            list of sentences.
	 *
	 * @return list sentences in the document with suggestions for each
	 *         misspelled word.
	 */
	public LinkedHashMap<String, LinkedHashMap<String, LinkedHashSet<String>>> checkDocument(
			ArrayList<String> document) {
		LinkedHashMap<String, LinkedHashMap<String, LinkedHashSet<String>>> documentSuggestion = new LinkedHashMap<>();
		for (String sentence : document) {
			LinkedHashMap<String, LinkedHashSet<String>> perSentence = checkSentence(sentence);
			documentSuggestion.put(sentence, perSentence);
		}
		return documentSuggestion;
	}

	/**
	 * Use the built-in stemming system of the spell checker to give the stem of
	 * the input word.
	 *
	 * @param word
	 *            input word.
	 *
	 * @return stem of the input word.
	 */
	public String checkStem(String word) {
		if (word.split(" ").length != 1)
			System.out.println("one word");
		else
			return stemmer.stemming(word);
		return word;
	}
}