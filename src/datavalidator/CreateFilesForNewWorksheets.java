package datavalidator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CreateFilesForNewWorksheets {

	public static void main(String[] args) throws IOException {
		createFiles();
	}

	@SuppressWarnings("deprecation")
	public static void createFiles() throws IOException {

		String path = "data/joey_uncleaned/tb_FIDLAREW2_";
		String[] filenames = { "240", "241", "242", "243", "244", "245", "246", "247", "248", "249", "250", "251",
				"252", "253", "254", "255", "256", "257", "258", "259", "260", "261", "262", "263", "264", "265", "266",
				"267", "268", "269", "270", "271", "272", "273", "274", "275", "276", "277", "278", "279", "280", "281",
				"282", "283", "284", "285", "286", "287", "288", "289", "290", "291", "292", "293", "294", "295", "296",
				"297", "298", "299", "300", "301", "302", "303", "304", "305", "306", "307", "308", "309", "310", "311",
				"312", "313", "314", "315", "316", "317", "318", "319", "320", "321", "322", "323", "324", "325", "326",
				"327", "328", "329", "330", "331", "332", "333", "334", "335", "336", "337", "338", "339", "340", "341",
				"342", "343", "344", "345", "346", "347", "348", "349", "350", "351", "352", "353", "354", "355", "356",
				"357", "358", "359", "360", "361", "362", "363", "364", "365", "366", "367", "368", "369", "370", "371",
				"372", "373", "374", "375", "376", "377", "378", "379", "380", "381", "382", "383", "384", "385", "386",
				"387", "388", "389", "390", "391", "392", "393", "394", "395", "396", "397", "398", "399", "400", "401",
				"402", "403", "404", "405", "406", "407", "408", "409", "410", "411", "412", "413", "414", "415", "416",
				"417", "418", "419", "420", "421" };
		String[] mappings = { "240", "241", "242", "243", "244", "245", "246", "247", "248", "249", "250", "251", "252",
				"253", "254", "255", "256", "257", "258", "259", "260", "261", "262", "263", "264", "265", "266", "267",
				"268", "269", "270", "271", "272", "273", "274", "275", "276", "277", "278", "279", "280", "281", "282",
				"283", "284", "285", "286", "287", "288", "289", "290", "291", "292", "293", "294", "295", "296", "297",
				"298", "299", "300", "301", "302", "303", "304", "305", "306", "307", "308", "309", "310", "311", "312",
				"313", "314", "315", "316", "317", "318", "319", "320", "321", "322", "323", "324", "325", "326", "327",
				"328", "329", "330", "331", "332", "333", "334", "335", "336", "337", "338", "339", "340", "341", "342",
				"343", "344", "345", "346", "347", "348", "349", "350", "351", "352", "353", "354", "355", "356", "357",
				"358", "359", "360", "361", "362", "363", "364", "365", "366", "367", "368", "369", "370", "371", "372",
				"373", "374", "375", "376", "377", "378", "379", "380", "381", "382", "383", "384", "385", "386", "387",
				"388", "389", "390", "391", "392", "393", "394", "395", "396", "397", "398", "399", "400", "401", "402",
				"403", "404", "405", "406", "407", "408", "409", "410", "411", "412", "413", "414", "415", "416", "417",
				"418", "419", "420", "421" };
		// for (String f : filenames) {
		// File fileWord = new File(path + f + "_words.txt");
		// File fileTag = new File(path + f + "_tags.txt");
		// File fileLemm = new File(path + f + "_lemmas.txt");
		// if (!fileWord.exists())
		// fileWord.createNewFile();
		// if (!fileTag.exists())
		// fileTag.createNewFile();
		// if (!fileLemm.exists())
		// fileLemm.createNewFile();
		// }
		String excelFilePath = "C:\\Users\\Guest\\Documents\\workspace\\NGramChecker\\data\\FIDLAR EW prt 2 Jan 2-6.xlsx";
		FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
		Workbook workbook = new XSSFWorkbook(inputStream);

		Iterator<Sheet> sheetsIterator = workbook.iterator();
		while (sheetsIterator.hasNext()) {
			Sheet sheet = sheetsIterator.next();
			List<String> words = new ArrayList<>();
			List<String> pos = new ArrayList<>();
			List<String> lemmas = new ArrayList<>();

			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
						if (cell.getColumnIndex() == 1)
							words.add(cell.getStringCellValue());
						else if (cell.getColumnIndex() == 2)
							pos.add(cell.getStringCellValue());
						else if (cell.getColumnIndex() == 3)
							lemmas.add(cell.getStringCellValue());
					}
				}
			}

			int index = -1;
			for (int i = 0; i < filenames.length; i++) {
				String filename = filenames[i];
				if (filename.equals(sheet.getSheetName()))
					index = i;
			}
			if (index != -1) {

				FileWriter writer = new FileWriter(path + sheet.getSheetName() + "_words.txt");
				for (String str : words) {
					writer.write(str + "\n");
				}
				writer.close();
				writer = new FileWriter(path + sheet.getSheetName() + "_tags.txt");
				for (String str : pos) {
					writer.write(str + "\n");
				}
				writer.close();
				writer = new FileWriter(path + sheet.getSheetName() + "_lemmas.txt");
				for (String str : lemmas) {
					writer.write(str + "\n");
				}
				writer.close();
			}
			System.out.println(sheet.getSheetName() + " " + words.size() + " " + pos.size() + " " + lemmas.size());
		}

		workbook.close();
		inputStream.close();
	}
}
