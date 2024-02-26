import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

class CSVFileReaderTest {

  private CSVFileReader csvFileReader;

  @BeforeEach
  void setUp() {
    // Create a new CSVFileReader object for each test
    csvFileReader = new CSVFileReader();
  }

  @Test
  void readCSVFile() {
    // Test reading a CSV file
    String filePath = "insurance-company-members.csv"; // Provide the path to your test CSV file
    List<CustomerData> customers = csvFileReader.readCSVFile(filePath);
    assertEquals(2, customers.size()); // Assuming there are 2 lines of data in the test CSV file

    // Validate the attributes of the first customer data
    CustomerData customer1 = customers.get(0);
    HashMap<String, String> expectedAttributes1 = new HashMap<>();
    expectedAttributes1.put("first_name", "James");
    expectedAttributes1.put("last_name", "Butt");
    assertEquals(expectedAttributes1.get("first_name"), customer1.getAttribute("first_name"));

    // Validate the attributes of the second customer data
    CustomerData customer2 = customers.get(1);
    HashMap<String, String> expectedAttributes2 = new HashMap<>();
    expectedAttributes2.put("first_name", "Josephine");
    expectedAttributes2.put("last_name", "Darakjy");
    assertEquals(expectedAttributes2.get("first_name"), customer2.getAttribute("first_name"));
  }
@Test
void readAllHeaders(){
  String filePath = "insurance-company-members.csv"; // Provide the path to your test CSV file
  List<CustomerData> customers = csvFileReader.readCSVFile(filePath);
  CustomerData customer1 = customers.get(0);
  assertTrue(customer1.getAttributes().containsKey("first_name"));
  assertTrue(customer1.getAttributes().containsKey("last_name"));
  assertTrue(customer1.getAttributes().containsKey("company_name"));
  assertTrue(customer1.getAttributes().containsKey("address"));
}

@Test
void returnFalseFornotincludedAttribute(){
  String filePath = "insurance-company-members.csv"; // Provide the path to your test CSV file
  List<CustomerData> customers = csvFileReader.readCSVFile(filePath);
  CustomerData customer1 = customers.get(0);
  assertFalse(customer1.getAttributes().containsKey("company_address"));
  assertFalse(customer1.getAttributes().containsKey("NumberOfChild"));
}
  @Test
  void testToString() {
    // Test toString method
    assertEquals("CSVFileReader{}", csvFileReader.toString());
  }
}
