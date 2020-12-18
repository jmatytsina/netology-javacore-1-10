import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    private static List<Employee> employees;

    public List<Employee> parseCSV(String[] columnMapping, String fileName) {
        try (CSVReader csvReader = new CSVReader(new FileReader(fileName))) {
            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping(columnMapping);

            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(csvReader)
                    .withMappingStrategy(strategy)
                    .build();
            return csv.parse();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Employee> parseXML(String fileName) {
        try {
            employees = new ArrayList<>();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(fileName));
            Node root = doc.getDocumentElement();
            NodeList nodeList = root.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node employee = nodeList.item(i);
                if (employee.getNodeType() != Node.TEXT_NODE) {
                    Employee employee1 = new Employee();
                    NodeList employeeProperties = employee.getChildNodes();
                    for (int j = 0; j < employeeProperties.getLength(); j++) {
                        Node property = employeeProperties.item(j);
                        String prop = property.getChildNodes().item(0).getTextContent();
                        switch (property.getNodeName()) {
                            case "id":
                                employee1.setId(Integer.parseInt(prop));
                                break;
                            case "firstName":
                                employee1.setFirstName(prop);
                                break;
                            case "lastName":
                                employee1.setLastName(prop);
                                break;
                            case "country":
                                employee1.setCountry(prop);
                                break;
                            case "age":
                                employee1.setAge(Integer.parseInt(prop));
                                break;
                        }
                    }
                    employees.add(employee1);
                }
            }
            return employees;
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
