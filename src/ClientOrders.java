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
