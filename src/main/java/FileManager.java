import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {

    public void writeString(String input, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(input);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readString(String fileName) {
        StringBuilder result = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String s;
            while ((s = br.readLine()) != null) {
                result.append(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
