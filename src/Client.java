import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private static final int serverPort = 5555;
    private static final String localHost = "127.0.0.1";

    public static void main(String[] args) {
        Socket socket = null;
        try {
            try {
                System.out.println("Welcome to Client side\n" +
                        "Connecting to the server\n\t" +
                        "(IP address: " + localHost +
                        ", port of server: " + serverPort + ")");
                InetAddress ipAddress = InetAddress.getByName(localHost);
                socket = new Socket(ipAddress, serverPort);
                System.out.println(">> The connection is established.");
                System.out.println(
                        "\tLocalPort = " +
                                socket.getLocalPort() +
                                "\n\tInetAddress.HostAddress = " +
                                socket.getInetAddress()
                                        .getHostAddress() +
                                "\n\tReceiveBufferSize (SO_RCVBUF) = "
                                + socket.getReceiveBufferSize());
                //Создаем входной и выходной потоки сокета для обмена сообщениями с сервером
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                //Создаем поток для чтения с клавиатуры.
                InputStreamReader isr = new InputStreamReader(System.in);
                BufferedReader keyboard = new BufferedReader(isr);

                String line;
                while (true) {
                    System.out.print("\nХотите получить цитату дня? (Yes/No) ");
                    line = keyboard.readLine();
                    if (line.endsWith("Yes")) {
                        outputStream.writeUTF(line); //Отсылаем строку серверу
                        outputStream.flush();        //Завершаем поток
                        line = inputStream.readUTF(); //Ждем ответа от сервера
                    } else if (line.endsWith("No")) {
                        break;
                    } else {
                        System.out.println("\nНеправильная команда");
                        continue;
                    }

                    System.out.println(line); //Сервер отправил мне эту информацию
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
