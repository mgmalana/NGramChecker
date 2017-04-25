/**
 * Created by laurenztolentino on 07/19/2016.
 */
package morphinas.Morphinas.Testing;

import morphinas.DataStructures.Sentence;
import morphinas.DataStructures.Word;
import morphinas.Morphinas.Comparator;
import morphinas.Morphinas.IOHandler;

import java.util.ArrayList;
import java.util.HashSet;

public class ComparatorTest
{
	private final static String FEATURES = "features";
	private final static String LEMMA 	 = "lemma";

	/* IOHandler */
	IOHandler ioh = new IOHandler();
	Comparator comp = new Comparator();
	/* usable variables */
	ArrayList<Sentence> test, gold, orig;
	/* resulting accuracy */
	double result = 0.0;

	public void oldMain()
	{
		/* Test input from morphinas-result.txt and compareWith.txt */
		/* PASTE BELOW */
		String from = ":FS~ka+han #sa #mga ~ma #na #bansa #, #simula +ng *20 #siglo #, ~ma+ng #mga +ng #may :F*down *syndrome #ang ~na #sa #mga #institusyon #o +ng #hindi ~ka #sa *lipunan #. \n" +
				":FS*magkaroon #ang #lugar #ng +ng ~ma #na #“ #rock *scene #” #sa +ng *1960’s #at *1970’s ~pa #ng :F*grand :F*ballroom #kasama #ng #mga +ng :F*alice :F*cooper #, :F*ted :F*nugent #, :F*mitch :F*ryder #, :F*rare :F*earth #, :F*brownsville :F*station #, :F*glenn :F*frey #, #at :F*bob :F*seger #. \n" +
				":FS+an #si :F*grant #ng +ng +ng +ng #si :F*virginia :F*cherrill +ng *ika26 #ng :F*marso #, *1955 #dahil #sa #mga #paratang #na *binuntal #siya #ni :F*grant #. \n" +
				":FS#nasa :F*refuge #ang #mga #pulo #, *coastal *wetlands #, *marshes #, #at #lupa #sa #tabi #ng #tubig #ng :F*detroit :F*river #at :F*western :F*lake :F*erie #. \n" +
				":FS~in #ni :F*copeland #ang :F*onggo #sa ~n+ng :F*protoctista #, ~g +ng ~ma #ang #problema #ngunit ~ki #ang ~ka+ng #espesyal #na *katayuan #. \n" +
				":FS#habang ~pa #, #hindi ~na #ni :F*dion #na ~ma #sa ~ka+an #. \n" +
				":FS#“ :F*sinubukan *kong +in #ang ~n+ng #panunungkulan #, :F*ginoong :F*pangulo #” #. \n" +
				":FS#gayunman #, #ang *topograpiya #at #kundisyon #ng #panahon #ang ~nag #ng *4 #% #lamang #sa #mga ~i #ng +in #. \n" +
				":FS#ang +ng #paraan #ng ~pa #ng ~pag+in #ay ~pa *magkaroon #ng #food ~s #at #ang ~s #ay #ang ~n #sa ~pag #ng ~po #na ~nag #sa ~pag #ng #labor #na *binuo #ng +ng #paraan #ng #produksyon #. \n" +
				":FS#sa +ng #normal #na ~pag #, ~pi+an #ng #mga #opisyal #ng #lungsod #na ~mag #ng #mga +han #at ~ma~pag+an #ng #mga #sundalo #, #dahil #sa #sitwasyon #na #ito ~na+an #ang #mga #tao #na ~mag #ng +han #at ~pag+in #para #sa ~n+ng #sarili #. \n" +
				":FS~ma ~i+ng #gawin #sa ~ma #ng ~pag #ng #imahe #ng *disk #sa +ng +ng #papel #o *card #gamit #ang *binoculars #, *telescope #, #o #isa #pang +ng *cardboard #na #may ~ma #na #butas #, #mas #kilala #ito #sa #tawag #na *pinhole *camera #. \n" +
				":FS#ang #mga +ng ~o #ng *diskretong ~pag #at #ang #segregasyon #ng #mga *allele #ay ~sama+ng ~ki+ng #“ :F*unang ~tun #ni :F*mendel #” #o #“ ~tun #ng #segregasyon #” #. \n" +
				":FS~ipag+an #ni :F*cassirer #na #“ ~tu #sa #sentral #na #konsepto #ng :F*kaliwanagan #at *ginuhit #ang ~ma #na #programa #” #ang :F*treatise :F*on :F*wisdom #ni :F*leibniz #. \n" +
				":FS#ayon #sa ~ka #, ~nag #naman #ang :F*jade :F*phoenix :F*pavilion #at :F*chilin :F*pavilion +ng *1958 #at *1963 #. \n" +
				":FS#halimbawa #, #ang #mga :F*crawford :F*box #ng :F*minute :F*maid :F*park #ay *315 #pulgada #( *96 #metro #) #lang #, #at #may #bakod #na #mas ~ma #kaysa #sa #sikat #na #“ :F*green :F*monster #” #sa :F*fenway :F*park #kung #saan #ito #ay #may #tatak #na ~ma #at #, #tangkad #na +ng #pulgada #. \n" +
				":FS~n ~pag #ang ~i #sa #mga #residente #. \n" +
				":FS~pa #ng *masalawahang ~di #sa :F*smolensk +ng :F*agosto *16 #- *18 #, *nakagawa #rin #siya #ng *depensibong #posisyon #sa :F*borodino #. \n" +
				":FS~i+ng #album #na #ito #ang ~pa #ni :F*dion #gayon #pa #man #, #at *nagpakita #si :F*dion #ng #ugali #na #husto #sa #gulang #sa #mga ~n+ng #\" :F*a :F*new :F*day :F*has :F*come #, #\" #\" #I'm :F*alive #, #\" #at #\" #Goodbye's #( :F*the :F*saddest :F*word #) #, #\" #ang ~ba+ng #naging #resulta #mula #sa ~n+ng +ng ~n #responsibilidad #bilang +ng #ina #, #dahil #, #sa ~n+ng +ng #mga #salita #\" *becoming #a *mother *makes #you #a *grownup #\" #. \n" +
				":FS~ipag #para #sa *subhetibong #kalikasan #ng ~ka+han #, ~i #niya #na #, #\" :F*ang ~pag #sa *panlasa #, #samakatuwid #, #ay #hindi +ng *kognitibong ~pag #, #at #kaya #hindi #ito ~l #, #ngunit #ay #estetika #, #na ~nga #ito #na #hindi ~a+ng #maging #iba #kundi *subhetibo #ang ~nag *pantukoy #. \n" +
				":FS~pa ~ni+ng *makalaya #, *ginamit #pa #rin #ang :F*ingles #, #ang *lenguwahe #ng #administrasyon #na *koloniyal +ng #panahon #na #sakop #sila #ng :F*britain #, #para #sa ~i+ng +ng *opisiyal #at +ng #de *facto #opisyal #kasama #ang :F*swahili #. ";
		/* PASTE BELOW */
		String to	= ":FS~ka+han #sa #mga #ma$u #na #bansa #, #simula +g *20 #siglo #, ~ma+ng #mga +ng #may :F*down *syndrome #ang ~na #sa #mga #institusyon #o +ng #hindi ~ka$tanggap #sa #lipunan #. \n" +
				":FS~mag~ka #ang #lugar #ng +ng ~ma #na #\" #rock *scene #\" #sa +ng *1960's #at *1970's ~pa #ng :F*grand :F*ballroom ~ka #ng #mga +ng :F*alice :F*cooper #, :F*ted :F*nugent #, :F*mitch :F*ryder #, :F*rare :F*earth #, :F*brownsville :F*station #, :F*glenn :F*frey #, #at :F*bob :F*seger #. \n" +
				":FS@in+an #si :F*grant #ng +ng +ng +ng #si :F*virginia :F*cherrill +g *ika26 #ng :F*marso #, *1955 #dahil #sa #mga +ng #na @in #siya #ni :F*grant #. \n" +
				":FS#nasa :F*refuge #ang #mga #pulo #, *coastal *wetlands #, *marshes #, #at #lupa #sa #tabi #ng #tubig #ng :F*detroit :F*river #at :F*western :F*lake :F*erie #. \n" +
				":FS~in@i #ni :F*copeland #ang :F*onggo #sa +ng :F*protoctista #, +ng +ng ~ma #ang #problema #ngunit $ki+nin #ang +ng #espesyal #na ~ka+an #. \n" +
				":FS#habang ~nag$pa #, #hindi +na #ni :F*dion #na ~maka #sa ~ka+an #. \n" +
				":FS#“ :F@in+an +ng +in #ang +ng # #, :F*ginoong :F*pangulo #\" #. \n" +
				":FS#gayunman #, #ang #topograpiya #at #kundisyon #ng #panahon #ang ~nag$ta #ng #4 #% #lamang #sa #mga ~in$a #ng +in #. \n" +
				":FS#ang +ng #paraan #ng ~pag #ng ~pag #ay ~pina+ng ~mag~ka #ng *food *surplus #at #ang *surplus #ay #ang +ng #sa ~pag #ng #populasyon #na ~nag$re #sa ~pag #ng #labor #na #binuo #ng +ng #paraan #ng #produksyon #. \n" +
				":FS#sa +ng #normal #na ~pag #, ~ma$pi #ng #mga #opisyal #ng #lungsod #na ~mag #ng #mga +han #at ~ma~pag$ka+an #ng #mga #sundalo #, #dahil #sa #sitwasyon #na #ito ~na+an #ang #mga #tao #na ~mag #ng +han #at ~pag #para #sa +ng #sarili #. \n" +
				":FS~ma +ng #gawin #sa #pamamagitan #ng ~pag #ng #imahe #ng #disk #sa +ng +ng #papel #o *card #gamit #ang *binoculars #, *telescope #, #o #isa +ng +ng *cardboard #na #may ~ma #na #butas #, #mas #kilala #ito #sa #tawag #na *pinhole *camera #. \n" +
				":FS#ang #mga +ng #obserbasyon #ng +ng ~pag@ma #at #ang #segregasyon #ng #mga *allele #ay $sama ~ki~ni+ng #\" :F*unang $tun #ni :F*mendel #\" #o #\" $tun #ng #segregasyon #\" #. \n" +
				":FS~i~pinag #ni :F*cassirer #na #\" ~tu #sa #sentral #na #konsepto #ng :F*kaliwanagan #at @in #ang ~ma$nilay #na #programa #\" #ang :F*treatise :F*on :F*wisdom #ni :F*leibniz #. \n" +
				":FS#ayon #sa ~pag$ka #, ~nag #naman #ang :F*jade :F*phoenix :F*pavilion #at :F*chi-lin :F*pavilion +g *1958 #at *1963 #. \n" +
				":FS#halimbawa #, #ang #mga :F*crawford :F*box #ng :F*minute :F*maid :F*park #ay *315 #pulgada #( *96 #metro #) #lang #, #at #may #bakod #na #mas ~ma #kaysa #sa #sikat #na #\" :F*green :F*monster #\" #sa :F*fenway :F*park #kung #saan #ito #ay #may #tatak #na #malayo #at #, #tangkad #na +ng #pulgada #. \n" +
				":FS~pan+ang ~pag$ha #ang ~i~pinag #sa #mga #residente #. \n" +
				":FS~pag #ng ~ma+g ~pag$di #sa :F*smolensk +g :F*agosto *16 #- *18 #, ~naka #rin #siya #ng +ng #posisyon #sa :F*borodino #. \n" +
				":FS+ng *album #na #ito #ang ~pinaka #ni :F*dion #gayon #pa #man #, #at ~nag~pa #si :F*dion #ng #ugali #na #husto #sa #gulang #sa #mga +ng #\" :F*a :F*new :F*day :F*has :F*come #, #\" #\" :F*i'm :F*alive #, #\" #at #\" :F*goodbye's #( :F*the :F*saddest :F*word #) #, #\" #ang ~pag$ba+ng #naging #resulta #mula #sa +ng +ng ~na+ang #responsibilidad #bilang +ng #ina #, #dahil #, #sa +ng +ng #mga #salita #\" *becoming #a *mother *makes *you #a *grown-up #\" #. \n" +
				":FS~i~pinag$ta #para #sa +ng ~ka+an #ng ~ka+han #, ~i@in #niya #na #, #\" :F*ang ~pag #sa ~pan #, #samakatuwid #, #ay #hindi +ng +ng ~pag #, #at #kaya #hindi #ito #lohikal #, #ngunit #ay #estetika #, #na ~na$nga+g #ito #na #hindi +ng #maging #iba #kundi +ng #ang ~na$gi ~pan #. \n" +
				":FS~pag~ka +ng ~maka #, @in #pa #rin #ang :F*ingles #, #ang #lenguwahe #ng #administrasyon #na #koloniyal +g #panahon #na #sakop #sila #ng :F*britain #, #para #sa +ng +ng #opisiyal #at @in+ng *de *facto #opisyal ~ka #ang :F*swahili #. ";

		/* variables to be passed as params */
		String[] compareFrom;
		String[] compareTo;

		/* convert String to String[]*/
//		from = from.trim();
		compareFrom = from.split(" ");
//		compareFrom = hpost.split(" "); // from hpost
		compareTo 	= to.split(" ");

		/* instantiate comparator */
		Comparator comp = new Comparator();

		/* start comparing */
		comp.compare(compareTo, compareFrom);
	}

	public void runUniqueComparator()
	{
		/* ArrayLists */
		ArrayList<Sentence> goldStandardSentences = new ArrayList<>();
		ArrayList<Sentence> testeeSentences 	  = new ArrayList<>();
		ArrayList<Word> goldenWords				  = new ArrayList<>();
		ArrayList<Word> testeeWords				  = new ArrayList<>();
		Sentence testSentence, goldSentence;
		String test, gold;

	}

	public void runComparator()
	{
		/*
		* Requirements for running UniqueComparator
		* */
		/* ArrayLists */
		ArrayList<Sentence> goldStandardSentences = new ArrayList<>();
		ArrayList<Sentence> testSentences 	      = new ArrayList<>();
		ArrayList<Word> goldenWords				  = new ArrayList<>();
		ArrayList<Word> testWords				  = new ArrayList<>();
		Sentence testSentence 					  = new Sentence();
		Sentence goldSentence 					  = new Sentence();
		/* Example sentence */
		String test = ":FS~ka+han #sa #mga ~ma #na #bansa #, #simula +ng *20 #siglo #, ~ma+ng #mga +ng #may :F*down *syndrome #ang ~na #sa #mga #institusyon #o +ng #hindi ~ka #sa *lipunan #. ";
		String gold = ":FS~ka+han #sa #mga #ma$u #na #bansa #, #simula +g *20 #siglo #, ~ma+ng #mga +ng #may :F*down *syndrome #ang ~na #sa #mga #institusyon #o +ng #hindi ~ka$tanggap #sa #lipunan #. ";
		/* String to Word()s */
		String[] aTest = test.split(" ");
		String[] aGold = gold.split(" ");
		goldenWords	   = comp.convertToWordsList(aGold);
		testWords      = comp.convertToWordsList(aTest);
		/* Word()s to Sentence() */
		goldSentence.setWords(goldenWords);
		testSentence.setWords(testWords);
		/* Sentence() to Sentence()s*/
		goldStandardSentences.add(goldSentence);
		testSentences.add(testSentence);
		/* Start uniqueComparator*/
		comp.multiComparator(testSentences, goldStandardSentences, LEMMA);
	}

	public void testHashSetCode()
	{
		HashSet<String> testSet = new HashSet<>();

		testSet.add("besh");
		testSet.add("pak");
		testSet.add("ganern");

		println("HashSet Test: " + testSet.contains("pak"));
	}

	public void testLemmaComparator() throws Exception
	{
		/* IOHandler */
		IOHandler ioh = new IOHandler();
		/* usable variables */
		ArrayList<Sentence> test, gold;
		/* resulting accuracy */
		double result = 0.0;

		/* read from file and transfer to local variables */
		gold = ioh.readFromFileToSentence("minitext-morphinas.txt");
		test = ioh.readFromFileToSentence("morphinas-result.txt");

		/* lemma comparator in Comparator() */
		result = comp.multiComparator( test, gold, LEMMA );
		println( "result: " + result );
	}

	public void testFeatureComparator() throws Exception
	{
		/* IOHandler */
		IOHandler ioh = new IOHandler();
		/* usable variables */
		ArrayList<Sentence> test, gold;
		/* resulting accuracy */
		double result = 0.0;

		/* read from file and transfer to local variables */
		test = ioh.readFromFileToSentence("morphTest.pinas");
		gold = ioh.readFromFileToSentence("morphGold.pinas");

		/* lemma comparator in Comparator() */
		result = comp.multiComparator( test, gold, FEATURES, 1 );
		println( "result: " + result );
	}

	public void printUnaligned() throws Exception
	{
		println("Printing all found unaligned lines");
		/* read from file and transfer to local variables */
		this.test = ioh.readFromFileToSentence("morphTest.pinas");
		this.gold = ioh.readFromFileToSentence("morphGold.pinas");

		println( comp.unalignedFinder(this.test, this.gold) );
	}

	public void testComparatorWithHash() throws Exception
	{
		/* read from file and transfer to local variables */
		test = ioh.readFromFileToSentence("stemmerResult.txt");
		gold = ioh.readFromFileToSentence("morphGold.pinas");
		orig = ioh.readFromFileToSentence("morphOriginal.pinas");
		/* run */
		comp.multiComparatorUnique( test, gold, orig, "feature" );
	}

	/*
	 *
	 * MAIN MAIN MAIN MAIN MAIN MAIN MAIN MAIN MAIN MAIN MAIN MAIN MAIN MAIN MAIN MAIN
	 *
	 */

	public static void main(String[] args) throws Exception
	{
		ComparatorTest compTest = new ComparatorTest();
//		compTest.testFeatureComparator();
//		compTest.printUnaligned();
		compTest.testComparatorWithHash();
	}

	/*
	 * UTILITY CODE
	 *
	 */
	public static void println(Object input)
	{
		System.out.println("" + input.toString());
	}
}
