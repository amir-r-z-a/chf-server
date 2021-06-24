import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class RestaurantCategories {
    private final HashMap<String, String> data;

    public RestaurantCategories(HashMap<String, String> data) {
        this.data = data;
    }

    public HashMap<String, String> getData() {
        return data;
    }

    String addCategories() {
        List<String> update = Arrays.asList(getCategories().split(", "));
        update.add(update.size() - 1, data.get("category"));
        StringBuilder ans = new StringBuilder();
        int n = update.size();
        for (int i = 0; i < n - 1; i++) {
            ans.append(update.get(i)).append(", ");
        }
        ans.append(update.get(update.size() - 1));
        DataBase.getSingleTone().getController("RestaurantCategories").writeFile(ans.toString(), true);
        return "valid";
    }

    String getCategories() {
        return DataBase.getSingleTone().getController("RestaurantCategories").getRow(data.get("phoneNumber"));
    }

    String getLength() {
        return (getCategories().split(", ").length - 2) + "";
    }
}
