package grammarchecker.grammarchecking.threaded;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import org.apache.poi.util.IOUtils;
import spellchecker.spellCheck.SpellChecker;
import util.ArrayToStringConverter;
import util.Constants;
import util.FileManager;
import grammarchecker.models.Input;
import grammarchecker.models.Suggestion;
import grammarchecker.models.SuggestionToken;
import grammarchecker.models.SuggestionType;
import grammarchecker.pos.POSTagger;

import morphinas.Stemmer.Stemmer;

public class GrammarChecker {

	static TestErrorsProvider testErrorsProvider = new TestErrorsProvider();
	static SubstitutionService subService;
	static InsertionAndUnmergingService insAndUnmService;
	static DeletionAndMergingService delAndMerService;
	private static String[] delimiters = { ".", ",", "!", "?"};
	private boolean isVerbose;
	private boolean isGenerateTextFile;
	private int nGramSizeToGet = 1;
	private final static int maxSuggestionPerNgram = 5;
	private FileManager fm;

	public static void main(String[] args) throws IOException, InterruptedException {
		try {
			GrammarChecker grammarChecker = new GrammarChecker(false, true, 5);

			for(String sugg: grammarChecker.getGrammarSuggestions(args[0])){
				System.out.println(sugg);
			}
		} catch (ArrayIndexOutOfBoundsException e){
			System.out.println("Please add the sentence argument to runnable jar");
		}


	}

	public GrammarChecker(){
		this(false, false, 5);
	}

	public GrammarChecker(boolean isVerbose, boolean isGenerateTextFile, int nGramSizeToGet){
		this.isVerbose = isVerbose;
		this.isGenerateTextFile = isGenerateTextFile;
		this.nGramSizeToGet = nGramSizeToGet;
	}

	public String[] getGrammarSuggestions(String words) throws IOException, InterruptedException {
		List <Suggestion> suggestions = checkGrammar(words);
		List <String> stringSuggs = new ArrayList<>();

		for (Suggestion s: suggestions){
			for (SuggestionToken sugg : s.getSuggestions()){
				stringSuggs.add(sugg.getSuggestionString());
			}
		}

		return stringSuggs.toArray(new String[stringSuggs.size()]);
	}

	/**
	 * Detects and suggests grammar corrections of a sentence
	 *
	 * @param words input words
	 * @return correction suggestions of the sentence
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public List<Suggestion> checkGrammar(String words) throws IOException, InterruptedException {
		POSTagger postagger = new  POSTagger(findTagger_model());
		Stemmer stemmer = new Stemmer();
		SpellChecker spellChecker = new SpellChecker();

		words = transformDelimiters(words);

		String[] wordList = words.split(" ");
		List<String> posList = postagger.tagSentence(words).getTags();
		String[] lemmasList = stemmer.lemmatizeMultiple(wordList);

		for(int i = 0; i < wordList.length; i++){
			if(!spellChecker.isWordValid(wordList[i])){
				lemmasList[i] = "?";
				posList.set(i, "?");
			}
		}

		String pos = arrayToString(posList);
		String lemmas = arrayToString(lemmasList);

		return checkGrammar(words, pos, lemmas);
	}

	/**
	 * Detects and suggests grammar corrections of a sentence
	 * @param words input words
	 * @param pos POS tags of the sentence
	 * @param lemmas lemmas of the sentence
	 * @return correction suggestions of the sentence
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public List<Suggestion> checkGrammar(String words, String pos, String lemmas) throws IOException, InterruptedException {
		return checkGrammar(new Input(words, lemmas, pos));
	}

	private List<Suggestion> checkGrammar(Input testError) throws InterruptedException, IOException {
		fm = new FileManager(Constants.RESULTS_ALL);

		if(this.isGenerateTextFile){
			fm.createFile();
		}
		if(isVerbose){
			System.out.println("Writing Suggestions to Files");

			System.out.println("Full: " + ArrayToStringConverter.convert(testError.getWords()) + " \n"
					+ ArrayToStringConverter.convert(testError.getPos()) + "\n"
					+ ArrayToStringConverter.convert(testError.getLemmas()) + " " + testError.getWords().length);
		}

		this.writeToFile("Full: " + ArrayToStringConverter.convert(testError.getWords()) + " \n"
				+ ArrayToStringConverter.convert(testError.getPos()) + "\n"
				+ ArrayToStringConverter.convert(testError.getLemmas()) + " " + testError.getWords().length);

		long startTime = System.currentTimeMillis();

		List<Suggestion> topSuggestions = new ArrayList<>();

		int size = Constants.NGRAM_SIZE_UPPER;

		if(this.nGramSizeToGet >= Constants.NGRAM_SIZE_LOWER && this.nGramSizeToGet <= Constants.NGRAM_SIZE_UPPER){
			size = nGramSizeToGet;
		}

		for (int ngramSize = size; ngramSize >= Constants.NGRAM_SIZE_LOWER; ngramSize--) {
			if(isVerbose) {
				System.out.println("N-gram = " + ngramSize);
			}
			this.writeToFile("N-gram = " + ngramSize);

			topSuggestions.addAll(getNgramSuggestions(ngramSize, testError));
		}


		if(isGenerateTextFile) {
			fm.close();
		}
		long endTime = System.currentTimeMillis();

		if(isVerbose) {
			System.out.println("Total Grammar Checking Time Elapsed: " + (endTime - startTime));
		}
		return topSuggestions;

	}

	private List<Suggestion> getNgramSuggestions(int ngramSize, Input testError) throws InterruptedException, IOException {
		List<Suggestion> ngramSuggestions = new ArrayList<>();

		for (int i = 0; i + ngramSize - 1 < testError.getWords().length; i++) {
			String[] wArr = Arrays.copyOfRange(testError.getWords(), i, i + ngramSize);
			String[] pArr = Arrays.copyOfRange(testError.getPos(), i, i + ngramSize);
			String[] lArr = Arrays.copyOfRange(testError.getLemmas(), i, i + ngramSize);

			List<Suggestion> suggs = new ArrayList<>();

			subService = new SubstitutionService();
			subService.setInputValues(wArr, lArr, pArr, ngramSize);
			subService.start();
			subService.join();
			suggs.addAll(subService.getSuggestions());

			if (ngramSize <= Constants.NGRAM_SIZE_UPPER - 1) {
				insAndUnmService = new InsertionAndUnmergingService();
				insAndUnmService.setInputValues(wArr, lArr, pArr, ngramSize);
				insAndUnmService.start();
				insAndUnmService.join();
				suggs.addAll(insAndUnmService.getSuggestions());

			}
			if (ngramSize >= Constants.NGRAM_SIZE_LOWER + 1) {
				delAndMerService = new DeletionAndMergingService();
				delAndMerService.setInputValues(wArr, lArr, pArr, ngramSize);
				delAndMerService.start();
				delAndMerService.join();
				suggs.addAll(delAndMerService.getSuggestions());
			}

			suggs = sortSuggestions(suggs);
			if(suggs.size() > 0){
				int index = 0;
				double nGramMinDistance = suggs.get(0).getEditDistance();

				for(Suggestion sugg : suggs){
					if(index < this.maxSuggestionPerNgram && nGramMinDistance == sugg.getEditDistance() && sugg.getEditDistance() > 0){
						index++;
					} else {
						break;
					}
				}

				if(index > 0){
					suggs = suggs.subList(0, index);
					ngramSuggestions.addAll(suggs);
				}
			}


			this.writeToFile("...............................\n");
			for (Suggestion s : suggs) {
				ArrayList<String> arrSugg = new ArrayList<>(Arrays.asList(wArr));
				this.writeToFile("Orig N-gram:" + ArrayToStringConverter.convert(wArr));
				this.writeToFile("ED: " + s.getEditDistance() + " Freq: " + s.getFrequency());
				if (s.getEditDistance() == 0)
					this.writeToFile("N-gram is correct");
				else {
					for (SuggestionToken sugg : s.getSuggestions()) {
						String stringCorrection = "";

						if (sugg.getSuggType() == SuggestionType.SUBSTITUTION) {
							if (sugg.isPOSGeneralized() == false) {
								// System.out
								// .println("Replace " + sugg.getWord() + "
								// in " + arrSugg.get(sugg.getIndex())
								// + ". " + " Edit Distance: " +
								// sugg.getEditDistance());
								stringCorrection = "Replace \"" + sugg.getWord() + "\" in \"" + arrSugg.get(sugg.getIndex()) + "\"";
								this.writeToFile(stringCorrection + ". Edit Distance: " + sugg.getEditDistance());
								arrSugg.set(sugg.getIndex(), sugg.getWord());
							} else {
								// System.out.println("TReplace " +
								// sugg.getPos() + "(" + sugg.getWord() +
								// ")" + " in "
								// + arrSugg.get(sugg.getIndex()) + ". " + "
								// Edit Distance:"
								// + sugg.getEditDistance());
								stringCorrection = "Replace \"" + sugg.getWord() + "\" in \""
										+ arrSugg.get(sugg.getIndex()) + "\"";

								this.writeToFile(stringCorrection + " Edit Distance:"
										+ sugg.getEditDistance());
								arrSugg.set(sugg.getIndex(), sugg.getPos());
							}
						} else if (sugg.getSuggType() == SuggestionType.INSERTION) {
							if (sugg.isPOSGeneralized() == false) {
								// System.out
								// .println("Insert " + sugg.getWord() + "
								// in before " + wArr[sugg.getIndex()]
								// + ". " + " Edit Distance:" +
								// sugg.getEditDistance());
								stringCorrection = "Insert \"" + sugg.getWord() + "\" in before \"" + wArr[sugg.getIndex()] + "\"";

								this.writeToFile(stringCorrection + ". Edit Distance:" + sugg.getEditDistance());
								arrSugg.add(sugg.getIndex(), sugg.getWord());
							} else {
								// System.out.println("Insert " +
								// sugg.getPos() + " in before " +
								// wArr[sugg.getIndex()]
								// + ". " + " Edit Distance:" +
								// sugg.getEditDistance());
								stringCorrection = "Insert \"" + sugg.getWord()
										+ "\" in before \"" + wArr[sugg.getIndex()] + "\"";

								this.writeToFile(stringCorrection + ". Edit Distance:"
										+ sugg.getEditDistance());
								arrSugg.add(sugg.getIndex(), sugg.getPos());
							}
						} else if (sugg.getSuggType() == SuggestionType.UNMERGING) {
							// System.out.println("Unmerge " +
							// wArr[sugg.getIndex()] + ". " + " Edit
							// Distance:"
							// + sugg.getEditDistance());
							stringCorrection = "Unmerge \"" + wArr[sugg.getIndex()] + "\"";
							this.writeToFile(stringCorrection + ". Edit Distance:"
									+ sugg.getEditDistance());
							arrSugg.set(sugg.getIndex(), sugg.getWord());
						} else if (sugg.getSuggType() == SuggestionType.DELETION) {
							// System.out.println("Delete " +
							// wArr[sugg.getIndex()] + ". " + " Edit
							// Distance:"
							// + sugg.getEditDistance());
							stringCorrection = "Delete \"" + wArr[sugg.getIndex()] + "\"";
							this.writeToFile(stringCorrection + ". Edit Distance:"
									+ sugg.getEditDistance());
							arrSugg.remove(sugg.getIndex());
						} else if (sugg.getSuggType() == SuggestionType.MERGING) {
							// System.out.println("Merge " +
							// wArr[sugg.getIndex()] + ". " + " Edit
							// Distance:"
							// + sugg.getEditDistance());
							stringCorrection = "Merge \"" + wArr[sugg.getIndex()] + "\"";
							this.writeToFile(stringCorrection + ". Edit Distance:"
									+ sugg.getEditDistance());
							arrSugg.set(sugg.getIndex(), sugg.getWord());
							arrSugg.remove(sugg.getIndex() + 1);
						}
						String suggToConvert = ArrayToStringConverter.convert(arrSugg);

						this.writeToFile("Sugg: " + suggToConvert);
						sugg.setSuggestionString(stringCorrection);
					}
				}
			}
			this.writeToFile("...............................\n");
		}

		return ngramSuggestions;
	}

	private static String transformDelimiters(String words){
		for (String mark : delimiters)
			words = words.replace(mark, " " + mark);
		return words;
	}

	private String findTagger_model() throws IOException {
		String folder =  "tagger_models";
		String filename = "/filipino-left3words-owlqn2-pref6.tagger";
		String path = folder + filename;

		if(GrammarChecker.class.getResource("GrammarChecker.class").toString().startsWith("jar:")){ //if running from jar

			if(this.isGenerateTextFile) {
				Files.createDirectories(Paths.get("results")); //create results folder
			}
			if(Files.notExists(Paths.get(path))) {//if tagger_models folder is not present
				Files.createDirectories(Paths.get(folder)); //create folder if does not exist

				InputStream in = GrammarChecker.class.getResourceAsStream(filename);
				OutputStream out = new FileOutputStream(path);
				IOUtils.copy(in, out);
			}
		}

		return path;

	}

	private static List<Suggestion> sortSuggestions(List<Suggestion> suggestions) {
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

	private void writeToFile(String toWrite) throws IOException {
		if(isGenerateTextFile){
			fm.writeToFile(toWrite);
		}
	}

	private String arrayToString(String[] list){
		String temp = "";

		for (String word: list){
			temp += " " + word;
		}
		if (temp.length() > 0) {
			temp = temp.substring(1);
		}
		return temp;
	}

	private String arrayToString(List<String> list){
		String temp = "";

		for (String word: list){
			temp += " " + word;
		}

		if (temp.length() > 0) {
			temp = temp.substring(1);
		}

		return temp;
	}
}
