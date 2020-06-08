package here.you.are;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static here.you.are.InputFileCheck.BOTH_FILES_EXISTS;
import static here.you.are.InputFileCheck.OUTPUT_FILE_MADE;
import static here.you.are.InputFileCheck.OUTPUT_FILE_N_MADE;
import static here.you.are.InputFileCheck.INPUT_FILE_N_EXIST;
import static here.you.are.InputFileCheck.OUTPUT_FILE_N_EXIST;

class FileParsersHandler extends Thread {
    private Map<WorkWithFile, CompleteReporter> fileParsingProcesses = new HashMap<>();

    @Override
    public void run() {
        fileParsingProcesses.clear();
        parsersHandler();
    }

    protected int addNewFileParser(String inFilePath, String outFilePath) { // Добавление нового потока-обработчика файла
        CompleteReporter newCompleteReporter = new CompleteReporter();
        WorkWithFile newFileParser = new WorkWithFile(newCompleteReporter);
        fileParsingProcesses.put(newFileParser, newCompleteReporter);
        int parserIndex = fileParsingProcesses.size();
        newFileParser.setParserIndex(parserIndex);
        newFileParser.setFilePaths(inFilePath, outFilePath);
        return parserIndex;
    }

    private void parsersHandler(){
        while (true) {
            for (Map.Entry<WorkWithFile, CompleteReporter> entry : fileParsingProcesses.entrySet()) {
                WorkWithFile parser = entry.getKey();
                if (!(parser.isParserThreadCreated())) { // Если поток ещё не создан -
                    Thread newParserThread = new Thread(parser);
                    Thread newReporterThread = new Thread(entry.getValue());
                    newParserThread.start();
                    newReporterThread.start();
                    parser.setParserThreadCreated(true);
                }
            }

            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    protected InputFileCheck tryFilePaths(String inputFilePath, String outputFilePath) {
        InputFileCheck ret;
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
