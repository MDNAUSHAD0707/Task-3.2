// File: ChatClient.java
import java.io.*;
import java.net.*;

public class ChatClient {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 1234;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT)) {
            System.out.println("🔗 Connected to Chat Server");

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            // Thread to read messages from server
            Thread readThread = new Thread(() -> {
                try {
                    String serverMsg;
                    while ((serverMsg = input.readLine()) != null) {
                        System.out.println(serverMsg);
                    }
                } catch (IOException e) {
                    System.out.println("❌ Connection closed.");
                }
            });
            readThread.start();

            // Main thread to send user messages
            String message;
            while ((message = userInput.readLine()) != null) {
                output.println(message);
            }

        } catch (IOException e) {
            System.out.println("❌ Error connecting to server: " + e.getMessage());
        }
    }
}