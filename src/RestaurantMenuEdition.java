import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class RestaurantMenuEdition {
    private final HashMap<String, String> data;

    public RestaurantMenuEdition(HashMap<String, String> data) {
        this.data = data;
    }

    public HashMap<String, String> getData() {
        return data;
    }

    String addCategory() {
        String[] categories = DataBase.getSingleTone().getController("RestaurantCategories").readFile().split("\n");
        StringBuilder ans = new StringBuilder();
        for (String str : categories) {
            if (str.startsWith(data.get("phoneNumber"))) {
                List<String> update = new ArrayList<>(Arrays.asList(str.split(", ")));
                update.add(update.size() - 1, data.get("category"));
                StringBuilder strBuilder = new StringBuilder();
                int n = update.size();
                for (int i = 0; i < n - 1; i++) {
                    strBuilder.append(update.get(i)).append(", ");
                }
                strBuilder.append(update.get(n - 1));
                str = strBuilder.toString();
            }
            ans.append(str).append("\n");
        }
        DataBase.getSingleTone().getController("RestaurantCategories").writeFile(ans.toString(), true);
        DataBase.getSingleTone().getController("RestaurantFoodNames").writeFile(data.get("phoneNumber")
                + ":" + data.get("category") + ": {, }\n");
        return "valid";
    }

    String addFood() {
        String[] foods = DataBase.getSingleTone().getController("RestaurantFoodNames").readFile().split("\n");
        StringBuilder ans = new StringBuilder();
        for (String str : foods) {
            if (str.startsWith(data.get("phoneNumber") + ":" + "All") ||
                    str.startsWith(data.get("phoneNumber") + ":" + data.get("category"))) {
                List<String> update = new ArrayList<>(Arrays.asList(str.split(", ")));
                update.add(update.size() - 1, data.get("foodName"));
                StringBuilder strBuilder = new StringBuilder();
                int n = update.size();
                for (int i = 0; i < n - 1; i++) {
                    strBuilder.append(update.get(i)).append(", ");
                }
                strBuilder.append(update.get(n - 1));
                str = strBuilder.toString();
            }
            ans.append(str).append("\n");
        }
        DataBase.getSingleTone().getController("RestaurantFoodNames").writeFile(ans.toString(), true);
        return "valid-" + addFoodDetails();
    }

    String addFoodDetails() {
        DataBase.getSingleTone().getController("RestaurantFoodDetails")
                .writeFile(data.get("phoneNumber") + ":All:" + data.get("foodName")
                        + ": {, " + data.get("foodDesc") + ", " + data.get("foodPrice") + ", " + data.get("foodStatus")
                        + ", " + data.get("numOfOrder") + ", }\n");
        DataBase.getSingleTone().getController("RestaurantFoodDetails")
                .writeFile(data.get("phoneNumber") + ":" + data.get("category") + ":" + data.get("foodName")
                        + ": {, " + data.get("foodDesc") + ", " + data.get("foodPrice") + ", " + data.get("foodStatus")
                        + ", " + data.get("numOfOrder") + ", }\n");
        return "valid";
    }

    String getCategories() {
        String categories = DataBase.getSingleTone().getController("RestaurantCategories").getRow(data.get("phoneNumber"));
        return categories.substring(categories.indexOf(", ") + 1, categories.lastIndexOf(", "));
    }

    String getAllCategories() {
        String[] categories = DataBase.getSingleTone().getController("RestaurantCategories").readFile().split("\n");
        StringBuilder ans = new StringBuilder();
        for (String str : categories) {
            ans.append(str).append("\n");
        }
        return ans.toString();
    }

    String getMenu() {
        String[] menu = DataBase.getSingleTone().getController("RestaurantFoodDetails").readFile().split("\n");
        StringBuilder ans = new StringBuilder();
        for (String str : menu) {
            if (str.startsWith(data.get("phoneNumber"))) {
                ans.append(str).append("\n");
            }
        }
        return ans.toString();
    }

    String getAllMenu() {
        return DataBase.getSingleTone().getController("RestaurantFoodDetails").readFile();
    }

    String deleteFood() {
        String[] foods = DataBase.getSingleTone().getController("RestaurantFoodNames").readFile().split("\n");
        StringBuilder ans = new StringBuilder();
        for (String str : foods) {
            if (str.startsWith(data.get("phoneNumber") + ":" + data.get("category"))
                    || str.startsWith(data.get("phoneNumber") + ":All")) {
                List<String> update = new ArrayList<>(Arrays.asList(str.split(", ")));
                update.remove(data.get("foodName"));
                StringBuilder strBuilder = new StringBuilder();
                int n = update.size();
                for (int i = 0; i < n - 1; i++) {
                    strBuilder.append(update.get(i)).append(", ");
                }
                strBuilder.append(update.get(n - 1));
                str = strBuilder.toString();
            }
            ans.append(str).append("\n");
        }
        DataBase.getSingleTone().getController("RestaurantFoodNames").writeFile(ans.toString(), true);
        return "valid-" + deleteFoodDetails();
    }

    String deleteFoodDetails() {
        String[] foodDetails = DataBase.getSingleTone().getController("RestaurantFoodDetails").readFile().split("\n");
        StringBuilder ans = new StringBuilder();
        for (String str : foodDetails) {
            if (!(str.startsWith(data.get("phoneNumber") + ":" + data.get("category") + ":" + data.get("foodName"))
                    || str.startsWith(data.get("phoneNumber") + ":All:" + data.get("foodName")))) {
                ans.append(str).append("\n");
            }
        }
        DataBase.getSingleTone().getController("RestaurantFoodDetails").writeFile(ans.toString(), true);
        return "valid";
    }

    String editStatus() {
        String[] foodStatus = DataBase.getSingleTone().getController("RestaurantFoodDetails").readFile().split("\n");
        StringBuilder ans = new StringBuilder();
        for (String str : foodStatus) {
            if (str.startsWith(data.get("phoneNumber") + ":" + data.get("category") + ":" + data.get("foodName"))
                    || str.startsWith(data.get("phoneNumber") + ":All:" + data.get("foodName"))) {
                String[] update = str.split(", ");
                update[3] = data.get("newFoodStatus");
                StringBuilder strBuilder = new StringBuilder();
                int n = update.length;
                for (int i = 0; i < n - 1; i++) {
                    strBuilder.append(update[i]).append(", ");
                }
                strBuilder.append(update[n - 1]);
                str = strBuilder.toString();
            }
            ans.append(str).append("\n");
        }
        DataBase.getSingleTone().getController("RestaurantFoodDetails").writeFile(ans.toString(), true);
        return "valid";
    }

    String editOther() {
        String[] foodStatus = DataBase.getSingleTone().getController("RestaurantFoodDetails").readFile().split("\n");
        StringBuilder ans = new StringBuilder();
        for (String str : foodStatus) {
            if (str.startsWith(data.get("phoneNumber") + ":" + data.get("category") + ":" + data.get("foodName"))
                    || str.startsWith(data.get("phoneNumber") + ":All:" + data.get("foodName"))) {
                String[] update = str.split(", ");
                String[] newFoodName = update[0].split(":");
                newFoodName[2] = data.get("newFoodName");
                update[0] = newFoodName[0] + ":" + newFoodName[1] + ":" + newFoodName[2] + ":" + newFoodName[3];
                update[1] = data.get("newFoodDesc");
                update[2] = data.get("newFoodPrice");
                StringBuilder strBuilder = new StringBuilder();
                int n = update.length;
                for (int i = 0; i < n - 1; i++) {
                    strBuilder.append(update[i]).append(", ");
                }
                strBuilder.append(update[n - 1]);
                str = strBuilder.toString();
            }
            ans.append(str).append("\n");
        }
        DataBase.getSingleTone().getController("RestaurantFoodDetails").writeFile(ans.toString(), true);
        return "valid-" + editFoodNames();
    }

    String editFoodNames() {
        String[] foodNames = DataBase.getSingleTone().getController("RestaurantFoodNames").readFile().split("\n");
        StringBuilder ans = new StringBuilder();
        for (String str : foodNames) {
            if (str.startsWith(data.get("phoneNumber") + ":" + data.get("category"))
                    || str.startsWith(data.get("phoneNumber") + ":All")) {
                List<String> update = new ArrayList<>(Arrays.asList(str.split(", ")));
                update.set(update.indexOf(data.get("foodName")), data.get("newFoodName"));
                StringBuilder strBuilder = new StringBuilder();
                int n = update.size();
                for (int i = 0; i < n - 1; i++) {
                    strBuilder.append(update.get(i)).append(", ");
                }
                strBuilder.append(update.get(n - 1));
                str = strBuilder.toString();
            }
            ans.append(str).append("\n");
        }
        DataBase.getSingleTone().getController("RestaurantFoodNames").writeFile(ans.toString(), true);
        return "valid";
    }

//    String getLength() {
//        return (getCategories().split(", ").length - 2) + "";
//    }
}
