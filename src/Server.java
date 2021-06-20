import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(2020);
//            DataBase.getSingleTone().addDataBase("RestaurantSignUp", new Table("D//Code//DataBase//Restaurant//RestaurantSignUp.txt"));
//            DataBase.getSingleTone().addDataBase("MenuEdition", new Table("D//Code//DataBase//Restaurant//MenuEdition.txt"));
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
//            System.out.println("ready");
            String command = ""/*dis.read()*/;
//            System.out.println(command);
//            dos.writeUTF("aaaaaaaaaa");
//            dos.flush();
//            System.out.println("done");
            String[] split = command.split("-");
            if (split[0].equals("-Restaurant")) {
                if (split[1].equals("RestaurantSignUp")) {
                    HashMap<String, String> data = new HashMap<>(
                            Map.of("name", split[2], "phoneNumber", split[3], "password", split[4],
                                    "openHour", split[5], "closeHour", split[6], "restaurantType", split[7]));
                    RestaurantAccount restaurantAccount = new RestaurantAccount(data);
                    dos.writeUTF(restaurantAccount.alreadyPhoneNumber());
                } else if (split[1].equals("RestaurantSignIn")) {
                    HashMap<String, String> data = new HashMap<>(
                            Map.of("phoneNumber", split[2], "password", split[3]));
                    RestaurantAccount restaurantAccount = new RestaurantAccount(data);
                    dos.writeUTF(restaurantAccount.foundPhoneNumber() + "-" + restaurantAccount.foundPassword());
                }
            } else if (command.equals("-Client")) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
