package spellchecker.training;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 
 * Control reading and writing of resource
 * 
 **/
public class IOFile {

	/**
	 * @return String - resource path in the system.
	 */
	public String getResource() {
		return (System.getProperty("user.dir") + "\\resource\\");
	}

	/**
	 * @return whether the resource file exists in the system
	 */
	public boolean isFileExists(String fileName) {
		File f = new File(fileName);
		return f.exists();
	}

	/**
	 * @param fileName
	 *            - Name of the file.
	 * @param wordList
	 *            - list of words that will be stored.
	 * 
	 */
	public void writeResource(String fileName, Set<String> wordList) {
		String filePath = getResource() + fileName;

		try (PrintWriter out = new PrintWriter(new File(filePath))) {
			for (String word : wordList)
				out.println(word);
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		}

	}

	/**
	 * @param fileName
	 *            - Name of the file.
	 * @return - list of words read from the file.
	 * 
	 */
	public LinkedHashSet<String> readResource(String fileName) {
		String filePath = getResource() + fileName;

		LinkedHashSet<String> wordList = new LinkedHashSet<>();
		String currentLine = "";
		try (BufferedReader read = new BufferedReader(new FileReader(filePath))) {
			while ((currentLine = read.readLine()) != null) {
				wordList.add(currentLine);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return wordList;
	}
}