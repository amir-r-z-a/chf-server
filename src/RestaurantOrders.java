import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class RestaurantOrders {
    private final HashMap<String, String> data;

    public RestaurantOrders(HashMap<String, String> data) {
        this.data = data;
    }

    public HashMap<String, String> getData() {
        return data;
    }

    String RestaurantActiveOrdersData() {
        String[] ordersData = DataBase.getSingleTone().getController("RestaurantActiveOrdersData").readFile().split("\n");
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

    String RestaurantActiveOrdersFoodNames() {
        String[] ordersFoodNames = DataBase.getSingleTone().getController("RestaurantActiveOrdersFoodNames").readFile().split("\n");
        if (!ordersFoodNames[0].equals("")) {
            String[] elementsSplit =
                    DataBase.getSingleTone().getController("RestaurantFoodDetails").readFile().split("\n");
            StringBuilder ans = new StringBuilder();
            int len = ordersFoodNames.length;
            for (int counter = 0; counter < len; counter++) {
                if (ordersFoodNames[counter].startsWith(data.get("phoneNumber"))) {
                    String[] strings = ordersFoodNames[counter].split(", ");
                    int n = strings.length;
                    for (int i = 1; i < n - 1; i++) {
                        String elements = "invalidFood";
                        for (String s : elementsSplit) {
                            String[] test = s.split(":");
                            if (s.startsWith(data.get("phoneNumber")) && !test[1].equals("All") && test[2].equals(strings[i])) {
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

    String RestaurantActiveOrdersNumbers() {
        String[] numbers = DataBase.getSingleTone().getController("RestaurantActiveOrdersNumbers").readFile().split("\n");
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

    String RestaurantFinishedOrders() {
        String[] ordersData = DataBase.getSingleTone().getController("RestaurantActiveOrdersData").readFile().split("\n");
        String[] foodNames = DataBase.getSingleTone().getController("RestaurantActiveOrdersFoodNames").readFile().split("\n");
        String[] numbers = DataBase.getSingleTone().getController("RestaurantActiveOrdersNumbers").readFile().split("\n");
        DataBase.getSingleTone().getController("RestaurantActiveOrdersData").writeFile("", true);
        DataBase.getSingleTone().getController("RestaurantActiveOrdersFoodNames").writeFile("", true);
        DataBase.getSingleTone().getController("RestaurantActiveOrdersNumbers").writeFile("", true);

        String[] clientOrdersData = DataBase.getSingleTone().getController("ClientActiveOrdersData").readFile().split("\n");
        String[] clientFoodNames = DataBase.getSingleTone().getController("ClientActiveOrdersFoodNames").readFile().split("\n");
        String[] clientNumbers = DataBase.getSingleTone().getController("ClientActiveOrdersNumbers").readFile().split("\n");
        DataBase.getSingleTone().getController("ClientActiveOrdersData").writeFile("", true);
        DataBase.getSingleTone().getController("ClientActiveOrdersFoodNames").writeFile("", true);
        DataBase.getSingleTone().getController("ClientActiveOrdersNumbers").writeFile("", true);
        for (int i = 0; i < ordersData.length; i++) {
            if (ordersData[i].startsWith(data.get("phoneNumber") + ":" + data.get("orderID"))) {
                DataBase.getSingleTone().getController("RestaurantOrdersHistoryData").writeFile(ordersData[i] + "\n");
                DataBase.getSingleTone().getController("RestaurantOrdersHistoryFoodNames").writeFile(foodNames[i] + "\n");
                DataBase.getSingleTone().getController("RestaurantOrdersHistoryNumbers").writeFile(numbers[i] + "\n");
            } else {
                DataBase.getSingleTone().getController("RestaurantActiveOrdersData").writeFile(ordersData[i] + "\n");
                DataBase.getSingleTone().getController("RestaurantActiveOrdersFoodNames").writeFile(foodNames[i] + "\n");
                DataBase.getSingleTone().getController("RestaurantActiveOrdersNumbers").writeFile(numbers[i] + "\n");
            }

            if (clientOrdersData[i].split(":")[1].equals(data.get("orderID"))) {
                DataBase.getSingleTone().getController("ClientOrdersHistoryData").writeFile(clientOrdersData[i] + "\n");
                DataBase.getSingleTone().getController("ClientOrdersHistoryFoodNames").writeFile(clientFoodNames[i] + "\n");
                DataBase.getSingleTone().getController("ClientOrdersHistoryNumbers").writeFile(clientNumbers[i] + "\n");
            } else {
                DataBase.getSingleTone().getController("ClientActiveOrdersData").writeFile(clientOrdersData[i] + "\n");
                DataBase.getSingleTone().getController("ClientActiveOrdersFoodNames").writeFile(clientFoodNames[i] + "\n");
                DataBase.getSingleTone().getController("ClientActiveOrdersNumbers").writeFile(clientNumbers[i] + "\n");
            }
        }
        return "valid";
    }

    String RestaurantOrdersHistoryData() {
        String[] ordersData = DataBase.getSingleTone().getController("RestaurantOrdersHistoryData").readFile().split("\n");
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

    String RestaurantOrdersHistoryFoodNames() {
        String[] ordersFoodNames = DataBase.getSingleTone().getController("RestaurantOrdersHistoryFoodNames").readFile().split("\n");
        if (!ordersFoodNames[0].equals("")) {
            String[] elementsSplit =
                    DataBase.getSingleTone().getController("RestaurantFoodDetails").readFile().split("\n");
            StringBuilder ans = new StringBuilder();
            int len = ordersFoodNames.length;
            for (int counter = 0; counter < len; counter++) {
                if (ordersFoodNames[counter].startsWith(data.get("phoneNumber"))) {
                    String[] strings = ordersFoodNames[counter].split(", ");
                    int n = strings.length;
                    for (int i = 1; i < n - 1; i++) {
                        String elements = "invalidFood";
                        for (String s : elementsSplit) {
                            String[] test = s.split(":");
                            if (s.startsWith(data.get("phoneNumber")) && !test[1].equals("All") && test[2].equals(strings[i])) {
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

    String RestaurantOrdersHistoryNumbers() {
        String[] numbers = DataBase.getSingleTone().getController("RestaurantOrdersHistoryNumbers").readFile().split("\n");
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
