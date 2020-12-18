import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        FileManager fileManager = new FileManager();
        Parser parser = new Parser();

        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.csv";
        List<Employee> list = parser.parseCSV(columnMapping, fileName);
        String json = listToJson(list);
        String fileOutputName = "data.json";
        fileManager.writeString(json, fileOutputName);

        List<Employee> listXML = parser.parseXML("data.xml");
        String json2 = listToJson(listXML);
        fileManager.writeString(json2, "data2.json");

        String jsonString = fileManager.readString("data.json");
        List<Employee> listJSON = jsonToList(jsonString);
        listJSON.forEach(System.out::println);
    }

    private static String listToJson(List<Employee> employees) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Type listType = new TypeToken<List<Employee>>() {}.getType();
        return gson.toJson(employees, listType);
    }

    private static List<Employee> jsonToList(String input) {
        List<Employee> employees = new ArrayList<>();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        JSONParser parser = new JSONParser();
        try {
            JSONArray array = (JSONArray) parser.parse(input);
            for (int i = 0; i < array.stream().toArray().length; i++) {
                JSONObject s = (JSONObject) array.get(i);
                Employee employee = gson.fromJson(s.toJSONString(), Employee.class);
                employees.add(employee);
            }
            return employees;
        } catch (ParseException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
