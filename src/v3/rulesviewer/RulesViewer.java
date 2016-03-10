package v3.rulesviewer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import util.ArrayToStringConverter;
import v3.dao.DaoManager;
import v3.dao.NGramDao;
import v3.model.NGram;

public class RulesViewer {

	public static void main(String[] args) throws SQLException, IOException {
		for (int i = 1; i <= 7; i++) {
			NGramDao ngramDao = DaoManager.getNGramDao(i);

			HashMap<Integer, NGram> generalizedNGrams = ngramDao.getGeneralizedNGrams();
			writeToFile(generalizedNGrams, "rules/rules-" + i + ".csv");
			Iterator it = generalizedNGrams.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				NGram n = (NGram) pair.getValue();
				System.out.println(getRule(n) + " | " + ArrayToStringConverter.convert(n.getWords()) + " | "
						+ ArrayToStringConverter.convert(n.getIsPOSGeneralized()));
			}
		}
	}

	private static void writeToFile(HashMap<Integer, NGram> generalizedNGrams, String filename) throws IOException {
		File file = new File(filename);
		if (file.exists())
			file.delete();

		file.createNewFile();

		FileWriter fileWriter = new FileWriter(file, true);
		BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
		Iterator it = generalizedNGrams.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			NGram n = (NGram) pair.getValue();
			bufferWriter.write(getRule(n) + " | " + ArrayToStringConverter.convert(n.getWords()) + " | "
					+ ArrayToStringConverter.convert(n.getIsPOSGeneralized()) + "\n");
		}
		bufferWriter.close();
	}

	private static String getRule(NGram n) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < n.getWords().length; i++) {
			if (n.getIsPOSGeneralized()[i] == true)
				s.append(n.getPos()[i]);
			else
				s.append(n.getWords()[i]);
			s.append(" ");
		}
		return s.toString();
	}
}
