import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

class MainTest {

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;

  @BeforeEach
  void setUp() {
    System.setOut(new PrintStream(outContent));
  }

  @Test
  void main_EmailOption_createdInOutputDir() {// successfully created emails
    String[] args = {"--email", "--email-template", "email-template.txt", "--csv-file", "insurance-company-members.csv", "--output-dir", "/Users/xinling/Desktop/assignment3/testOutput"};
    Main.main(args);
    // Check if email files have been generated
    String outputDirPath = "/Users/xinling/Desktop/assignment3/testOutput";
    File outputDir = new File(outputDirPath);
    File[] emailFiles = outputDir.listFiles((dir, name) -> name.endsWith(".txt"));
    assertNotNull(emailFiles, "Output directory should not be null.");

    // Ensure at least one email file is generated
    assertTrue(emailFiles.length > 0, "At least one email file should be generated.");

  }


  @Test
  void main_LetterOption_Success() {// successfully created letters
    String[] args = {"--letter", "--letter-template", "letter-template.txt", "--csv-file",
        "insurance-company-members.csv", "--output-dir", "/Users/xinling/Desktop/assignment3/testOutput"};
    Main.main(args);
    // Check if email files have been generated
    String outputDirPath = "/Users/xinling/Desktop/assignment3/testOutput";
    File outputDir = new File(outputDirPath);
    File[] emailFiles = outputDir.listFiles((dir, name) -> name.endsWith(".txt"));
    assertNotNull(emailFiles, "Output directory should not be null.");

    // Ensure at least one email file is generated
    assertTrue(emailFiles.length > 0, "At least one email file should be generated.");
  }

@Test
void testDifferentOrderOfArgus(){// try different order of arguments
  String[] args = {"--output-dir", "/Users/xinling/Desktop/assignment3/testOutput","--email-template", "email-template.txt", "--email", "--csv-file", "insurance-company-members.csv" };
  Main.main(args);
  // Check if email files have been generated
  String outputDirPath = "/Users/xinling/Desktop/assignment3/testOutput";
  File outputDir = new File(outputDirPath);
  File[] emailFiles = outputDir.listFiles((dir, name) -> name.endsWith(".txt"));
  assertNotNull(emailFiles, "Output directory should not be null.");

  // Ensure at least one email file is generated
  assertTrue(emailFiles.length > 0, "At least one email file should be generated.");

}

  @Test
  void main_MissingOption_Error() {// missing option
    String[] args = {"--email-template", "email-template.txt", "--csv-file",
        "insurance-company-members.csv", "--output-dir", "emails"};
    Throwable exception = null;
    try {
      Main.main(args);
    } catch (Exception e) {
      exception = e;
    }
    // Assert that the output contains the error message for missing option
    assertEquals("Error: No option specified (use --email or --letter).", exception.getMessage());
  }

  @Test
  void main_MissingRequiredArguments_Error() {// missing templates
    String[] args = {"--email", "--output-dir", "emails"};
    Throwable exception = null;
    try {
      Main.main(args);
    } catch (Exception e) {
      exception = e;
    }
    // Assert that the output contains the error message for missing required arguments
    assertEquals("Error: Missing required arguments.",exception.getMessage());
  }

  @Test
  void generateEmailorletter_EmailOption_Success() {//send all emails
    List<CustomerData> customers = new ArrayList<>();
    // Add dummy customer data to the list
    String template = "email-template.txt";
    String outputDir = "emails";
    String option = "email";
    Main.generateEmailorletter(customers, template, outputDir, option);
    // Assert that the output contains the success message for generating emails
    assertTrue(outContent.toString().contains("[stub] Sending generated email to clients."));
  }

  @Test
  void generateEmailorletter_LetterOption_Success() {// send all letters
    List<CustomerData> customers = new ArrayList<>();
    // Add dummy customer data to the list
    String template = "letter-template.txt";
    String outputDir = "letters";
    String option = "letter";
    Main.generateEmailorletter(customers, template, outputDir, option);
    // Assert that the output contains the success message for generating letters
    assertTrue(outContent.toString().contains("[stub] Sending generated letter to clients."));
  }

  @Test
  void generateEmailorletter_InvalidOption_Error() {// unkown option --message
    String[] args = {"--email-template", "email-template.txt", "--output-dir", "emails", "--csv-file", "customer-data.csv", "--message"};
    Main.main(args);
    assertTrue(outContent.toString().contains("Error: Unknown option: --message"));
  }

  @Test
  void generateEmailorletter_NullCustomers_Error() {// CSV file doesn't exist
    String[] args = {"--email-template", "email-template.txt", "--output-dir", "/Users/xinling/Desktop/assignment3/testOutput", "--csv-file", "non-existent.csv", "--email"};
    Throwable exception = null;
    try {
      Main.main(args);
    } catch (Exception e) {
      exception = e;
    }

    assertNotNull(exception);
    //assertEquals(FileNotFoundException.class, exception.getClass());
    assertEquals("Error: CSV file does not exist.", exception.getMessage());

  }


  @Test
  void generateEmailorletter_NullTemplate_Error() {// missing email template
    String[] args = {"--output-dir", "/Users/xinling/Desktop/assignment3/testOutput", "--csv-file", "customer-data.csv", "--email"};
    Throwable exception = null;
    try {
      Main.main(args);
    } catch (Exception e) {
      exception = e;
    }
    assertEquals("Error: Missing required arguments.",exception.getMessage() );
  }

  @Test
  void generateEmailorletter_NullOutputDir_Error() {// missing output dir
    String[] args = {"--email-template", "email-template.txt", "--csv-file", "customer-data.csv", "--email"};
    Throwable exception = null;
    try {
      Main.main(args);
    } catch (Exception e) {
      exception = e;
    }
    assertEquals("Error: Missing required arguments.",exception.getMessage() );
  }

}