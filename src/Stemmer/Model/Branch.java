package Stemmer.Model;

import Stemmer.Model.AffixModules.AffixCommand;
import Stemmer.Model.AffixModules.Infix.InfixCommand;
import Stemmer.Model.AffixModules.Prefix.PrefixCommand;
import Stemmer.Model.AffixModules.Suffix.SuffixCommand;

import static Utility.print.println;

/**
 * Created by laurenz on 22/02/2017.
 */
public class Branch implements Cloneable
{
	/*
	  * ********************************************************************
	  *                               Variables
	  * ********************************************************************
	 */
		/* Important properties */
	private Stem stem;
	/* Parent Branch String */
	private String parentString;
	/* Children Branches */
	private Branch prefixBranch, infixBranch, suffixBranch;
	/* directions */
	private final static char _s = 's', _i = 'i', _p = 'p', _c = 'R';
	private String directionHistory = "";
	private char direction;
	/* Tree properties */
	private boolean isTop = false, isRootWord = false, isTreeRoot = false, isTreeLeaf = false;
	private boolean isPrefixRoot = false, isInfixRoot = false, isSuffixRoot = false;
	private int treeDepth, nullCount = 0;
	private boolean canGenerate = true;
	/* Stoppers */
	int stopper = 0;

	/*
	  * ********************************************************************
	  *                             Constructors
	  * ********************************************************************
	 */

	/**
	 * Use this for root of the tree only (unstemmed)
	 * @param untouchedStem The original input lemma by the user
	 */
	public Branch(Stem untouchedStem) {
		this.stem = untouchedStem;
		this.direction = _c;
		this.directionHistory = directionHistory + _c + "-";
	}

	/**
	 * AVOID USING THIS ONE
	 * @param direction Must contain either 'p', 'i', 's'
	 * @param stem      Must be passed every time.
	 */
	public Branch(char direction, Stem stem) {
		this.direction = direction;
		this.stem = stem;
		this.directionHistory = directionHistory + direction + "-";
	}

	/**
	 * Use only when creating a non-root branch
	 * @param direction
	 * Direction of this branch (prefix, infix, suffix)
	 * @param stem
	 * latest modified stem
	 * @param directionHistory
	 * The latest modified path that this branch has gone through to get here
	 */
	public Branch(char direction, Stem stem, String directionHistory) {
		this.direction = direction;
		this.stem = stem;
		this.directionHistory = directionHistory + direction + "-";
	}

	public void generateBranchChildren() {
		generateBranchChildren(this.stem);
	}

	public void generateBranchChildren(Stem currentStem)
	{
		/* Stem p = currentStem is mutable. Any changes to p will reflect on currentStem also (pass-by-reference) */
		Stem p = new Stem(currentStem.getStemString());
		Stem i = new Stem(currentStem.getStemString());
		Stem s = new Stem(currentStem.getStemString());

		generatePrefixBranch(p);
		generateInfixBranch(i);
		generateSuffixBranch(s);
	}

	/*
	  * ********************************************************************
	  *                      Branch Children Generators
	  * ********************************************************************
	 */


	public Branch[] generateBranchChildren2()
	{
		return generateBranchChildren2( this.stem );
	}

	/**
	 * Generatest the PIS branches for the current stem and returns it in an array of branches (Branch[])
	 * with a size of 3.
	 * @param currentStem
	 * @return
	 */
	public Branch[] generateBranchChildren2(Stem currentStem)
	{
		Branch[] children = new Branch[3];
		Branch prefixBranch, infixBranch, suffixBranch;
		Stem tempStem = currentStem.cloneThis();
		this.prefixBranch = createBranch( tempStem, _p);
		this.infixBranch  = createBranch( tempStem, _i);
		this.suffixBranch = createBranch( tempStem, _s);
		/* set parent stem (string) for every branch */
		this.parentString = currentStem.getStemString();
		this.prefixBranch.setParentString( this.parentString );
		this.infixBranch.setParentString( this.parentString );
		this.suffixBranch.setParentString( this.parentString );
		/* place in children array */
		children[0]  = this.prefixBranch;
		children[1]  = this.infixBranch;
		children[2]  = this.suffixBranch;
		/* return children */
		return children;
	}

	public Branch createBranch( Stem parentStem, char direction )
	{
		/* Mandatory cloning :( */
		Stem newStem = parentStem.cloneThis();
		/* Call commands */
		PrefixCommand pc; InfixCommand ic; SuffixCommand sc;
		/* Branch to be returned */
		Branch newBranch;

		switch( direction )
		{
			case _p:
				pc 			= new PrefixCommand();
				newStem 	= pc.performStemmingModules( newStem );
				checkIfStemLengthAtSmallest( parentStem );
				newBranch 	= new Branch( _p, newStem, this.directionHistory);
				break;
			case _i:
				ic 			= new InfixCommand();
				newStem 	= ic.performStemmingModules( newStem );
				checkIfStemLengthAtSmallest( parentStem );
				newBranch 	= new Branch( _i, newStem, this.directionHistory);
				break;
			case _s:
				sc 			= new SuffixCommand();
				newStem 	= sc.performStemmingModules( newStem );
				newBranch 	= new Branch( _s, newStem, this.directionHistory);
				break;
			default:
				return null;
		}

		return newBranch;
	}

	public Branch nullBranch(Branch oldBranch)
	{
		Branch newBranch;
		Stem nullStem = new Stem("NULL");

		newBranch = oldBranch.cloneThis();
		newBranch.setStem( nullStem );

		return newBranch;
	}

//			parentStem = pc.performStemmingModules();
//			println("pb: " + parentStem.getStemString());
	public Branch generatePrefixBranch(Stem parentStem)
	{
		PrefixCommand pc = new PrefixCommand();
		parentStem = pc.performStemmingModules(parentStem);
			/* check if stem is already root lemma */

//			if( pc.checkDB() )
//			{
//				isPrefixRoot = true;
//				parentStem.setRootWord( parentStem.getStemString() );
//				parentStem.setPathTaken( directionHistory );
//				stopper++;
//			}
		/* check if stem is too small for more stemming */
		checkIfStemLengthAtSmallest( parentStem );
			/* set this branch's p branch */
		setPrefixBranch( new Branch(_p, parentStem, this.directionHistory) );
		return prefixBranch;
	}

	public Branch generateInfixBranch(Stem parentStem)
	{
		InfixCommand ic = new InfixCommand();
		parentStem = ic.performStemmingModules(parentStem);
		/* check if stem is already root lemma */
//			if( ic.checkDB() )
//			{
//				isInfixRoot = true;
//				parentStem.setRootWord( parentStem.getStemString() );
//				parentStem.setPathTaken( directionHistory );
//				stopper++;
//			}
		/* check if stem is too small for more stemming */
		checkIfStemLengthAtSmallest( parentStem );
		/* set this branch's i branch */
		setInfixBranch( new Branch(_i, parentStem, this.directionHistory) );
		return infixBranch;
	}

	public Branch generateSuffixBranch(Stem parentStem)
	{
		SuffixCommand sc = new SuffixCommand();
		parentStem = sc.performStemmingModules(parentStem);
			/* check if stem is already root lemma */
//			if( sc.checkDB() )
//			{
//				isSuffixRoot = true;
//				parentStem.setRootWord( parentStem.getStemString() );
//				parentStem.setPathTaken( directionHistory );
//				stopper++;
//			}
			/* check if stem is too small for more stemming */
		checkIfStemLengthAtSmallest( parentStem );
			/* set this branch's s branch */
		setSuffixBranch( new Branch(_s, parentStem, this.directionHistory) );
		return suffixBranch;
	}



	/**
	 * Gets children in a Branch[] format.
	 * index 0 for prefix, 1 for infix, and 2 for suffix.
	 * @return
	 * children of the selected branch
	 */
	public Branch[] getChildren()
	{
		Branch[] children = new Branch[3];
		children[0] = getPrefixBranch();
		children[1] = getInfixBranch();
		children[2] = getSuffixBranch();
		return children;
	}

	/*
	 * ********************************************************************
	 *                             Other Utility 							*
	 * ********************************************************************
	 */

	/**
	 * Checks DB if current stem is a root lemma
	 * @param checkStem
	 * stem to be checked if root lemma
	 * @return
	 * true if root lemma otherwise false
	 */
	public boolean checkIfRoot(Stem checkStem)
	{
		DBHandler db = new DBHandler();
		String word  = checkStem.getStemString();
		return db.lookup(word);
	}

	/**
	 * Check if the length of the current stem is suitable for further stemming.
	 * @param checkStem
	 * stem to be checked
	 * @return
	 * true if stem is <= 4 and false otherwise.
	 * @Note Stemming should end when this is true.
	 */
	public boolean checkIfStemLengthAtSmallest(Stem checkStem)
	{
		String word = checkStem.getStemString();
		if ( word.length() <= 4 )
		{
			isTreeLeaf = true;
			stopper++;
			return true;
		}
		return false;
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                       FOR CLONING ONLY                        *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public Branch cloneThis()
	{
		try
		{
			return (Branch) Branch.super.clone();
		} catch (CloneNotSupportedException e)
		{
			println(" ERROR: BRANCH CLONING FAILED HUHUBELLS ");
			e.printStackTrace();
		}
		return this;
	}

	/*
	  * ********************************************************************
	  *              Getters and Setters only beyond this point
	  * ********************************************************************
	 */
	public String getDirectionHistory() {
		return directionHistory;
	}

	public void setDirectionHistory(String directionHistory) {
		this.directionHistory = directionHistory;
	}

	public void printBranchStem()
	{
		println( "B-Stem: " + this.stem.getStemString() );
	}

	public Branch getPrefixBranch() {
		return prefixBranch;
	}

	public void setPrefixBranch(Branch prefixBranch) {
		this.prefixBranch = prefixBranch;
	}

	public Branch getInfixBranch() {
		return infixBranch;
	}

	public void setInfixBranch(Branch infixBranch) {
		this.infixBranch = infixBranch;
	}

	public Branch getSuffixBranch() {
		return suffixBranch;
	}

	public void setSuffixBranch(Branch suffixBranch) {
		this.suffixBranch = suffixBranch;
	}

	public char getDirection() {
		return direction;
	}

	public void setDirection(char direction) {
		this.direction = direction;
	}

	public Stem getStem() {
		return stem;
	}

	public void setStem(Stem stem) {
		this.stem = stem;
	}

	public int getTreeDepth() {
		return treeDepth;
	}

	public void setTreeDepth(int treeDepth) {
		this.treeDepth = treeDepth;
	}

	public boolean isTop() {
		return isTop;
	}

	public void setTop(boolean top) {
		isTop = top;
	}

	public boolean isRoot() {
		return this.isRootWord;
	}

	public void setRoot(boolean root) {
		this.isRootWord = root;
	}

	public boolean isRootWord() {
		return isRootWord;
	}

	public void setRootWord(boolean rootWord) {
		isRootWord = rootWord;
	}

	public boolean isTreeRoot() {
		return isTreeRoot;
	}

	public void setTreeRoot(boolean treeRoot) {
		isTreeRoot = treeRoot;
	}

	public boolean isTreeLeaf() {
		return isTreeLeaf;
	}

	public void setTreeLeaf(boolean treeLeaf) {
		isTreeLeaf = treeLeaf;
	}

	public boolean isPrefixRoot() {
		return isPrefixRoot;
	}

	public void setPrefixRoot(boolean prefixRoot) {
		isPrefixRoot = prefixRoot;
	}

	public boolean isInfixRoot() {
		return isInfixRoot;
	}

	public void setInfixRoot(boolean infixRoot) {
		isInfixRoot = infixRoot;
	}

	public boolean isSuffixRoot() {
		return isSuffixRoot;
	}

	public void setSuffixRoot(boolean suffixRoot) {
		isSuffixRoot = suffixRoot;
	}

	public int getNullCount() {
		return nullCount;
	}

	public void setNullCount(int nullCount) {
		this.nullCount = nullCount;
	}

	public boolean isCanGenerate() {
		return canGenerate;
	}

	public void setCanGenerate(boolean canGenerate) {
		this.canGenerate = canGenerate;
	}

	public int getStopper() {
		return stopper;
	}

	public void setStopper(int stopper) {
		this.stopper = stopper;
	}

	public String getParentString() {
		return parentString;
	}

	public void setParentString(String parentString) {
		this.parentString = parentString;
	}

	/*
	  * ********************************************************************
	  *              You're a dick if you write anything else
	  *                        beyond this point
	  * ********************************************************************
	 */
}
