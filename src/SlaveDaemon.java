class SlaveDaemon extends Thread {
    @Override
    public void run() {
        while (true) {
            if (main.fileHandlerSample.checkIfPathsValid())
                main.fileHandlerSample.fileParsing();
            else
            {
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
