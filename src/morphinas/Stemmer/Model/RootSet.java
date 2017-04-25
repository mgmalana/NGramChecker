package morphinas.Stemmer.Model;

import static morphinas.Utility.print.println;

public class RootSet implements Cloneable
	{
		String lemma;
		String features;
		String originalWord;
		int frequency = 0;

		public RootSet(String lemma, String features, String originalWord)
		{
			this.lemma = lemma;
			this.features 	 	= features;
			this.originalWord	= originalWord;
		}

		/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                       FOR CLONING ONLY                        *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

		@Override
		protected Object clone() throws CloneNotSupportedException {
			return super.clone();
		}

		public RootSet cloneThis()
		{
			try
			{
				return (RootSet) RootSet.super.clone();
			} catch (CloneNotSupportedException e)
			{
				println(" ERROR: BRANCH CLONING FAILED HUHUBELLS ");
				e.printStackTrace();
			}
			return this;
		}

		public String getLemma() {
			return lemma;
		}

		public String getFeatures() {
			return features;
		}

		public String getOriginalWord() {
			return originalWord;
		}

		public void addFreq()
		{
			this.frequency++;
		}

		public int getFrequency() {
			return frequency;
		}
	}