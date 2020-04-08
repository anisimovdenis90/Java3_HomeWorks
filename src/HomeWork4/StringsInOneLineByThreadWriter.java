package HomeWork4;

public class StringsInOneLineByThreadWriter {
    private final Object lock = new Object();
    private volatile String nowWrite;

    public StringsInOneLineByThreadWriter(int countToWrite, String...stringsToWrite) {
        nowWrite = stringsToWrite[0];
        String nextStringToWrite;
        for (int i = 0; i < stringsToWrite.length; i++) {
            if (stringsToWrite.length > 1 && i != stringsToWrite.length - 1) {
                nextStringToWrite = stringsToWrite[i + 1];
            } else {
                nextStringToWrite = stringsToWrite[0];
            }
            new Thread(new Printer(countToWrite, stringsToWrite[i], nextStringToWrite)).start();
        }
    }

    private class Printer implements Runnable {
        private int countToWrite;
        private String stringToWrite;
        private String anotherThreadString;

        private Printer(int countToWrite, String stringToWrite, String anotherThreadString) {
            this.countToWrite = countToWrite;
            this.stringToWrite = stringToWrite;
            this.anotherThreadString = anotherThreadString;
        }

        @Override
        public void run() {
            synchronized (lock) {
                try {
                    for (int i = 0; i < countToWrite; i++) {
                        while (!nowWrite.equals(stringToWrite)) {
                            lock.wait();
                        }
                        System.out.print(stringToWrite);
                        nowWrite = anotherThreadString;
                        lock.notifyAll();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

