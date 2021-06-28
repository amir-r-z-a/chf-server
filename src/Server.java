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
            DataBase.getSingleTone().addDataBase("RestaurantTopTenFoods", new Controller("D:\\Code\\DataBase\\Restaurant\\RestaurantTopTenFoods.txt"));
            DataBase.getSingleTone().addDataBase("RestaurantActiveOrdersData", new Controller("D:\\Code\\DataBase\\Restaurant\\RestaurantActiveOrdersData.txt"));
            DataBase.getSingleTone().addDataBase("RestaurantActiveOrdersFoodNames", new Controller("D:\\Code\\DataBase\\Restaurant\\RestaurantActiveOrdersFoodNames.txt"));
            DataBase.getSingleTone().addDataBase("RestaurantActiveOrdersNumbers", new Controller("D:\\Code\\DataBase\\Restaurant\\RestaurantActiveOrdersNumbers.txt"));
            DataBase.getSingleTone().addDataBase("RestaurantOrdersHistoryData", new Controller("D:\\Code\\DataBase\\Restaurant\\RestaurantOrdersHistoryData.txt"));
            DataBase.getSingleTone().addDataBase("RestaurantOrdersHistoryFoodNames", new Controller("D:\\Code\\DataBase\\Restaurant\\RestaurantOrdersHistoryFoodNames.txt"));
            DataBase.getSingleTone().addDataBase("RestaurantOrdersHistoryNumbers", new Controller("D:\\Code\\DataBase\\Restaurant\\RestaurantOrdersHistoryNumbers.txt"));
            DataBase.getSingleTone().addDataBase("RestaurantComments", new Controller("D:\\Code\\DataBase\\Restaurant\\RestaurantComments.txt"));
            DataBase.getSingleTone().addDataBase("ClientActiveOrdersData", new Controller("D:\\Code\\DataBase\\Client\\ClientActiveOrdersData.txt"));
            DataBase.getSingleTone().addDataBase("ClientActiveOrdersFoodNames", new Controller("D:\\Code\\DataBase\\Client\\ClientActiveOrdersFoodNames.txt"));
            DataBase.getSingleTone().addDataBase("ClientActiveOrdersNumbers", new Controller("D:\\Code\\DataBase\\Client\\ClientActiveOrdersNumbers.txt"));
            DataBase.getSingleTone().addDataBase("ClientOrdersHistoryData", new Controller("D:\\Code\\DataBase\\Client\\ClientOrdersHistoryData.txt"));
            DataBase.getSingleTone().addDataBase("ClientOrdersHistoryFoodNames", new Controller("D:\\Code\\DataBase\\Client\\ClientOrdersHistoryFoodNames.txt"));
            DataBase.getSingleTone().addDataBase("ClientOrdersHistoryNumbers", new Controller("D:\\Code\\DataBase\\Client\\ClientOrdersHistoryNumbers.txt"));
            DataBase.getSingleTone().addDataBase("ClientAccounts", new Controller("D:\\Code\\DataBase\\Client\\ClientAccounts.txt"));
            DataBase.getSingleTone().addDataBase("ClientFavRestaurants", new Controller("D:\\Code\\DataBase\\Client\\ClientFavRestaurants.txt"));
            DataBase.getSingleTone().addDataBase("ClientComments", new Controller("D:\\Code\\DataBase\\Client\\ClientComments.txt"));
            DataBase.getSingleTone().addDataBase("ClientAddresses", new Controller("D:\\Code\\DataBase\\Client\\ClientAddresses.txt"));
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
//        case RestaurantGetData: --> factor data and RestaurantMenuEdition
//        strBuilder.append() in edits--> ans.append()
//        DataBase.getSingleTone().getController("").writeFile(ans.toString(), true)   (true: new FileWriter())   ;--> DataBase.getSingleTone().getController("").writeFile(data[i]);
//        writeFile("",true)--> delete
        System.out.println("ready");
        String command = listener();
        System.out.println("command is: " + command);
        String[] split = command.split("-");
        if (split[0].equals("Restaurant")) {
            HashMap<String, String> data;
            RestaurantAccount restaurantAccount;
            RestaurantMenuEdition restaurantMenuEdition;
            RestaurantOrders restaurantOrders;
            RestaurantTopTenFoods restaurantTopTenFoods;
            StringBuilder finalWrite = new StringBuilder();
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
                                        "email", "null", "address", "null"
                                        , "latLang", "null", "radius", "null"));
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
                        case "accounts":
                            restaurantAccount = new RestaurantAccount(null);
                            restaurantMenuEdition = new RestaurantMenuEdition(null);
                            writer(restaurantAccount.getAccounts() + "***" + restaurantMenuEdition.getAllCategories() + "***" + restaurantMenuEdition.getAllMenu());
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
                case "RestaurantOrders":
                    for (int i = 2; i < split.length; i += 2) {
                        if (i != 2) {
                            finalWrite.append("...");
                        }
                        switch (split[i]) {
                            case "RestaurantActiveOrdersData":
                                data = new HashMap<>(
                                        Map.of("phoneNumber", split[i + 1]));
                                restaurantOrders = new RestaurantOrders(data);
                                finalWrite.append(restaurantOrders.RestaurantActiveOrdersData());
                                break;
                            case "RestaurantActiveOrdersFoodNames":
                                data = new HashMap<>(
                                        Map.of("phoneNumber", split[i + 1]));
                                restaurantOrders = new RestaurantOrders(data);
                                finalWrite.append(restaurantOrders.RestaurantActiveOrdersFoodNames());
                                break;
                            case "RestaurantActiveOrdersNumbers":
                                data = new HashMap<>(
                                        Map.of("phoneNumber", split[i + 1]));
                                restaurantOrders = new RestaurantOrders(data);
                                finalWrite.append(restaurantOrders.RestaurantActiveOrdersNumbers());
                                break;
                            case "RestaurantFinishedOrders":
                                String orderID = split[i + 2] + "-" + split[i + 3];
                                data = new HashMap<>(
                                        Map.of("phoneNumber", split[i + 1]
                                                , "orderID", orderID));
                                restaurantOrders = new RestaurantOrders(data);
                                finalWrite.append(restaurantOrders.RestaurantFinishedOrders());
                                break;
                            case "RestaurantOrdersHistoryData":
                                data = new HashMap<>(
                                        Map.of("phoneNumber", split[i + 1]));
                                restaurantOrders = new RestaurantOrders(data);
                                finalWrite.append(restaurantOrders.RestaurantOrdersHistoryData());
                                break;
                            case "RestaurantOrdersHistoryFoodNames":
                                data = new HashMap<>(
                                        Map.of("phoneNumber", split[i + 1]));
                                restaurantOrders = new RestaurantOrders(data);
                                finalWrite.append(restaurantOrders.RestaurantOrdersHistoryFoodNames());
                                break;
                            case "RestaurantOrdersHistoryNumbers":
                                data = new HashMap<>(
                                        Map.of("phoneNumber", split[i + 1]));
                                restaurantOrders = new RestaurantOrders(data);
                                finalWrite.append(restaurantOrders.RestaurantOrdersHistoryNumbers());
                                break;
                        }
                    }
                    writer(finalWrite.toString());
                    break;
                case "RestaurantTopTenFoods":
                    data = new HashMap<>(
                            Map.of("phoneNumber", split[2], "topTenFoods", split[3]));
                    restaurantTopTenFoods = new RestaurantTopTenFoods(data);
                    writer(restaurantTopTenFoods.refreshTopTenFoods());
                    break;
            }
        } else if (split[0].equals("Client")) {
            HashMap<String, String> data;
            ClientOrders clientOrders;
            ClientAccount clientAccount;
            ClientFavRestaurants clientFavRestaurants;
            Comments comments;
            StringBuilder finalWrite = new StringBuilder();
            switch (split[1]) {
                case "ClientSignUp":
                    if (split[2].equals("alreadyPhoneNumber")) {
                        data = new HashMap<>(
                                Map.of("phoneNumber", split[3]));
                        clientAccount = new ClientAccount(data);
                        writer(clientAccount.alreadyPhoneNumber());
                    } else {
                        data = new HashMap<>(
                                Map.of("name", split[2], "phoneNumber", split[3], "password", split[4]));
                        clientAccount = new ClientAccount(data);
                        writer(clientAccount.signUp());
                    }
                    break;
                case "ClientSignIn":
                    data = new HashMap<>(
                            Map.of("phoneNumber", split[2], "password", split[3]));
                    clientAccount = new ClientAccount(data);
                    writer(clientAccount.signIn());
                    break;
                case "ClientGetData":
                    switch (split[2]) {
                        case "account":
                            data = new HashMap<>(
                                    Map.of("phoneNumber", split[3]));
                            clientAccount = new ClientAccount(data);
                            writer(clientAccount.getAccount());
                            break;
                        case "clientFavRestaurants":
                            data = new HashMap<>(
                                    Map.of("phoneNumber", split[3]));
                            clientAccount = new ClientAccount(data);
                            writer(clientAccount.getFavRestaurants());
                            break;
                        case "clientComments":
                            data = new HashMap<>(
                                    Map.of("phoneNumber", split[3]));
                            clientAccount = new ClientAccount(data);
                            writer(clientAccount.getComments());
                            break;
                    }
                    break;
                case "ClientAddresses":
                    switch (split[2]) {
                        case "addAddress":
                            data = new HashMap<>(
                                    Map.of("phoneNumber", split[3], "newAddress", split[4]));
                            clientAccount = new ClientAccount(data);
                            writer(clientAccount.addAddress());
                            break;
                        case "deleteAddress":
                            data = new HashMap<>(
                                    Map.of("phoneNumber", split[3], "address", split[4]));
                            clientAccount = new ClientAccount(data);
                            writer(clientAccount.deleteAddress());
                            break;
                    }
                    break;
                case "ClientOrders":
                    for (int i = 2; i < split.length; i += 2) {
                        if (i != 2) {
                            finalWrite.append("...");
                        }
                        switch (split[i]) {
                            case "AddOrder":
                                data = new HashMap<>(
                                        Map.of("phoneNumber", split[i + 1], "name", split[i + 2]
                                                , "clientPhoneNumber", split[i + 3], "address", split[i + 4]
                                                , "latLng", split[i + 5], "orderDate", split[i + 6]
                                                , "isOnlinePayment", split[i + 7], "foodNames", split[i + 8]
                                                , "numbers", split[i + 9], "restaurantName", split[i + 10]));
                                data.put("sumPrice", split[i + 11]);
                                data.put("sumNumberOfFoods", split[i + 12]);
                                clientOrders = new ClientOrders(data);
                                writer(clientOrders.AddOrder());
                                break;
                            case "ClientActiveOrdersData":
                                data = new HashMap<>(
                                        Map.of("phoneNumber", split[i + 1]));
                                clientOrders = new ClientOrders(data);
                                finalWrite.append(clientOrders.ClientActiveOrdersData());
                                break;
                            case "ClientActiveOrdersFoodNames":
                                data = new HashMap<>(
                                        Map.of("phoneNumber", split[i + 1]));
                                clientOrders = new ClientOrders(data);
                                finalWrite.append(clientOrders.ClientActiveOrdersFoodNames());
                                break;
                            case "ClientActiveOrdersNumbers":
                                data = new HashMap<>(
                                        Map.of("phoneNumber", split[i + 1]));
                                clientOrders = new ClientOrders(data);
                                finalWrite.append(clientOrders.ClientActiveOrdersNumbers());
                                break;
                            case "ClientOrdersHistoryData":
                                data = new HashMap<>(
                                        Map.of("phoneNumber", split[i + 1]));
                                clientOrders = new ClientOrders(data);
                                finalWrite.append(clientOrders.ClientOrdersHistoryData());
                                break;
                            case "ClientOrdersHistoryFoodNames":
                                data = new HashMap<>(
                                        Map.of("phoneNumber", split[i + 1]));
                                clientOrders = new ClientOrders(data);
                                finalWrite.append(clientOrders.ClientOrdersHistoryFoodNames());
                                break;
                            case "ClientOrdersHistoryNumbers":
                                data = new HashMap<>(
                                        Map.of("phoneNumber", split[i + 1]));
                                clientOrders = new ClientOrders(data);
                                finalWrite.append(clientOrders.ClientOrdersHistoryNumbers());
                                break;
                        }
                    }
                    writer(finalWrite.toString());
                    break;
                case "ClientFavRestaurants":
                    data = new HashMap<>(
                            Map.of("phoneNumber", split[2], "favRestaurants", split[3]));
                    clientFavRestaurants = new ClientFavRestaurants(data);
                    writer(clientFavRestaurants.refreshFavRestaurants());
                    break;
                case "ClientEditProfile":
                    switch (split[2]) {
                        case "feature":
                            for (int i = 3; i < split.length; i += 3) {
                                if (i != 3) {
                                    finalWrite.append("-");
                                }
                                switch (split[i]) {
                                    case "name":
                                        data = new HashMap<>(
                                                Map.of("phoneNumber", split[i + 1], "newName", split[i + 2]));
                                        clientAccount = new ClientAccount(data);
                                        finalWrite.append(clientAccount.editName());
                                        break;
                                    case "phoneNumber":
                                        data = new HashMap<>(
                                                Map.of("phoneNumber", split[i + 1], "newPhoneNumber", split[i + 2]));
                                        clientAccount = new ClientAccount(data);
                                        finalWrite.append(clientAccount.editPhoneNumber());
                                        break;
                                    case "email":
                                        data = new HashMap<>(
                                                Map.of("phoneNumber", split[i + 1], "newEmail", split[i + 2]));
                                        clientAccount = new ClientAccount(data);
                                        finalWrite.append(clientAccount.editEmail());
                                        break;
                                }
                            }
                            writer(finalWrite.toString());
                            break;
                        case "password":
                            data = new HashMap<>(
                                    Map.of("phoneNumber", split[3],
                                            "oldPassword", split[4], "newPassword", split[5], "confirmPassword", split[6]));
                            clientAccount = new ClientAccount(data);
                            writer(clientAccount.editPassword());
                            break;
                        case "wallet":
                            break;
                    }
                    break;
                case "Comments":
                    switch (split[2]) {
                        case "addComment":
                            data = new HashMap<>(
                                    Map.of("phoneNumber", split[3],
                                            "question", split[4], "answer", split[5], "foodName", split[6],
                                            "PhoneNumberRest", split[7], "date", split[8]));
                            comments = new Comments(data);
                            writer(comments.addComment());
                            break;
                        case "RestaurantComments":
                            data = new HashMap<>(
                                    Map.of("phoneNumber", split[3]));
                            comments = new Comments(data);
                            writer(comments.RestaurantComments());
                            break;
                        case "reply":
                            String id = split[4] + "-" + split[5];
                            data = new HashMap<>(
                                    Map.of("phoneNumber", split[3], "id", id, "newAnswer", split[6]));
                            comments = new Comments(data);
                            writer(comments.reply());
                            break;
                    }
                    break;
            }
        } else if (split[0].equals("Other")) {
            System.out.println("other");
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
