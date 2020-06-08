package here.you.are;

import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;


class WorkWithFile implements Runnable
{
    private File inputFile;
    private File outputFile;
    private boolean parserThreadCreated = false;
    private int parserIndex;
    private boolean parsingCompleted;
    private CompleteReporter pairedReporter;

    @Override
    public void run() {
        fileParsing();
    }

    public WorkWithFile(CompleteReporter reporterSample) {
        this.pairedReporter = reporterSample;
    }

    protected void setParserIndex(int newIndex) {
        this.parserIndex = newIndex;
    }

    protected void setFilePaths(String inputPath, String outputPath) {
        inputFile = new File(inputPath);
        outputFile = new File(outputPath);
    }

    protected void fileParsing() {
        int wordsCount = countWordsInFile();
        writeToFile(wordsCount);

    }

    private int countWordsInFile() {
        int wordsCount = 0;
        FileReader inputFileReader;
        try {
            inputFileReader = new FileReader(inputFile);
            BufferedReader reader = new BufferedReader(inputFileReader);

            String line;
            String[] words;
            while (true) {
                if ((line = reader.readLine()) != null) {
                    words = line.split("\\s");
                    wordsCount += words.length;
                } else {
                    break;
                }
            }
            inputFileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wordsCount;
    }

    private void writeToFile(int wordsCounted) {
        FileWriter outputFileWriter;
        try {
            outputFileWriter = new FileWriter(outputFile);
            outputFileWriter.write(Integer.toString(wordsCounted));
            outputFileWriter.close();
            throwInfoForReport();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void throwInfoForReport() {
        pairedReporter.setFileIndex(parserIndex);
        pairedReporter.setInputPath(inputFile.getPath());
        pairedReporter.setOutputPath(outputFile.getPath());
        pairedReporter.setParsingCompleted(true);
    }

    public boolean isParserThreadCreated() {
        return parserThreadCreated;
    }

    public void setParserThreadCreated(boolean parserCreated) {
        this.parserThreadCreated = parserCreated;
    }

    public boolean isParsingCompleted() {
        return parsingCompleted;
    }

    public void setParsingCompleted(boolean parsingCompleted) {
        this.parsingCompleted = parsingCompleted;
    }
}


