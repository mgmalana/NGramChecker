package revised.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {

	static FileWriter fileWriter;
	static BufferedWriter bufferWriter;
	static String filename;

	public FileManager(String filename) {
		this.filename = filename;
	}

	public void createFile() throws IOException {
		File file = new File(filename);
		if (file.exists())
			file.delete();

		file.createNewFile();
		fileWriter = new FileWriter(file);
		bufferWriter = new BufferedWriter(fileWriter);
	}

	public void writeToFile(String line) throws IOException {
		bufferWriter.write(line + "\n");
	}

	public void close() throws IOException {
		bufferWriter.close();
	}
}