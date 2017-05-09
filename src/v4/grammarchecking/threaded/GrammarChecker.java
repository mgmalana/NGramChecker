package v4.grammarchecking.threaded;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.poi.util.IOUtils;
import spellchecker.spellCheck.SpellChecker;
import util.ArrayToStringConverter;
import util.Constants;
import util.FileManager;
import v4.models.Input;
import v4.models.Suggestion;
import v4.models.SuggestionToken;
import v4.models.SuggestionType;
import v4.pos.POSTagger;

import morphinas.Stemmer.Stemmer;

public class GrammarChecker {

	static TestErrorsProvider testErrorsProvider = new TestErrorsProvider();
	static SubstitutionService subService;
	static InsertionAndUnmergingService insAndUnmService;
	static DeletionAndMergingService delAndMerService;
	private static String[] delimiters = { ".", ",", "!", "?"};
	private static boolean isVerbose = true;

	public static void main(String[] args) throws IOException, InterruptedException {
		// 0, 4, 11, 14 - working
		// 5 - not
//		Input testError = testErrorsProvider.getTestErrors().get(0);
//		List<Suggestion> suggestions = checkGrammar(testError);
		List<Suggestion> suggestions = checkGrammar("Ako ay nag punta sa banko.");
	}

	 public static List<Suggestion> checkGrammar(String words) throws IOException, InterruptedException {
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

		 String pos = String.join(" ", posList);
		 String lemmas = String.join(" ", lemmasList);

		return checkGrammar(words, lemmas, pos);
	 }

	 private static String transformDelimiters(String words){
		 for (String mark : delimiters)
			 words = words.replace(mark, " " + mark);
		return words;
	 }

	public static List<Suggestion> checkGrammar(String words, String pos, String lemmas) throws IOException, InterruptedException {
		return checkGrammar(new Input(words, pos, lemmas));
	}

	private static List<Suggestion> checkGrammar(Input testError) throws InterruptedException, IOException {
		FileManager fm = new FileManager(Constants.RESULTS_ALL);

		fm.createFile();
		if(isVerbose){
			System.out.println("Writing Suggestions to Files");

			System.out.println("Full: " + ArrayToStringConverter.convert(testError.getWords()) + " \n"
					+ ArrayToStringConverter.convert(testError.getPos()) + "\n"
					+ ArrayToStringConverter.convert(testError.getLemmas()) + " " + testError.getWords().length);
		}
		fm.writeToFile("Full: " + ArrayToStringConverter.convert(testError.getWords()) + " \n"
				+ ArrayToStringConverter.convert(testError.getPos()) + "\n"
				+ ArrayToStringConverter.convert(testError.getLemmas()) + " " + testError.getWords().length);

		long startTime = System.currentTimeMillis();

		List<Suggestion> allSuggestions = new ArrayList<>();

		for (int ngramSize = Constants.NGRAM_SIZE_UPPER; ngramSize >= Constants.NGRAM_SIZE_LOWER; ngramSize--) {
			if(isVerbose) {
				System.out.println("N-gram = " + ngramSize);
			}
			fm.writeToFile("N-gram = " + ngramSize);

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

				//only gets top suggestions for each ngram
				if(suggs.size() > 0){
					double maxScore =  suggs.get(0).getEditDistance(); //TODO: ASk matthew

					for(Suggestion sugg : suggs){
						if(maxScore == sugg.getEditDistance()){
							allSuggestions.add(sugg);
						} else {
							break;
						}
					}
				}


//				allSuggestionss.addAll(suggs);

				fm.writeToFile("...............................\n");
				for (Suggestion s : suggs) {
					ArrayList<String> arrSugg = new ArrayList<String>(Arrays.asList(wArr));
					fm.writeToFile("Orig N-gram:" + ArrayToStringConverter.convert(wArr));
					fm.writeToFile("ED: " + s.getEditDistance() + " Freq: " + s.getFrequency());
					if (s.getEditDistance() == 0)
						fm.writeToFile("N-gram is correct");
					else {
						for (SuggestionToken sugg : s.getSuggestions()) {
							if (sugg.getSuggType() == SuggestionType.SUBSTITUTION) {
								if (sugg.isPOSGeneralized() == false) {
									// System.out
									// .println("Replace " + sugg.getWord() + "
									// in " + arrSugg.get(sugg.getIndex())
									// + ". " + " Edit Distance: " +
									// sugg.getEditDistance());
									fm.writeToFile("Replace " + sugg.getWord() + " in " + arrSugg.get(sugg.getIndex())
											+ ". " + " Edit Distance: " + sugg.getEditDistance());
									arrSugg.set(sugg.getIndex(), sugg.getWord());
								} else {
									// System.out.println("TReplace " +
									// sugg.getPos() + "(" + sugg.getWord() +
									// ")" + " in "
									// + arrSugg.get(sugg.getIndex()) + ". " + "
									// Edit Distance:"
									// + sugg.getEditDistance());
									fm.writeToFile("TReplace " + sugg.getPos() + "(" + sugg.getWord() + ")" + " in "
											+ arrSugg.get(sugg.getIndex()) + ". " + " Edit Distance:"
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
									fm.writeToFile("Insert " + sugg.getWord() + " in before " + wArr[sugg.getIndex()]
											+ ". " + " Edit Distance:" + sugg.getEditDistance());
									arrSugg.add(sugg.getIndex(), sugg.getWord());
								} else {
									// System.out.println("Insert " +
									// sugg.getPos() + " in before " +
									// wArr[sugg.getIndex()]
									// + ". " + " Edit Distance:" +
									// sugg.getEditDistance());
									fm.writeToFile("Insert " + sugg.getPos() + "(" + sugg.getWord() + ")"
											+ " in before " + wArr[sugg.getIndex()] + ". " + " Edit Distance:"
											+ sugg.getEditDistance());
									arrSugg.add(sugg.getIndex(), sugg.getPos());
								}
							} else if (sugg.getSuggType() == SuggestionType.UNMERGING) {
								// System.out.println("Unmerge " +
								// wArr[sugg.getIndex()] + ". " + " Edit
								// Distance:"
								// + sugg.getEditDistance());
								fm.writeToFile("Unmerge " + wArr[sugg.getIndex()] + ". " + " Edit Distance:"
										+ sugg.getEditDistance());
								arrSugg.set(sugg.getIndex(), sugg.getWord());
							} else if (sugg.getSuggType() == SuggestionType.DELETION) {
								// System.out.println("Delete " +
								// wArr[sugg.getIndex()] + ". " + " Edit
								// Distance:"
								// + sugg.getEditDistance());
								fm.writeToFile("Delete " + wArr[sugg.getIndex()] + ". " + " Edit Distance:"
										+ sugg.getEditDistance());
								arrSugg.remove(sugg.getIndex());
							} else if (sugg.getSuggType() == SuggestionType.MERGING) {
								// System.out.println("Merge " +
								// wArr[sugg.getIndex()] + ". " + " Edit
								// Distance:"
								// + sugg.getEditDistance());
								fm.writeToFile("Merge " + wArr[sugg.getIndex()] + ". " + " Edit Distance:"
										+ sugg.getEditDistance());
								arrSugg.set(sugg.getIndex(), sugg.getWord());
								arrSugg.remove(sugg.getIndex() + 1);
							}
							fm.writeToFile("Sugg: " + ArrayToStringConverter.convert(arrSugg));
						}

					}
				}
				fm.writeToFile("...............................\n");
			}
		}
		fm.close();

		long endTime = System.currentTimeMillis();

		if(isVerbose) {
			System.out.println("Total Grammar Checking Time Elapsed: " + (endTime - startTime));
		}
		return allSuggestions;

	}

	private static String findTagger_model() throws IOException {
		String folder =  "tagger_models";
		String filename = "/filipino-left3words-owlqn2-pref6.tagger";
		String path = folder + filename;

		if(GrammarChecker.class.getResource("GrammarChecker.class").toString().startsWith("jar:")){ //if running from jar

			Files.createDirectories(Paths.get("results")); //create results folder

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
}
