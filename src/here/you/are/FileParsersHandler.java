package here.you.are;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static here.you.are.INPUT_FILE_CHECK.BOTH_FILES_EXISTS;
import static here.you.are.INPUT_FILE_CHECK.OUTPUT_FILE_MADE;
import static here.you.are.INPUT_FILE_CHECK.OUTPUT_FILE_N_MADE;
import static here.you.are.INPUT_FILE_CHECK.INPUT_FILE_N_EXIST;
import static here.you.are.INPUT_FILE_CHECK.OUTPUT_FILE_N_EXIST;

class FileParsersHandler extends Thread {
    private boolean parserCreated = false;
    private int lastParserIndex = 0;
    private ArrayList<WorkWithFile> fileParsingProcesses = new ArrayList<>();

    private ArrayList<Thread> currentParsingThreads = new ArrayList<>();

    @Override
    public void run() {
        fileParsingProcesses.clear();
        parsersHandler();
    }

    protected int addNewFileParser(String inFilePath, String outFilePath) {
        fileParsingProcesses.add(new WorkWithFile());
        lastParserIndex = fileParsingProcesses.size();
        WorkWithFile newFileParser = fileParsingProcesses.get(lastParserIndex - 1);
        newFileParser.setParserIndex(lastParserIndex);
        newFileParser.setFilePaths(inFilePath, outFilePath);
        parserCreated = true;
        return lastParserIndex;
    }

    private void parsersHandler(){
        while (true) {
            if (parserCreated)
            {
                fileParsingProcesses.trimToSize();
                WorkWithFile startingParser = fileParsingProcesses.get(lastParserIndex - 1);
                Thread newThread = new Thread(startingParser);
                currentParsingThreads.add(newThread);
                newThread.start();
                parserCreated = false;
            }
            else {
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected INPUT_FILE_CHECK tryFilePaths(String inputFilePath, String outputFilePath) {
        INPUT_FILE_CHECK ret;
        File inputFile = new File(inputFilePath);
        if (!(inputFile.exists()))
            return INPUT_FILE_N_EXIST;

        File outputFile = new File(outputFilePath);
        if (!(outputFile.exists())) {
            ret = OUTPUT_FILE_N_EXIST;
            inputFilePath = inputFilePath.replace(".txt", "");
            outputFilePath = inputFilePath + "_frequency.txt";
            outputFile = new File(outputFilePath);
            if (outputFile.exists()) {
                ret = OUTPUT_FILE_N_MADE;
            }
            else{
                try {
                    if (outputFile.createNewFile()) {
                        ret = OUTPUT_FILE_MADE;
                    }
                    else {
                        ret = OUTPUT_FILE_N_MADE;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else
        {
            ret = BOTH_FILES_EXISTS;
        }
        return ret;
    }
}
