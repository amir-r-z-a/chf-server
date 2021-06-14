import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(2020);
            DataBase.getSingleTone().addDataBase("RestaurantSignUp", new Table("D//Code//DataBase//"));

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Connected");
                ClientHandler clientHandler = new ClientHandler(socket);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


class ClientHandler extends Thread {
    Socket socket;
    DataInputStream dis;
    DataOutputStream dos;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            String command = dis.readUTF();
            String[] strings = command.split("-");
            if (strings[0].equals("-Restaurant")) {
                if (strings[1].equals("RestaurantSignUp")) {

                }
            } else if (command.equals("-Client")) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
