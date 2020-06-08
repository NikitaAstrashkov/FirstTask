package here.you.are;

public class CompleteReporter implements Runnable {
    private int fileIndex;
    private String inputPath;
    private String outputPath;
    private boolean parsingCompleted = false;

    public void run() {
        writerHoldLoop();
    }

    private void writerHoldLoop() {
        while (true) {
            if (!(isParsingCompleted())) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            } else {
                reportParsingCompleted();
                return;
            }
        }
    }

    private void reportParsingCompleted() {
        System.out.println(String.format("Thread #%d finished.%nResult of parsing of file %s saved at path %s", getFileIndex(), getInputPath(), getOutputPath()));
    }

    public int getFileIndex() {
        return fileIndex;
    }

    public void setFileIndex(int fileIndex) {
        this.fileIndex = fileIndex;
    }

    public String getInputPath() {
        return inputPath;
    }

    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public boolean isParsingCompleted() {
        return parsingCompleted;
    }

    public void setParsingCompleted(boolean parsingCompleted) {
        this.parsingCompleted = parsingCompleted;
    }
}
