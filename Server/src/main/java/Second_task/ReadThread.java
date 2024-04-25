package Second_task;

import java.io.BufferedReader;
import java.io.IOException;


public class ReadThread implements Runnable {
    private BufferedReader socketReader;

    public ReadThread(BufferedReader socketReader) {
        this.socketReader = socketReader;
    }

    @Override
    public void run() {
        try {
            String message;
            while ((message = socketReader.readLine()) != null) {
                System.out.println("Received message: " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
