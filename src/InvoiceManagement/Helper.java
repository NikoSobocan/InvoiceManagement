package InvoiceManagement;
import java.util.*;
import java.io.*;
import com.google.gson.*;

public class Helper {

	public static String read() {



		Gson gson = new Gson();
		String json = "";


		try {
			BufferedReader br = new BufferedReader(new FileReader("JsonFile.json"));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			json = sb.toString();
			br.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}


		return json;
	}

	public static void write(String json) {

		File file = new File("JsonFile.json");


		try {
			file.mkdirs();
			file.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			PrintWriter pw = new PrintWriter(file);
			pw.println(json);
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}


	}

}
