package spellchecker.stemming;

import java.util.LinkedHashMap;

import spellchecker.utility.Configuration;

/**
 * Class that is called to remove prefixes in spellchecker.stemming.
 */
public class Prefixes {

	private final String vowel = "[aeiou].*";
	private final String consonant = "[^aeiou-].*";

	private final String assimilation_ng_to_n = "[dlrst].*";
	private final String assimilation_ng_to_m = "[bp].*";
	LinkedHashMap<String, String> patternReplace = null;

	/**
	 * List of pre-defined prefixes that will be used by the stemmer
	 */
	public Prefixes() {
		/**
		 * key - prefix that will be matched in the inflected word. value -
		 * pattern that will be removed in the inflected word if the key
		 * matched.
		 */
		patternReplace = new LinkedHashMap<String, String>();

		patternReplace.put("^pakikipag-" + vowel, "pakikipag-");
		patternReplace.put("^pakikipag" + consonant, "pakikipag");

		patternReplace.put("^pagpapaka", "pagpapaka");
		patternReplace.put("^nakipagsa", "nakipagsa");
		patternReplace.put("^nakapagpa", "nakapagpa");

		patternReplace.put("^maipapang-" + vowel, "maipapang-");
		patternReplace.put("^maipapan" + assimilation_ng_to_n, "maipapan");
		patternReplace.put("^maipapam" + assimilation_ng_to_m, "maipapam");
		patternReplace.put("^maipapang" + consonant, "maipapang");

		patternReplace.put("^magkasing-" + vowel, "magkasing-");
		patternReplace.put("^magkasin" + assimilation_ng_to_n, "magkasin");
		patternReplace.put("^magkasim" + assimilation_ng_to_m, "magkasim");
		patternReplace.put("^magkasing" + consonant, "magkasing");

		patternReplace.put("^nasipang-" + vowel, "nasipang-");
		patternReplace.put("^nasipan" + assimilation_ng_to_n, "nasipan");
		patternReplace.put("^nasipam" + assimilation_ng_to_m, "nasipam");
		patternReplace.put("^nasipang" + consonant, "nasipang");

		patternReplace.put("^nakapang-" + vowel, "nakapang-");
		patternReplace.put("^nakapan" + assimilation_ng_to_n, "nakapan");
		patternReplace.put("^nakapam" + assimilation_ng_to_m, "nakapam");
		patternReplace.put("^nakapang" + consonant, "nakapang");

		patternReplace.put("^makapang-" + vowel, "makapang-");
		patternReplace.put("^makapan" + assimilation_ng_to_n, "makapan");
		patternReplace.put("^makapam" + assimilation_ng_to_m, "makapam");
		patternReplace.put("^makapang" + consonant, "makapang");

		patternReplace.put("^maipagpa.*", "maipagpa");

		patternReplace.put("^magsipag-" + vowel, "magsipag-");
		patternReplace.put("^magsipag" + consonant, "magsipag");

		patternReplace.put("^magkanda", "magkanda");
		patternReplace.put("^ikapagpa", "ikapagpa");

		patternReplace.put("^tagapag-" + vowel, "tagapag-");
		patternReplace.put("^tagapag" + consonant, "tagapag");

		patternReplace.put("^pagkaka.*", "pagkaka");
		patternReplace.put("^pagpapa.*", "pagpapa");
		patternReplace.put("^papagka.*", "papagka");

		patternReplace.put("^nakipag-" + vowel, "nakipag-");
		patternReplace.put("^nakipag" + consonant, "nakipag");

		patternReplace.put("^nakapag-" + vowel, "nakapag-");
		patternReplace.put("^nakapag" + consonant, "nakapag");

		patternReplace.put("^nagpaka.*", "nagpaka");

		patternReplace.put("^mapapag-" + vowel, "mapapag-");
		patternReplace.put("^mapapag" + consonant, "mapapag");

		patternReplace.put("^mangaka.*", "mangaka");
		patternReplace.put("^mapagpa.*", "mapagpa");

		patternReplace.put("^pinapapag-" + vowel, "pinapapag-");
		patternReplace.put("^pinapapag" + consonant, "pinapapag");

		patternReplace.put("^pinapag-" + vowel, "pinapag-");
		patternReplace.put("^pinapag" + consonant, "pinapag");

		patternReplace.put("^makikipag-" + vowel, "makikipag-");
		patternReplace.put("^makikipag" + consonant, "makikipag");

		patternReplace.put("^makipag-" + vowel, "makipag-");
		patternReplace.put("^makipag" + consonant, "makipag");

		patternReplace.put("^maka(ka)?+pag-" + vowel, "maka(ka)?+pag-");
		patternReplace.put("^maka(ka)?+pag" + consonant, "maka(ka)?+pag");

		patternReplace.put("^maipang-" + vowel, "maipang-");
		patternReplace.put("^maipan" + assimilation_ng_to_n, "maipan");
		patternReplace.put("^maipam" + assimilation_ng_to_m, "maipam");
		patternReplace.put("^maipang" + consonant, "maipang");

		patternReplace.put("^magpaka.*", "magpaka");

		patternReplace.put("^magkang-" + vowel, "magkang-");
		patternReplace.put("^magkan" + assimilation_ng_to_n, "magkan");
		patternReplace.put("^magkam" + assimilation_ng_to_m, "magkam");
		patternReplace.put("^magkang" + consonant, "magkang");

		patternReplace.put("^ipapang-" + vowel, "ipapang-");
		patternReplace.put("^ipapan" + assimilation_ng_to_n, "ipapan");
		patternReplace.put("^ipapam" + assimilation_ng_to_m, "ipapam");
		patternReplace.put("^ipapang" + consonant, "ipapang");

		patternReplace.put("^ikapang-" + vowel, "ikapang-");
		patternReplace.put("^ikapan" + assimilation_ng_to_n, "ikapan");
		patternReplace.put("^ikapam" + assimilation_ng_to_m, "ikapam");
		patternReplace.put("^ikapang" + consonant, "ikapang");

		patternReplace.put("^papang-" + vowel, "papang-");
		patternReplace.put("^papan" + assimilation_ng_to_n, "papan");
		patternReplace.put("^papam" + assimilation_ng_to_m, "papam");
		patternReplace.put("^papang" + consonant, "papang");

		patternReplace.put("^nakaka.*", "nakaka");
		patternReplace.put("^napaka.*", "napaka");
		patternReplace.put("^pakiki.*", "pakiki");

		patternReplace.put("^naipag-" + vowel, "naipag-");
		patternReplace.put("^naipag" + consonant, "naipag");

		patternReplace.put("^naging-" + vowel, "naging-");
		patternReplace.put("^nagin" + assimilation_ng_to_n, "nagin");
		patternReplace.put("^nagim" + assimilation_ng_to_m, "nagim");
		patternReplace.put("^naging" + consonant, "naging");

		patternReplace.put("^mapang-" + vowel, "mapang-");
		patternReplace.put("^mapan" + assimilation_ng_to_n, "mapan");
		patternReplace.put("^mapam" + assimilation_ng_to_m, "mapam");
		patternReplace.put("^mapang" + consonant, "mapang");

		patternReplace.put("^kasing-" + vowel, "kasing-");
		patternReplace.put("^kasin" + assimilation_ng_to_n, "kasin");
		patternReplace.put("^kasim" + assimilation_ng_to_m, "kasim");
		patternReplace.put("^kasing" + consonant, "kasing");

		patternReplace.put("^maging-" + vowel, "maging-");
		patternReplace.put("^magin" + assimilation_ng_to_n, "magin");
		patternReplace.put("^magim" + assimilation_ng_to_m, "magim");
		patternReplace.put("^maging" + consonant, "maging");

		patternReplace.put("^maipag-" + vowel, "maipag-");
		patternReplace.put("^maipag" + consonant, "maipag");

		patternReplace.put("^mangag-" + vowel, "mangag-");
		patternReplace.put("^mangag" + consonant, "mangag");

		patternReplace.put("^ipagka.*", "ipagka");

		patternReplace.put("^ikapag-" + vowel, "ikapag-");
		patternReplace.put("^ikapag" + consonant, "ikapag");

		patternReplace.put("^papag-" + vowel, "papag-");
		patternReplace.put("^papag" + consonant, "papag");

		patternReplace.put("^nagpa.*", "nagpa");
		patternReplace.put("^nagsa.*", "nagsa");
		patternReplace.put("^naipa.*", "naipa");
		patternReplace.put("^pagka.*", "pagka");
		patternReplace.put("^pagpa.*", "pagpa");

		patternReplace.put("^mapag-" + vowel, "mapag-");
		patternReplace.put("^mapag" + consonant, "mapag");

		patternReplace.put("^pinag-" + vowel, "pinag-");
		patternReplace.put("^pinag" + consonant, "pinag");

		patternReplace.put("^magka.*", "magka");
		patternReplace.put("^magpa.*", "magpa");
		patternReplace.put("^magsa.*", "magsa");
		patternReplace.put("^magsi.*", "magsi");
		patternReplace.put("^maipa.*", "maipa");
		patternReplace.put("^manga.*", "manga");

		patternReplace.put("^ipang-" + vowel, "ipang-");
		patternReplace.put("^ipan" + assimilation_ng_to_n, "ipan");
		patternReplace.put("^ipam" + assimilation_ng_to_m, "ipam");
		patternReplace.put("^ipang" + consonant, "ipang");

		patternReplace.put("^ikina.*", "ikina");
		patternReplace.put("^ipaki.*", "ipaki");
		patternReplace.put("^taga-.*", "taga-");
		patternReplace.put("^tala.*", "tala");

		patternReplace.put("^sang-" + vowel, "sang-");
		patternReplace.put("^san" + assimilation_ng_to_n, "san");
		patternReplace.put("^sam" + assimilation_ng_to_m, "sam");
		patternReplace.put("^sang" + consonant, "sang");

		patternReplace.put("^sing-" + vowel, "sing-");
		patternReplace.put("^sin" + assimilation_ng_to_n, "sin");
		patternReplace.put("^sim" + assimilation_ng_to_m, "sim");
		patternReplace.put("^sing" + consonant, "sing");

		patternReplace.put("^pina.*", "pina");
		patternReplace.put("^puma.*", "puma");

		patternReplace.put("^pang-" + vowel, "pang-");
		patternReplace.put("^pan" + assimilation_ng_to_n, "pan");
		patternReplace.put("^pam" + assimilation_ng_to_m, "pam");
		patternReplace.put("^pang" + consonant, "pang");

		patternReplace.put("^napa.*", "napa");
		patternReplace.put("^paka.*", "paka");
		patternReplace.put("^paki.*", "paki");
		patternReplace.put("^pala.*", "pala");
		patternReplace.put("^papa.*", "papa");

		patternReplace.put("^nang-" + vowel, "nang-");
		patternReplace.put("^nan" + assimilation_ng_to_n, "nan");
		patternReplace.put("^nam" + assimilation_ng_to_m, "nam");
		patternReplace.put("^nang" + consonant, "nang");

		patternReplace.put("^mapa.*", "mapa");
		patternReplace.put("^naka.*", "naka");
		patternReplace.put("^naki.*", "naki");

		patternReplace.put("^mang-" + vowel, "mang-");
		patternReplace.put("^man" + assimilation_ng_to_n, "man");
		patternReplace.put("^mam" + assimilation_ng_to_m, "mam");
		patternReplace.put("^mang" + consonant, "mang");

		patternReplace.put("^makaka.*", "makaka");
		patternReplace.put("^maka.*", "maka");

		patternReplace.put("^makiki.*", "makiki");
		patternReplace.put("^maki.*", "maki");

		patternReplace.put("^mala.*", "mala");

		patternReplace.put("^ipag-" + vowel, "ipag-");
		patternReplace.put("^ipag" + consonant, "ipag");

		patternReplace.put("^tig-" + vowel, "tig-");
		patternReplace.put("^tig" + consonant, "tig");

		patternReplace.put("^tag-" + vowel, "tag-");
		patternReplace.put("^tag" + consonant, "tag");

		patternReplace.put("^pag-" + vowel, "pag-");
		patternReplace.put("^pag" + consonant, "pag");

		patternReplace.put("^nai.*", "nai");

		patternReplace.put("^may-" + vowel, "may-");
		patternReplace.put("^may" + consonant, "may");

		patternReplace.put("^nag-" + vowel, "nag-");
		patternReplace.put("^nag" + consonant, "nag");

		patternReplace.put("^mai.*", "mai");

		patternReplace.put("^mag-" + vowel, "mag-");
		patternReplace.put("^mag" + consonant, "mag");

		patternReplace.put("^ika.*", "ika");
		patternReplace.put("^ipa.*", "ipa");
		patternReplace.put("^isa.*", "isa");

		patternReplace.put("^di-.*", "di-");

		// Configuration.LIGHT_STEMMER == FALSE
		if (!Configuration.LIGHT_STEMMER) {
			patternReplace.put("^ma.*", "ma");
			patternReplace.put("^na.*", "na");
			patternReplace.put("^pa.*", "pa");
			patternReplace.put("^um.*", "um");
		}
	}

	/**
	 * Return the hashmap that contains the pre-defined prefixes
	 */
	public LinkedHashMap<String, String> getPrefixes() {
		return patternReplace;
	}
}
