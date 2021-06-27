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
                + data.get("open") + ", " + data.get("close") + ", " + data.get("restaurantType")
                + ", null, null, null, null, 0, " + DataBase.restaurantCounter + ", }\n");
        DataBase.getSingleTone().getController("RestaurantCategories").writeFile(data.get("phoneNumber") + ": {, All, }\n");
        DataBase.getSingleTone().getController("RestaurantFoodNames").writeFile(data.get("phoneNumber") + ":All: {, }\n");
        DataBase.getSingleTone().getController("RestaurantTopTenFoods").writeFile(data.get("phoneNumber") + ": {, }\n");
        DataBase.restaurantCounter++;
        return "valid";
    }

    String signIn() {
        String alreadyPhoneNumber = alreadyPhoneNumber();
        if (alreadyPhoneNumber.equals("invalid")) {
            return "invalid";
        } else if (!alreadyPhoneNumber.split(", ")[2].equals(data.get("password"))) {
            return "invalid-match";
        }
        return "valid";
    }

    String getName() {
        return alreadyPhoneNumber().equals("invalid") ? "invalid" : alreadyPhoneNumber().split(", ")[1];
    }

    String getEmail() {
        return alreadyPhoneNumber().equals("invalid") ? "invalid" : alreadyPhoneNumber().split(", ")[6];
    }

    String getAddress() {
        return alreadyPhoneNumber().equals("invalid") ? "invalid" : alreadyPhoneNumber().split(", ")[7];
    }

    String getLatLang() {
        return alreadyPhoneNumber().equals("invalid") ? "invalid" : alreadyPhoneNumber().split(", ")[8];
    }

    String getRadius() {
        return alreadyPhoneNumber().equals("invalid") ? "invalid" : alreadyPhoneNumber().split(", ")[9];
    }

    String getAccount() {
        return DataBase.getSingleTone().getController("RestaurantAccounts").getRow(data.get("phoneNumber"));
    }

    String getAccounts() {
        return DataBase.getSingleTone().getController("RestaurantAccounts").readFile();
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

    String editPhoneNumber() {
        String phoneNumber = data.get("phoneNumber");
        data.put("phoneNumber", data.get("newPhoneNumber"));
        if (!alreadyPhoneNumber().equals("invalid")) {
            return "invalid";
        }
        data.put("phoneNumber", phoneNumber);
        String users = DataBase.getSingleTone().getController("RestaurantAccounts").readFile();
        String[] split = users.split("\n");
        StringBuilder ans = new StringBuilder();
        for (String str : split) {
            if (str.startsWith(data.get("phoneNumber"))) {
                StringBuilder strBuilder = new StringBuilder(str);
                strBuilder.replace(0, str.indexOf(":"), data.get("newPhoneNumber"));
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
        String returnValue = "";
        returnValue += !alreadyPhoneNumber().split(", ")[2].equals(data.get("oldPassword")) ?
                "invalid-" : "valid-";
        returnValue += !data.get("newPassword").equals(data.get("confirmPassword")) ? "invalid" : "valid";
        if (returnValue.equals("valid-valid")) {
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
        }
        return returnValue;
    }

    String editAddress() {
        String users = DataBase.getSingleTone().getController("RestaurantAccounts").readFile();
        String[] split = users.split("\n");
        StringBuilder ans = new StringBuilder();
        for (String str : split) {
            if (str.startsWith(data.get("phoneNumber"))) {
                String[] update = str.split(", ");
                update[7] = data.get("address");
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

    String editLatLang() {
        String users = DataBase.getSingleTone().getController("RestaurantAccounts").readFile();
        String[] split = users.split("\n");
        StringBuilder ans = new StringBuilder();
        for (String str : split) {
            if (str.startsWith(data.get("phoneNumber"))) {
                String[] update = str.split(", ");
                update[8] = data.get("latLang");
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

    String editRadius() {
        String users = DataBase.getSingleTone().getController("RestaurantAccounts").readFile();
        String[] split = users.split("\n");
        StringBuilder ans = new StringBuilder();
        for (String str : split) {
            if (str.startsWith(data.get("phoneNumber"))) {
                String[] update = str.split(", ");
                update[9] = data.get("radius");
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
