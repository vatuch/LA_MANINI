package Second_task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class WriteThread implements Runnable {
    private PrintWriter socketWriter;
    private BufferedReader consoleReader;

    public WriteThread(PrintWriter socketWriter, BufferedReader consoleReader) {
        this.socketWriter = socketWriter;
        this.consoleReader = consoleReader;
    }

    @Override
    public void run() {
        try {
            String message;
            while ((message = consoleReader.readLine()) != null) {
                socketWriter.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}