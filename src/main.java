import java.io.File;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class main {
    protected static WorkWithFile fileHandlerSample = new WorkWithFile();
    public static void main(String[] args)
    {
        SlaveDaemon slaveDaemon = new SlaveDaemon();
        slaveDaemon.setDaemon(true);
        slaveDaemon.start();
        while (true) {
            fileHandlerSample.setParsingIncomplete();
            Scanner inputScanner = new Scanner(System.in);

            /* Reading input file path */
            System.out.print("Enter file path for word counting: ");
            String inFilePath = inputScanner.nextLine();
            if (!fileHandlerSample.tryInputPath(inFilePath)) // Trying to find a file at entered path
            {
                System.out.println("Input file doesn't exist, try again."); // Can't go further without input file
                continue;
            }

            /* Reading output file path */
            System.out.print("Enter output file: ");
            String outFilePath = inputScanner.nextLine();
            if (!fileHandlerSample.tryOutputPath(outFilePath))  // Trying to find a file at entered path
            {                                                   // File doesn't exist, made new file
                System.out.println("Output file doesn't exist, generated file at path " + fileHandlerSample.getOutputPath());
            }

            while (fileHandlerSample.parsingProceeding())
            {
                System.out.println(fileHandlerSample.getParsingState());
                try {
                    sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Parsing completed. Result saved in " + fileHandlerSample.getOutputPath());
            System.out.println("Enter any character to parse another file");
            if (inputScanner.hasNext());

            // C:\Users\nicit\Desktop\GOlang.txt
            // C:\Users\nicit\Desktop\GOGO.txt
            // C:\Users\nicit\Desktop\New Text Document (3).txt
        }
    }
}
