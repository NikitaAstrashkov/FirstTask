package here.you.are;

import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;


class WorkWithFile implements Runnable
{
    private File inputFile;
    private File outputFile;
    private int parserIndex;

    @Override
    public void run() {
        fileParsing();
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
        FileReader inputFileReader = null;
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
        FileWriter outputFileWriter = null;
        try {
            outputFileWriter = new FileWriter(outputFile);
            outputFileWriter.write(Integer.toString(wordsCounted));
            outputFileWriter.close();
            reportParsingCompleted();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void reportParsingCompleted() {
        System.out.println(String.format("Thread #%d finished.%nResult of parsing file %s saved at path %s", parserIndex, inputFile.getPath(), outputFile.getPath()));
    }
}


