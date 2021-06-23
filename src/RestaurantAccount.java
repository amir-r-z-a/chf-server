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
        String users = DataBase.getSingleTone().getController("RestaurantAccounts").readFile();
        String[] split = users.split("\n");
        for (String str : split) {
            if (str.startsWith(data.get("phoneNumber"))) {
                return str;
            }
        }
        return "invalid";
    }

    String signUp() {
        DataBase.getSingleTone().getController("RestaurantAccounts").writeFile(data.get("phoneNumber")
                + ": {, " + data.get("name") + ", " + data.get("password") + ", "
                + data.get("open") + ", " + data.get("close") + ", " + data.get("restaurantType") + ", null, " + "}\n");
        return "valid";
    }

    String signIn() {
        String alreadyPhoneNumber = alreadyPhoneNumber();
        if (alreadyPhoneNumber.equals("invalid")
                || !alreadyPhoneNumber.split(", ")[2].equals(data.get("password"))) {
            return "invalid";
        }
        return "valid";
    }

    String editName() {
        String users = DataBase.getSingleTone().getController("RestaurantAccounts").readFile();
        String[] split = users.split("\n");
        StringBuilder ans = new StringBuilder();
        for (String str : split) {
            if (str.startsWith(data.get("phoneNumber"))) {
                String[] update = str.split(", ");
                update[1] = data.get("newName");
                StringBuilder strBuilder = new StringBuilder(update[0] + ", ");
                int n = update.length;
                for (int i = 1; i < n - 1; i++) {
                    strBuilder.append(update[i]).append(", ");
                }
                strBuilder.append(update[n - 1]);
                str = strBuilder.toString();
            }
            ans.append(str).append("\n");
        }
        DataBase.getSingleTone().getController("RestaurantAccounts").writeFile(ans.toString(), true);
        return "valid";
    }

    String editEmail() {
        String users = DataBase.getSingleTone().getController("RestaurantAccounts").readFile();
        String[] split = users.split("\n");
        StringBuilder ans = new StringBuilder();
        for (String str : split) {
            if (str.startsWith(data.get("phoneNumber"))) {
                String[] update = str.split(", ");
                update[6] = data.get("newEmail");
                StringBuilder strBuilder = new StringBuilder(update[0] + ", ");
                int n = update.length;
                for (int i = 1; i < n - 1; i++) {
                    strBuilder.append(update[i]).append(", ");
                }
                strBuilder.append(update[n - 1]);
                str = strBuilder.toString();
            }
            ans.append(str).append("\n");
        }
        DataBase.getSingleTone().getController("RestaurantAccounts").writeFile(ans.toString(), true);
        return "valid";
    }

    String editPassword() {
        if (!alreadyPhoneNumber().split(", ")[2].equals(data.get("oldPassword"))) {
            return "invalid";
        }
        String users = DataBase.getSingleTone().getController("RestaurantAccounts").readFile();
        String[] split = users.split("\n");
        StringBuilder ans = new StringBuilder();
        for (String str : split) {
            if (str.startsWith(data.get("phoneNumber"))) {
                String[] update = str.split(", ");
                update[2] = data.get("newPassword");
                StringBuilder strBuilder = new StringBuilder(update[0] + ", ");
                int n = update.length;
                for (int i = 1; i < n - 1; i++) {
                    strBuilder.append(update[i]).append(", ");
                }
                strBuilder.append(update[n - 1]);
                str = strBuilder.toString();
            }
            ans.append(str).append("\n");
        }
        DataBase.getSingleTone().getController("RestaurantAccounts").writeFile(ans.toString(), true);
        return "valid";
    }
}
