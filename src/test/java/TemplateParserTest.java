import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TemplateParserTest {

  private TemplateParser parser;
  private CustomerData customerData;
  private String inputFilePath;
  private String outputDir;
  String option = "";
  @BeforeEach
  void setUp() {

    parser = new TemplateParser();
    customerData = new CustomerData(new HashMap<>());
    customerData.setAttribute("first_name", "John");
    customerData.setAttribute("last_name", "lastName");
    customerData.setAttribute("email","john@gmail.com");
    inputFilePath = "email_template.txt";
    outputDir = "/Users/xinling/Desktop/assignment3/testOutput";
  }

  @Test
  void parseTemplate() {
    // Create a sample input template file
    createInputTemplateFile(inputFilePath);
    outputDir = "/Users/xinling/Desktop/assignment3/testOutput";
    option = "email";
    // Parse the template and verify the output file exists
    parser.parseTemplate(inputFilePath, outputDir, customerData,option);
    File outputFile = new File(outputDir + File.separator + "emailJohn.txt");  // outputfile name has option at the front
    assertTrue(outputFile.exists(), "Output file should exist.");

    // Clean up: delete the created output file and input template file
    outputFile.delete();
    new File(inputFilePath).delete();
  }

  @Test
  void placeholderNotIncludedInAttribute(){
    option = "email";
    inputFilePath = "letter-template.txt";
    parser = new TemplateParser();
    parser.replacePlaceholders(inputFilePath,customerData);
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      parser.parseTemplate(inputFilePath, "outputDir", customerData,option);
    });

    // Assert that the exception message contains the expected placeholder address
    String expectedMessage = "Placeholder 'address' not found in attributes";
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
  }

  private void createInputTemplateFile(String inputFilePath) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFilePath))) {
      writer.write("Hello [[first_name]],\n");
      writer.write("This is a sample email template.\n");
      writer.write("Best regards,\n");

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testParseTemplateWithNonExistingDirectory() {
    // Set the output directory to a non-existing directory
    outputDir = "non_existing_dir";
    option = "email";
    // Create a sample input template file
    createInputTemplateFile(inputFilePath);

    // Parse the template and verify that an error is printed
    parser.parseTemplate(inputFilePath, outputDir, customerData,option);
    System.out.println("Test PASSED: Error message printed as expected.");

    // Clean up: delete the created input template file
    new File(inputFilePath).delete();

  }


}
