import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CSVFileReader {
// CSVFileReader class is aim to read CSV header and data to generate attributes for CustomerData in each line
  public List<CustomerData> readCSVFile(String filePath) {
    List<CustomerData> customers = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      // Read the header line
      String headerLine = br.readLine();
      String[] headers = headerLine.split(",");

      String dataLine;
      while ((dataLine = br.readLine()) != null) {
        // Split data line using regex to handle commas within quotes
        String[] data = dataLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        // create a new customerData
        CustomerData customerData = new CustomerData(new HashMap<>());
        for (int i = 0; i < headers.length; i++) {
          // Remove surrounding quotes if present
          String attributeName = headers[i].replaceAll("^\"|\"$", "");
          String attributeValue = data[i].replaceAll("^\"|\"$", "");
          customerData.setAttribute(attributeName, attributeValue);  //add attributes to customerData
        }
        // add the customerData
        customers.add(customerData);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return customers;
  }

  @Override
  public String toString() {
    return "CSVFileReader{}";
  }
}
