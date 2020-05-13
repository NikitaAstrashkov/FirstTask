import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;


public class WorkWithFile
{
    protected boolean parsingCompleted;
    protected volatile boolean inputPathValid = false;
    protected volatile boolean outputPathValid = false;
    protected File inputFile;
    protected File outputFile;
    volatile int linesCountSaved;
    volatile int linesParsedCount;
    volatile int wordsFound;
    List<String> inputFileRows = new ArrayList<>();

    protected String getOutputPath()
    {
        return outputFile.getPath();
    }

    protected void setParsingIncomplete()
    {
        parsingCompleted = false;
    }

    protected boolean tryInputPath(String filePath)
    {
        inputPathValid = false;
        inputFile = new File(filePath);
        if (inputFile.exists())
            inputPathValid = true;
        return inputPathValid;
    }

    protected boolean tryOutputPath(String filePath)
    {
        outputPathValid = false;
        outputFile = new File(filePath);
        if (outputFile.exists())
            outputPathValid = true;
        else
        {
            filePath = inputFile.getPath() + "_frequency.txt";
            outputFile = new File(filePath);
            if (outputFile.exists())
                outputFile.delete();
            try {
                if (outputFile.createNewFile())
                    outputPathValid = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
        return outputPathValid;
    }

    protected boolean parsingProceeding()
    {
        boolean ret = false;
        if (!parsingCompleted)
            ret = true;
        return ret;
    }

    protected boolean checkIfPathsValid()
    {
        boolean ret = false;
        if (inputPathValid && outputPathValid)
            ret = true;
        return ret;
    }

    protected String getParsingState()
    {
        String ret = "File parsing proceeding, ";
        int rows = inputFileRows.size();
        if (rows == 0)
            ret = "Preparing.";
        else if (rows != linesCountSaved)
        {
            linesCountSaved = rows;
            ret += String.format("%d rows found.", rows);
        }
        else
            ret += String.format("parsed %d rows out of %d.", linesParsedCount, rows);

        return ret;
    }

    protected void resetParsingState()
    {
        parsingCompleted = true;
        linesParsedCount = 0;
        linesCountSaved = 0;
        wordsFound = 0;
        inputPathValid = false;
        outputPathValid = false;
    }

    protected void fileParsing(){
        // Reading lines from file, collecting to arraylist
        FileReader inputFileReader = null;
        try {
            inputFileReader = new FileReader(inputFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try (BufferedReader reader = new BufferedReader(inputFileReader)) {
            String line;
            while (true) {
                if ((line = reader.readLine()) != null)
                    inputFileRows.add(line);
                else
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            inputFileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        // Lines into arraylist, time to action
        for (String line: inputFileRows) {
            String[] words = line.split("\\s");
            wordsFound += words.length;
            linesParsedCount++;
        }

        inputFileRows.clear();


        FileWriter outputFileWriter = null;
        try {
            outputFileWriter = new FileWriter(outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(outputFileWriter)) {
                writer.write(Integer.toString(wordsFound));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            outputFileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        resetParsingState();
    }
}

