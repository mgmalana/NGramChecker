package revised.datavalidator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import revised.util.FileManager;

public class AlignmentCheckerWithGUI {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		JFrame frame = new JFrame();
		JLabel label = new JLabel();
		frame.add(label);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setSize(800, 800);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		File[] files = filesChoose(frame);
		if (files.length == 3)
			checkEachLines(files, frame);
	}

	public static void checkEachLines(File[] files, JFrame frame) throws FileNotFoundException, IOException {
		List<String> file1 = FileManager.readFile(files[0]);
		List<String> file2 = FileManager.readFile(files[1]);
		List<String> file3 = FileManager.readFile(files[2]);

		if (file1.size() != file2.size() || file2.size() != file3.size()) {
			JOptionPane.showMessageDialog(frame, "Number of lines in files are not equal. Please restart the program");
		}

		File outputFile = new File("output.txt");
		if (outputFile.exists()) {
			outputFile.delete();
			outputFile.createNewFile();
		}
		PrintWriter outFile = new PrintWriter(new FileWriter(outputFile.getName(), true));

		for (int j = 0; j < file1.size(); j++) {
			String[] oneSplit = file1.get(j).split(" ");
			String[] twoSplit = file2.get(j).split(" ");
			String[] threeSplit = file3.get(j).split(" ");

			if (oneSplit.length != twoSplit.length || oneSplit.length != threeSplit.length
					|| twoSplit.length != threeSplit.length) {
				outFile.println(files[0].getName() + "   Line: " + (j + 1) + " | Lengths : " + oneSplit.length + ","
						+ twoSplit.length + "," + threeSplit.length);

				for (String a : oneSplit)
					outFile.printf("%20s", a);
				outFile.println();
				for (String b : twoSplit)
					outFile.printf("%20s", b);
				outFile.println();
				for (String c : threeSplit)
					outFile.printf("%20s", c);
				outFile.println();
			}
		}
		outFile.close();
	}

	private static File[] filesChoose(JFrame frame) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setMultiSelectionEnabled(true);
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			File[] files = fileChooser.getSelectedFiles();
			if (files.length != 3)
				JOptionPane.showMessageDialog(frame, "There should be 3 files chosen.");
			return files;
		} else
			return null;
	}
}
