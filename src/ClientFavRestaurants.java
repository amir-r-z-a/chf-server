import java.util.HashMap;

public class ClientFavRestaurants {
    private final HashMap<String, String> data;

    public ClientFavRestaurants(HashMap<String, String> data) {
        this.data = data;
    }

    public HashMap<String, String> getData() {
        return data;
    }

    String refreshFavRestaurants() {
        String[] favRestaurants = DataBase.getSingleTone().getController("ClientFavRestaurants").readFile().split("\n");
        StringBuilder ans = new StringBuilder();
        for (String str : favRestaurants) {
            if (str.startsWith(data.get("phoneNumber"))) {
                str = data.get("phoneNumber") + ":" + data.get("favRestaurants");
            }
            ans.append(str).append("\n");
        }
        DataBase.getSingleTone().getController("ClientFavRestaurants").writeFile(ans.toString(), true);
        return "valid";
    }
}
