class SlaveDaemon extends Thread {
    @Override
    public void run() {
        while (true) {
            if (main.FileHandlerSample.checkIfPathsValid())
                main.FileHandlerSample.fileParsing();
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
