import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ClientOrders {
    private final HashMap<String, String> data;

    public ClientOrders(HashMap<String, String> data) {
        this.data = data;
    }

    public HashMap<String, String> getData() {
        return data;
    }

    String AddOrder() {
        String id = ordersIDGenerator();
        DataBase.getSingleTone().getController("RestaurantActiveOrdersData")
                .writeFile(data.get("phoneNumber") + ":" + id + ": {, "
                        + data.get("name") + ", " + data.get("clientPhoneNumber") + ", " + data.get("address")
                        + ", " + data.get("latLng") + ", " + data.get("orderDate") + ", "
                        + data.get("isOnlinePayment") + ", " + data.get("sumPrice") + ", " + data.get("sumNumberOfFoods") + ", }\n");
        DataBase.getSingleTone().getController("RestaurantActiveOrdersFoodNames")
                .writeFile(data.get("phoneNumber") + ":" + id + ":" + data.get("foodNames") + "\n");
        DataBase.getSingleTone().getController("RestaurantActiveOrdersNumbers")
                .writeFile(data.get("phoneNumber") + ":" + id + ":" + data.get("numbers") + "\n");
        DataBase.getSingleTone().getController("ClientActiveOrdersData")
                .writeFile(data.get("clientPhoneNumber") + ":" + id + ": {, "
                        + data.get("phoneNumber") + ", " + data.get("restaurantName") + ", " + data.get("address")
                        + ", " + data.get("latLng") + ", " + data.get("orderDate") + ", "
                        + data.get("isOnlinePayment") + ", " + data.get("sumPrice") + ", " + data.get("sumNumberOfFoods") + ", }\n");
        DataBase.getSingleTone().getController("ClientActiveOrdersFoodNames")
                .writeFile(data.get("clientPhoneNumber") + ":" + id + ":" + data.get("foodNames") + "\n");
        DataBase.getSingleTone().getController("ClientActiveOrdersNumbers")
                .writeFile(data.get("clientPhoneNumber") + ":" + id + ":" + data.get("numbers") + "\n");
        return "valid";
    }

    String ordersIDGenerator() {
        List<String> numbers = new ArrayList<>(Arrays
                .asList(DataBase.getSingleTone().getController("RestaurantActiveOrdersNumbers").readFile().split("\n")));
        numbers.addAll(new ArrayList<>(Arrays.asList(DataBase.getSingleTone()
                .getController("RestaurantOrdersHistoryNumbers").readFile().split("\n"))));
        String randomID;
        boolean flag = false;
        do {
            randomID = "#" + data.get("name").charAt(0)
                    + data.get("clientPhoneNumber").substring(data.get("clientPhoneNumber").length() - 4) + '-'
                    + (int) (Math.random() * 8999 + 1000);
            for (String str : numbers) {
                if (!str.equals("") && str.substring(str.indexOf(":") + 1, str.lastIndexOf(":")).equals(randomID)) {
                    flag = true;
                    break;
                }
            }
        } while (flag);
        return randomID;
    }

    String ClientActiveOrdersData() {
        String[] ordersData = DataBase.getSingleTone().getController("ClientActiveOrdersData").readFile().split("\n");
        if (!ordersData[0].equals("")) {
            StringBuilder ans = new StringBuilder();
            int n = ordersData.length;
            for (int i = 0; i < n; i++) {
                if (ordersData[i].startsWith(data.get("phoneNumber"))) {
                    ans.append(ordersData[i].split(":")[1]).append(", ").append(ordersData[i], ordersData[i].indexOf(",") + 2, ordersData[i].lastIndexOf(","));
                    if (i != n - 1) {
                        ans.append("\n");
                    }
                }
            }
            return ans.toString();
        }
        return "invalid";
    }

    String ClientActiveOrdersFoodNames() {
        String[] ordersFoodNames = DataBase.getSingleTone().getController("ClientActiveOrdersFoodNames").readFile().split("\n");
        if (!ordersFoodNames[0].equals("")) {
            String[] elementsSplit =
                    DataBase.getSingleTone().getController("RestaurantFoodDetails").readFile().split("\n");
            String[] getData = ClientActiveOrdersData().split("\n");
            StringBuilder ans = new StringBuilder();
            int len = ordersFoodNames.length;
            for (int counter = 0; counter < len; counter++) {
                if (ordersFoodNames[counter].startsWith(data.get("phoneNumber"))) {
                    String phoneNumber = "";
                    String clientID = ordersFoodNames[counter].split(":")[1];
                    for (String st : getData) {
                        if (st.startsWith(data.get("phoneNumber") + ":" + clientID)) {
                            phoneNumber = st.split(", ")[1];
                        }
                    }
                    String[] strings = ordersFoodNames[counter].split(", ");
                    int n = strings.length;
                    for (int i = 1; i < n - 1; i++) {
                        String elements = "invalidFood";
                        for (String s : elementsSplit) {
                            String[] test = s.split(":");
                            if (s.startsWith(phoneNumber) && !test[1].equals("All") && test[2].equals(strings[i])) {
                                elements = s;
                                break;
                            }
                        }
                        String[] splitElements = elements.split(", ")[0].split(":");
                        StringBuilder strBuilder = new StringBuilder(splitElements[1] + ", " + splitElements[2] + ", ");
                        strBuilder.append(elements, elements.indexOf(",") + 2, elements.lastIndexOf(","));
                        if (i != n - 2) {
                            strBuilder.append("+");
                        }
                        ans.append(strBuilder);
                    }
                    if (counter != len - 1) {
                        ans.append("\n");
                    }
                }
            }
            return ans.toString();
        }
        return "invalid";
    }

    String ClientActiveOrdersNumbers() {
        String[] numbers = DataBase.getSingleTone().getController("ClientActiveOrdersNumbers").readFile().split("\n");
        if (!numbers[0].equals("")) {
            StringBuilder ans = new StringBuilder();
            int n = numbers.length;
            for (int i = 0; i < n; i++) {
                if (numbers[i].startsWith(data.get("phoneNumber"))) {
                    ans.append(numbers[i], numbers[i].indexOf(",") + 2, numbers[i].lastIndexOf(","));
                    if (i != n - 1) {
                        ans.append("\n");
                    }
                }
            }
            return ans.toString();
        }
        return "invalid";
    }

    String ClientOrdersHistoryData() {
        String[] ordersData = DataBase.getSingleTone().getController("ClientOrdersHistoryData").readFile().split("\n");
        if (!ordersData[0].equals("")) {
            StringBuilder ans = new StringBuilder();
            int n = ordersData.length;
            for (int i = 0; i < n; i++) {
                if (ordersData[i].startsWith(data.get("phoneNumber"))) {
                    ans.append(ordersData[i].split(":")[1]).append(", ").append(ordersData[i], ordersData[i].indexOf(",") + 2, ordersData[i].lastIndexOf(","));
                    if (i != n - 1) {
                        ans.append("\n");
                    }
                }
            }
            return ans.toString();
        }
        return "invalid";
    }

    String ClientOrdersHistoryFoodNames() {
        String[] ordersFoodNames = DataBase.getSingleTone().getController("ClientOrdersHistoryFoodNames").readFile().split("\n");
        if (!ordersFoodNames[0].equals("")) {
            String[] elementsSplit =
                    DataBase.getSingleTone().getController("RestaurantFoodDetails").readFile().split("\n");
            String[] getData = ClientActiveOrdersData().split("\n");
            StringBuilder ans = new StringBuilder();
            int len = ordersFoodNames.length;
            for (int counter = 0; counter < len; counter++) {
                if (ordersFoodNames[counter].startsWith(data.get("phoneNumber"))) {
                    String phoneNumber = "";
                    String clientID = ordersFoodNames[counter].split(":")[1];
                    for (String st : getData) {
                        if (st.startsWith(data.get("phoneNumber") + ":" + clientID)) {
                            phoneNumber = st.split(", ")[1];
                        }
                    }
                    String[] strings = ordersFoodNames[counter].split(", ");
                    int n = strings.length;
                    for (int i = 1; i < n - 1; i++) {
                        String elements = "invalidFood";
                        for (String s : elementsSplit) {
                            String[] test = s.split(":");
                            if (s.startsWith(phoneNumber) && !test[1].equals("All") && test[2].equals(strings[i])) {
                                elements = s;
                                break;
                            }
                        }
                        String[] splitElements = elements.split(", ")[0].split(":");
                        StringBuilder strBuilder = new StringBuilder(splitElements[1] + ", " + splitElements[2] + ", ");
                        strBuilder.append(elements, elements.indexOf(",") + 2, elements.lastIndexOf(","));
                        if (i != n - 2) {
                            strBuilder.append("+");
                        }
                        ans.append(strBuilder);
                    }
                    if (counter != len - 1) {
                        ans.append("\n");
                    }
                }
            }
            return ans.toString();
        }
        return "invalid";
    }

    String ClientOrdersHistoryNumbers() {
        String[] numbers = DataBase.getSingleTone().getController("ClientOrdersHistoryNumbers").readFile().split("\n");
        if (!numbers[0].equals("")) {
            StringBuilder ans = new StringBuilder();
            int n = numbers.length;
            for (int i = 0; i < n; i++) {
                if (numbers[i].startsWith(data.get("phoneNumber"))) {
                    ans.append(numbers[i], numbers[i].indexOf(",") + 2, numbers[i].lastIndexOf(","));
                    if (i != n - 1) {
                        ans.append("\n");
                    }
                }
            }
            return ans.toString();
        }
        return "invalid";
    }
}

//bool flag;
//    String randomID;
//    do {
//      flag = false;
//      randomID = '#' +
//          String.fromCharCode(
//              clientActiveOrderTile.clientName.codeUnitAt(0)) +
//          String.fromCharCode(
//              clientActiveOrderTile.clientLastName.codeUnitAt(0)) +
//          clientActiveOrderTile.clientPhoneNumber.substring(
//              clientActiveOrderTile.clientPhoneNumber.length - 4) +
//          '-' +
//          (Random().nextInt(8999) + 1000).toString();
//      for (int i = 0; i < getActiveOrdersLength(); i++) {
//        if (activeOrders[i].id == randomID) {
//          flag = true;
//          break;
//        }
//      }
//    } while (flag);
//    return randomID;
