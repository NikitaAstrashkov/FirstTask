package here.you.are;

import java.util.Scanner;

public class UI {
    private FileParsersHandler fileParsersHandler;
    public UI(FileParsersHandler fileParsersHandler) {
        this.fileParsersHandler = fileParsersHandler;
    }
    protected void startUI() {
        Scanner inputScanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter file path for word counting: ");
            String inFilePath = inputScanner.nextLine();

            System.out.print("Enter output file: ");
            String outFilePath = inputScanner.nextLine();

            INPUT_FILE_CHECK checkResult = fileParsersHandler.tryFilePaths(inFilePath, outFilePath);
            if (!checkPathsResult(checkResult, outFilePath)) {
                continue;
            }

            int parserIndex = fileParsersHandler.addNewFileParser(inFilePath, outFilePath);
            System.out.println(String.format("Parsing thread %d started successfully, wait for result or...", parserIndex));
        }
    }

    private boolean checkPathsResult(INPUT_FILE_CHECK result, String outFilePath) {
        boolean ret = false;
        switch(result) {
            case BOTH_FILES_EXISTS: {
                System.out.println("Paths checked successfully.");
                ret = true;
                break;
            }
            case OUTPUT_FILE_MADE: {
                System.out.println(String.format("Output file doesn't exists, made at path %S.", outFilePath));
                ret = true;
                break;
            }
            case OUTPUT_FILE_N_MADE: {
                System.out.println(String.format("Output file doesn't exists, but file already exists at path %S .%nTry another output path or delete file at generated path.", outFilePath));
                break;
            }
            case INPUT_FILE_N_EXIST: {
                System.out.println("Input file doesn't exists, try another filepath.");
                break;
            }
            case OUTPUT_FILE_N_EXIST: {
                System.out.println("Output file doesn't exists, try another filepath.");
                break;
            }
        }
        return ret;
    }
}
