package optimization.testing.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import optimization.dao.WordPOSLemmaMapDao;
import optimization.models.Input;
import optimization.models.Suggestion;
import optimization.models.WordLemmaPOSMap;
import util.Constants;
import v4.models.SuggestionType;

public class RuleBasedService {

	WordPOSLemmaMapDao wplmDao;
	List<WordLemmaPOSMap> dictionary;

	public RuleBasedService() throws SQLException {
		wplmDao = new WordPOSLemmaMapDao();
		dictionary = wplmDao.getWords();
	}

	public List<Suggestion> performTask(Input input, int indexOffset, int ngramSize)
			throws SQLException, CloneNotSupportedException {
		List<Suggestion> suggestions = new ArrayList<>();

		Suggestion naNgSugg = getNaNgSuggestion(input, indexOffset);

		if (naNgSugg != null)
			suggestions.add(naNgSugg);

		Suggestion masUnmSugg = getMasUnmergingSuggestion(input, indexOffset);

		if (masUnmSugg != null)
			suggestions.add(masUnmSugg);

		List<Suggestion> affixMergeSugg = getAffixMergingSuggestion(input, indexOffset);

		if (affixMergeSugg != null)
			suggestions.addAll(affixMergeSugg);

		Suggestion rhSugg = getRemovedHyphenSuggestion(input, indexOffset);

		if (rhSugg != null)
			suggestions.add(rhSugg);

		return suggestions;
	}

	private Suggestion getNaNgSuggestion(Input input, int indexOffset) {

		for (int i = 0; i < input.getWords().length - 1; i++) {
			if (isVowel(input.getWords()[i].charAt(input.getWords()[i].length() - 1))
					&& input.getWords()[i + 1].equals("na")) {
				String suggestion = input.getWords()[i] + "ng";
				String[] tokenSuggestions = { suggestion };
				String suggestedPOS = input.getPos()[i] + "_CCP";
				return new Suggestion(SuggestionType.MERGING, tokenSuggestions, false, suggestedPOS, indexOffset + i,
						Constants.EDIT_DISTANCE_RULE_BASED, 0);
			} else if (isN(input.getWords()[i].charAt(input.getWords()[i].length() - 1))
					&& input.getWords()[i + 1].equals("na")) {
				String suggestion = input.getWords()[i] + "g";
				String[] tokenSuggestions = { suggestion };
				String suggestedPOS = input.getPos()[i] + "_CCP";

				return new Suggestion(SuggestionType.MERGING, tokenSuggestions, false, suggestedPOS, indexOffset + i,
						Constants.EDIT_DISTANCE_RULE_BASED, 0);
			}

		}
		return null;
	}

	private Suggestion getMasUnmergingSuggestion(Input input, int indexOffset) throws SQLException {
		for (int i = 0; i < input.getWords().length; i++) {
			if (input.getWords()[i].length() >= 5) {
				String firstThree = input.getWords()[i].substring(0, 3);
				String remaining = input.getWords()[i].substring(3);
				if ((firstThree.equals("mas") || firstThree.equals("Mas")) && wplmDao.wordExists(remaining)) {
					String[] tokenSuggestions = { firstThree, remaining };
					return new Suggestion(SuggestionType.UNMERGING, tokenSuggestions, false, null, indexOffset + i,
							Constants.EDIT_DISTANCE_RULE_BASED, 0);
				}
			}
		}
		return null;
	}

	private List<Suggestion> getAffixMergingSuggestion(Input input, int indexOffset) throws SQLException {
		String[] prefixesEndingVowels = { "pinaka", "paki", "ipa", "magkaka", "pakiki", "magka", "tagapagpa",
				"pagpapaka", "pagka", "pagkaka", "pagsisi", "pagpapa", "pagkakapagka", "pagkakapaki", "mala", "naka",
				"maka", "magka", "nakaka", "magkaka" };
		List<String> prefixesEndingVowelsList = Arrays.asList(prefixesEndingVowels);
		String[] prefixesEndingConsonants = { "pag", "mapag", "ipag", "maipag", "taga", "mag", "pakikipag", "tagapag",
				"pagkakapag", "mapag", "nag" };
		List<String> prefixesEndingConsonantsList = Arrays.asList(prefixesEndingConsonants);

		List<Suggestion> suggs = null;
		for (int i = 0; i < input.getWords().length - 1; i++) {
			if (prefixesEndingVowelsList.contains(input.getWords()[i].toLowerCase())) {
				String sugg = input.getWords()[i] + input.getWords()[i + 1];
				String[] tokenSuggestions = { sugg };
				if (suggs == null) {
					suggs = new ArrayList<>();
				}
				suggs.add(new Suggestion(SuggestionType.MERGING, tokenSuggestions, false, null, indexOffset + i,
						Constants.EDIT_DISTANCE_RULE_BASED, 0));
			} else if (prefixesEndingConsonantsList.contains(input.getWords()[i].toLowerCase())) {

				String sugg;
				if (isVowel(input.getWords()[i + 1].charAt(0)))
					sugg = input.getWords()[i] + "-" + input.getWords()[i + 1];
				else
					sugg = input.getWords()[i] + input.getWords()[i + 1];
				String[] tokenSuggestions = { sugg };
				if (suggs == null) {
					suggs = new ArrayList<>();
				}
				suggs.add(new Suggestion(SuggestionType.MERGING, tokenSuggestions, false, null, indexOffset + i,
						Constants.EDIT_DISTANCE_RULE_BASED, 0));
			}

		}
		return suggs;
	}

	private Suggestion getRemovedHyphenSuggestion(Input input, int indexOffset) throws SQLException {
		for (int i = 0; i < input.getWords().length; i++) {
			if (input.getWords()[i].indexOf('-') != -1) {
				String removedHyphen = input.getWords()[i].replace("-", "");
				if (wplmDao.wordExists(removedHyphen)) {
					String[] tokenSuggestions = { removedHyphen };
					return new Suggestion(SuggestionType.SUBSTITUTION, tokenSuggestions, false, null, indexOffset + i,
							Constants.EDIT_DISTANCE_RULE_BASED, 0);
				}
				String[] split = input.getWords()[i].split("-");
				if (wplmDao.wordExists(split[0]) && wplmDao.wordExists(split[1])) {
					String[] tokenSuggestions = { split[0], split[1] };
					return new Suggestion(SuggestionType.UNMERGING, tokenSuggestions, false, null, indexOffset + i,
							Constants.EDIT_DISTANCE_RULE_BASED, 0);
				}
			}
		}
		return null;
	}

	public static boolean isVowel(char c) {
		return "AEIOUaeiou".indexOf(c) != -1;
	}

	public static boolean isN(char c) {
		return "Nn".indexOf(c) != -1;
	}
}
