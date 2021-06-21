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
            DataBase.getSingleTone().addDataBase("RestaurantAccounts", new Controller("D:\\Code\\DataBase\\Restaurant\\RestaurantAccounts.txt"));
//            DataBase.getSingleTone().addDataBase("MenuEdition", new Controller("D://Code//DataBase//Restaurant//MenuEdition.txt"));
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

    String listener() {
        StringBuilder num = new StringBuilder();
        StringBuilder ans = new StringBuilder();
        char i;
        try {
            while ((i = (char) dis.read()) != ',') {
                num.append(i);
            }
            int counter = Integer.parseInt(num.toString());
            for (int j = 0; j < counter; j++) {
                ans.append((char) dis.read());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ans.toString();
    }

    @Override
    public void run() {
//        IOExeption-->Exeption
        try {
            System.out.println("ready");
            String command = listener();
            System.out.println("command is: " + command);
            while (!command.equals("finish")) {
                String[] split = command.split("-");
                if (split[0].equals("Restaurant")) {
                    switch (split[1]) {
                        case "RestaurantSignUp": {
                            HashMap<String, String> data = new HashMap<>(
                                    Map.of("name", split[2], "phoneNumber", split[3], "password", split[4],
                                            "openHour", split[5], "closeHour", split[6], "restaurantType", split[7],
                                            "email", "null"));
                            RestaurantAccount restaurantAccount = new RestaurantAccount(data);
                            dos.writeUTF(restaurantAccount.signUp());
                            break;
                        }
                        case "RestaurantSignIn": {
                            HashMap<String, String> data = new HashMap<>(
                                    Map.of("phoneNumber", split[2], "password", split[3]));
                            RestaurantAccount restaurantAccount = new RestaurantAccount(data);
                            dos.writeUTF(restaurantAccount.signIn());
                            break;
                        }
                        case "EditProfile":
                            HashMap<String, String> data;
                            RestaurantAccount restaurantAccount;
                            switch (split[2]) {
                                case "name":
                                    data = new HashMap<>(
                                            Map.of("phoneNumber", split[3], "newName", split[4]));
                                    restaurantAccount = new RestaurantAccount(data);
                                    dos.writeUTF(restaurantAccount.editName());
                                    break;
                                case "password":
                                    data = new HashMap<>(
                                            Map.of("phoneNumber", split[3],
                                                    "oldPassword", split[4], "newPassword", split[5]));
                                    restaurantAccount = new RestaurantAccount(data);
                                    dos.writeUTF(restaurantAccount.editPassword());
                                    break;
                                case "email":
                                    data = new HashMap<>(
                                            Map.of("phoneNumber", split[3], "newEmail", split[4]));
                                    restaurantAccount = new RestaurantAccount(data);
                                    dos.writeUTF(restaurantAccount.editEmail());
                                    break;
                            }
                            break;
                    }
                } else if (command.equals("Client")) {

                }

                command = listener();
                System.out.println(command);
            }
            System.out.println("done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
