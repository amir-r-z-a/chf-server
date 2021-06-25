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
            ServerSocket serverSocket = new ServerSocket(2442);
            DataBase.getSingleTone().addDataBase("RestaurantAccounts", new Controller("D:\\Code\\DataBase\\Restaurant\\RestaurantAccounts.txt"));
            DataBase.getSingleTone().addDataBase("RestaurantCategories", new Controller("D:\\Code\\DataBase\\Restaurant\\RestaurantCategories.txt"));
            DataBase.getSingleTone().addDataBase("RestaurantFoodNames", new Controller("D:\\Code\\DataBase\\Restaurant\\RestaurantFoodNames.txt"));
            DataBase.getSingleTone().addDataBase("RestaurantFoodDetails", new Controller("D:\\Code\\DataBase\\Restaurant\\RestaurantFoodDetails.txt"));
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Connected");
                RequestHandler requestHandler = new RequestHandler(socket);
                requestHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class RequestHandler extends Thread {
    Socket socket;
    DataInputStream dis;
    DataOutputStream dos;

    public RequestHandler(Socket socket) throws IOException {
        this.socket = socket;
        dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());
    }

    String listener() {
        StringBuilder num = new StringBuilder();
        StringBuilder listen = new StringBuilder();
        char i;
        try {
            while ((i = (char) dis.read()) != ',') {
                num.append(i);
            }
            int counter = Integer.parseInt(num.toString());
            for (int j = 0; j < counter; j++) {
                listen.append((char) dis.read());
            }
        } catch (IOException e) {
            try {
                dis.close();
                dos.close();
                socket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            e.printStackTrace();
        }
        return listen.toString();
    }

    void writer(String write) {
        if (write != null && !write.isEmpty()) {
            try {
                dos.writeUTF(write);
                System.out.println("write: " + write);
            } catch (IOException e) {
                try {
                    dis.close();
                    dos.close();
                    socket.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                e.printStackTrace();
            }
            return;
        }
        System.out.println("invalid write");
    }

    @Override
    public void run() {
//        IOExeption-->Exeption
//        editName,.... --> edit
//        getName,....--> get
//        alreadyPhoneNumber--> getRow
        System.out.println("ready");
        String command = listener();
        System.out.println("command is: " + command);
        String[] split = command.split("-");
        if (split[0].equals("Restaurant")) {
            HashMap<String, String> data;
            RestaurantAccount restaurantAccount;
            RestaurantMenuEdition restaurantMenuEdition;
            StringBuilder finalWrite/* = new StringBuilder()*/;
            switch (split[1]) {
                case "RestaurantSignUp": {
                    if (split[2].equals("alreadyPhoneNumber")) {
                        data = new HashMap<>(
                                Map.of("phoneNumber", split[3]));
                        restaurantAccount = new RestaurantAccount(data);
                        writer(restaurantAccount.alreadyPhoneNumber());
                    } else {
                        data = new HashMap<>(
                                Map.of("name", split[2], "phoneNumber", split[3], "password", split[4],
                                        "open", split[5], "close", split[6], "restaurantType", split[7],
                                        "email", "null", "address", "null", "latLang", "null", "radius", "null"));
                        data.put("rate", "null");
                        restaurantAccount = new RestaurantAccount(data);
                        writer(restaurantAccount.signUp());
                    }
                    break;
                }
                case "RestaurantSignIn": {
                    data = new HashMap<>(
                            Map.of("phoneNumber", split[2], "password", split[3]));
                    restaurantAccount = new RestaurantAccount(data);
                    writer(restaurantAccount.signIn());
                    break;
                }
                case "RestaurantGetData":
                    switch (split[2]) {
                        case "account":
                            data = new HashMap<>(
                                    Map.of("phoneNumber", split[3]));
                            restaurantAccount = new RestaurantAccount(data);
                            writer(restaurantAccount.getAccount());
                            break;
                        case "name":
                            data = new HashMap<>(
                                    Map.of("phoneNumber", split[3]));
                            restaurantAccount = new RestaurantAccount(data);
                            writer(restaurantAccount.getName());
                            break;
                        case "email":
                            data = new HashMap<>(
                                    Map.of("phoneNumber", split[3]));
                            restaurantAccount = new RestaurantAccount(data);
                            writer(restaurantAccount.getEmail());
                            break;
                        case "address":
                            data = new HashMap<>(
                                    Map.of("phoneNumber", split[3]));
                            restaurantAccount = new RestaurantAccount(data);
                            writer(restaurantAccount.getAddress());
                            break;
                        case "radius":
                            data = new HashMap<>(
                                    Map.of("phoneNumber", split[3]));
                            restaurantAccount = new RestaurantAccount(data);
                            writer(restaurantAccount.getRadius());
                            break;
                        case "categories":
                            data = new HashMap<>(
                                    Map.of("phoneNumber", split[3]));
                            restaurantMenuEdition = new RestaurantMenuEdition(data);
                            writer(restaurantMenuEdition.getCategories());
                            break;
                        case "menu":
                            data = new HashMap<>(
                                    Map.of("phoneNumber", split[3]));
                            restaurantMenuEdition = new RestaurantMenuEdition(data);
                            writer(restaurantMenuEdition.getMenu());
                            break;
                    }
                    break;
                case "RestaurantEditProfile":
                    switch (split[2]) {
                        case "feature":
                            finalWrite = new StringBuilder();
                            for (int i = 3; i < split.length; i += 3) {
                                if (i != 3) {
                                    finalWrite.append("-");
                                }
                                switch (split[i]) {
                                    case "name":
                                        data = new HashMap<>(
                                                Map.of("phoneNumber", split[i + 1], "newName", split[i + 2]));
                                        restaurantAccount = new RestaurantAccount(data);
                                        finalWrite.append(restaurantAccount.editName());
                                        break;
                                    case "phoneNumber":
                                        data = new HashMap<>(
                                                Map.of("phoneNumber", split[i + 1], "newPhoneNumber", split[i + 2]));
                                        restaurantAccount = new RestaurantAccount(data);
                                        finalWrite.append(restaurantAccount.editPhoneNumber());
                                        break;
                                    case "email":
                                        data = new HashMap<>(
                                                Map.of("phoneNumber", split[i + 1], "newEmail", split[i + 2]));
                                        restaurantAccount = new RestaurantAccount(data);
                                        finalWrite.append(restaurantAccount.editEmail());
                                        break;
                                }
                            }
                            writer(finalWrite.toString());
                            break;
                        case "password":
                            data = new HashMap<>(
                                    Map.of("phoneNumber", split[3],
                                            "oldPassword", split[4], "newPassword", split[5], "confirmPassword", split[6]));
                            restaurantAccount = new RestaurantAccount(data);
                            writer(restaurantAccount.editPassword());
                            break;
                        case "location":
                            finalWrite = new StringBuilder();
                            for (int i = 3; i < split.length; i += 3) {
                                if (i != 3) {
                                    finalWrite.append("-");
                                }
                                switch (split[i]) {
                                    case "address":
                                        data = new HashMap<>(
                                                Map.of("phoneNumber", split[i + 1],
                                                        "address", split[i + 2]));
                                        restaurantAccount = new RestaurantAccount(data);
                                        finalWrite.append(restaurantAccount.editAddress());
                                        break;
                                    case "latLang":
                                        data = new HashMap<>(
                                                Map.of("phoneNumber", split[i + 1],
                                                        "latLang", split[i + 2]));
                                        restaurantAccount = new RestaurantAccount(data);
                                        finalWrite.append(restaurantAccount.editLatLang());
                                        break;
                                    case "radius":
                                        data = new HashMap<>(
                                                Map.of("phoneNumber", split[i + 1],
                                                        "radius", split[i + 2]));
                                        restaurantAccount = new RestaurantAccount(data);
                                        finalWrite.append(restaurantAccount.editRadius());
                                        break;
                                }
                            }
                            writer(finalWrite.toString());
                            break;
                    }
                case "RestaurantMenuEdition":
                    switch (split[2]) {
                        case "addCategory":
                            data = new HashMap<>(
                                    Map.of("phoneNumber", split[3], "category", split[4]));
                            restaurantMenuEdition = new RestaurantMenuEdition(data);
                            writer(restaurantMenuEdition.addCategory());
                            break;
                        case "addFood":
                            data = new HashMap<>(
                                    Map.of("phoneNumber", split[3], "category", split[4], "foodName", split[5]
                                            , "foodDesc", split[6], "foodPrice", split[7], "foodStatus", split[8]
                                            , "numOfOrder", split[9]));
                            restaurantMenuEdition = new RestaurantMenuEdition(data);
                            writer(restaurantMenuEdition.addFood());
                            break;
                        case "deleteFood":
                            data = new HashMap<>(
                                    Map.of("phoneNumber", split[3], "category", split[4], "foodName", split[5]));
                            restaurantMenuEdition = new RestaurantMenuEdition(data);
                            writer(restaurantMenuEdition.deleteFood());
                            break;
                        case "editStatus":
                            data = new HashMap<>(
                                    Map.of("phoneNumber", split[3], "category", split[4]
                                            , "foodName", split[5], "newFoodStatus", split[6]));
                            restaurantMenuEdition = new RestaurantMenuEdition(data);
                            writer(restaurantMenuEdition.editStatus());
                            break;
                        case "editOther":
                            data = new HashMap<>(
                                    Map.of("phoneNumber", split[3], "category", split[4]
                                            , "foodName", split[5], "newFoodName", split[6]
                                            , "newFoodDesc", split[7], "newFoodPrice", split[8]));
                            restaurantMenuEdition = new RestaurantMenuEdition(data);
                            writer(restaurantMenuEdition.editOther());
                            break;
//                        case "length":
//                            data = new HashMap<>(
//                                    Map.of("phoneNumber", split[3]));
//                            restaurantCategories = new RestaurantCategories(data);
//                            writer(restaurantCategories.getLength());
//                            break;
                    }
                    break;
            }
        } else if (command.equals("Client")) {

        }
        try {
            dis.close();
            dos.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("done");
    }
}
