import java.util.HashMap;

public class RestaurantTopTenFoods {
    private final HashMap<String, String> data;

    public RestaurantTopTenFoods(HashMap<String, String> data) {
        this.data = data;
    }

    public HashMap<String, String> getData() {
        return data;
    }

    String refreshTopTenFoods() {
        String[] topTenFoods = DataBase.getSingleTone().getController("RestaurantTopTenFoods").readFile().split("\n");
        DataBase.getSingleTone().getController("RestaurantTopTenFoods").writeFile("", true);
        StringBuilder ans = new StringBuilder();
        for (String str : topTenFoods) {
            if (str.startsWith(data.get("phoneNumber"))) {
                str = data.get("phoneNumber") + ":" + data.get("topTenFoods");
            }
            ans.append(str).append("\n");
        }
        DataBase.getSingleTone().getController("RestaurantTopTenFoods").writeFile(ans.toString(), true);
        return "valid";
    }
}
