import java.util.HashMap;

public class RestaurantAccount {
    private final HashMap<String, String> data;

    public RestaurantAccount(HashMap<String, String> data) {
        this.data = data;
    }

    public HashMap<String, String> getData() {
        return data;
    }

    String alreadyPhoneNumber() {
        String users = DataBase.getSingleTone().getController("RestaurantSignUp").readFile();
        String[] split = users.split("\n");
        for (String str : split) {
            if (str.startsWith(data.get("phoneNumber"))) {
                return "invalid";
            }
        }
        DataBase.getSingleTone().getController("RestaurantSignUp").writeFile(data.get("phoneNumber")
                + ": {" + data.get("name") + ", " + data.get("phoneNumber") + ", " + data.get("password") + ", "
                + data.get("openHour") + ", " + data.get("closeHour") + ", " + data.get("restaurantType") + "}\n");
        return "valid";
    }

    String foundPhoneNumber() {
        return null;
    }

    String foundPassword() {
        return null;
    }
}
