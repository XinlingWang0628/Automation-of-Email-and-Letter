import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class TemplateParser {
  private static final String nameOfGeneratedEmail = "first_name";

  public void parseTemplate(String inputFilePath, String outputDir, CustomerData customerData,String option) {
    // this method can be used to read and write email and letter according to the template by replace placeholders
    try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath))) {
      // Use customerData's first name as the output file name
      String outputFileName = option+ customerData.getAttribute(nameOfGeneratedEmail) + ".txt";
      // Construct the path for the output file
      String outputFileWithPath = outputDir + File.separator + outputFileName;

      // Create the output directory if it doesn't exist
      File outputDirFile = new File(outputDir);
      outputDirFile.mkdirs();

      try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFileWithPath))) {
        String line;
        StringBuilder template = new StringBuilder();
        while ((line = br.readLine()) != null) {
          template.append(line).append("\n");
        }
        String modifiedTemplate = replacePlaceholders(template.toString(), customerData);
        bw.write(modifiedTemplate);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String replacePlaceholders(String template, CustomerData customerData) {
    // this method can replace all the placeholder with customerData's attributes
    Pattern pattern = Pattern.compile("\\[\\[(.*?)\\]\\]");
    Matcher matcher = pattern.matcher(template);
    StringBuffer str = new StringBuffer();
    while (matcher.find()) {
      String placeholder = matcher.group(1);
      String value = customerData.getAttribute(placeholder);

      if (value != null) {
        matcher.appendReplacement(str, Matcher.quoteReplacement(value));
      } else {
        // Placeholder not found in attributes, throw an exception
        throw new IllegalArgumentException("Placeholder '" + placeholder + "' not found in attributes");
      }
    }
    matcher.appendTail(str);
    return str.toString();
  }

  @Override
  public String toString() {// tostring method
    return "TemplateParser{}";
  }
}
