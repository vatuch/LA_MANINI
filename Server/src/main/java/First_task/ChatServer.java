package First_task;

import First_task.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
    private static final int PORT = 8889;
    private static List<ClientHandler> clients = new ArrayList<>();
    private ServerSocket serverSocket;

    public void start() throws IOException {
        serverSocket = new ServerSocket(PORT);
        System.out.println("Сервер запущен на порту " + PORT);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Новый клиент подключен: " + clientSocket);

            ClientHandler clientHandler = new ClientHandler(clientSocket, this);
            clients.add(clientHandler);

            Thread thread = new Thread(clientHandler);
            thread.start();
        }
    }

    public void broadcastMessage(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    public void removeClient(ClientHandler client) {
        clients.remove(client);
    }
}

