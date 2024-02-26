import java.io.File;
import java.util.List;

public class Main {
    // main class is to parse command line arguments and generate either letters or emails by using helper functions
    public static void main(String[] args) {
        // main method can detect errors from command line inputs
        if (args.length == 0) {
            throw new IllegalArgumentException("Error: No arguments provided.");
        }
        String option = null;
        String emailTemplate = null;
        String letterTemplate = null;
        String outputDir = null;
        String csvFile = null;


        for (int i = 0; i < args.length; i++) {// looking for option in arguments
            switch (args[i]) {
                case "--email":
                    option = "email";
                    break;
                case "--letter":
                    option = "letter";
                    break;
                case "--email-template":
                    emailTemplate = args[++i];
                    break;
                case "--letter-template":
                    letterTemplate = args[++i];
                    break;
                case "--output-dir":
                    outputDir = args[++i];
                    break;
                case "--csv-file":
                    csvFile = args[++i];
                    break;
                default:
                    System.out.println("Error: Unknown option: " + args[i]);
                    return;
            }
        }

        if (option == null) { // when option is null, print error
            throw new IllegalArgumentException("Error: No option specified (use --email or --letter).");
        }

        if ((option.equals("email") || option.equals("letter")) && // no template or output or csvFile, can't generate email or letter
            ((emailTemplate == null && letterTemplate == null) || outputDir == null || csvFile == null)) {
            throw new IllegalArgumentException("Error: Missing required arguments.");

        }
        if (option == null || (emailTemplate == null && letterTemplate == null) || outputDir == null || csvFile == null) {
            throw new IllegalArgumentException("Error: Missing required arguments."); // another error about missing argumemts in command line

        }
        File file = new File(csvFile);
        if (!file.exists()) {
            throw new IllegalArgumentException("Error: CSV file does not exist.");
        }
        CSVFileReader csvFileReader = new CSVFileReader();
        List<CustomerData> customers = csvFileReader.readCSVFile(csvFile); // read CSV file and save all customerDatas
        if (customers.size() == 0){
            throw new IllegalArgumentException("Error: CSV file cannot be empty");
        }

        if (option.equals("email")) { // generate emails
          generateEmailorletter(customers,emailTemplate,outputDir,option);

        } else if (option.equals("letter")) {// generate letters
            generateEmailorletter(customers, letterTemplate,outputDir,option);
        }
    }

    public static void generateEmailorletter(List<CustomerData> customers, String template,String outputDir, String option){
        // build a helper function to avoid repeated code
        TemplateParser emailGenerator = new TemplateParser();
        for(CustomerData customerData: customers){

            emailGenerator.parseTemplate(template, outputDir, customerData, option);
        }
        System.out.println("[stub] Sending generated " + option +" to clients.");
    }


}
