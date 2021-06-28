import java.util.HashMap;

public class ClientAccount {
    private final HashMap<String, String> data;

    public ClientAccount(HashMap<String, String> data) {
        this.data = data;
    }

    public HashMap<String, String> getData() {
        return data;
    }

    String alreadyPhoneNumber() {
        String users = DataBase.getSingleTone().getController("ClientAccounts").readFile();
        String[] split = users.split("\n");
        for (String str : split) {
            if (str.startsWith(data.get("phoneNumber"))) {
                return str;
            }
        }
        return "invalid";
    }

    String signUp() {
        DataBase.getSingleTone().getController("ClientAccounts").writeFile(data.get("phoneNumber")
                + ": {, " + data.get("name") + ", " + data.get("password") + ", null, null, null, null, null, }\n");
        DataBase.getSingleTone().getController("ClientFavRestaurants").writeFile(data.get("phoneNumber") + ": {, }\n");
//        addresses
//        faves
        DataBase.clientCounter++;
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

    String getAccount() {
        return DataBase.getSingleTone().getController("ClientAccounts").getRow(data.get("phoneNumber"));
    }

    String getFavRestaurants() {
        String favRestaurants = DataBase.getSingleTone().getController("ClientFavRestaurants").getRow(data.get("phoneNumber"));
        return favRestaurants.equals(data.get("phoneNumber") + ": {, }") ? "null" :
                favRestaurants.substring(favRestaurants.indexOf(",") + 2, favRestaurants.lastIndexOf(","));
    }

    String getComments() {
        String[] comments = DataBase.getSingleTone().getController("ClientComments").readFile().split("\n");
        StringBuilder ans = new StringBuilder();
        for (String str : comments) {
            if (str.startsWith(data.get("phoneNumber"))) {
                ans.append(str).append("\n");
            }
        }
        return ans.toString();
    }

    String addAddress() {
        DataBase.getSingleTone().getController("ClientAddresses").writeFile(data.get("phoneNumber") + ":" + data.get("newAddress"));
        return "valid";
    }

    String deleteAddress() {
        String[] addresses = DataBase.getSingleTone().getController("ClientAddresses").readFile().split("\n");
        StringBuilder ans = new StringBuilder();
        for (String str : addresses) {
            if (!str.startsWith(data.get("phoneNumber")) || !str.substring(str.indexOf(":") + 1).equals(data.get("address"))) {
                ans.append(str).append("\n");
            }
        }
        DataBase.getSingleTone().getController("ClientAddresses").writeFile(ans.toString(), true);
        return "valid";
    }

    String editName() {
        String users = DataBase.getSingleTone().getController("ClientAccounts").readFile();
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
        DataBase.getSingleTone().getController("ClientAccounts").writeFile(ans.toString(), true);
        return "valid";
    }

    String editPhoneNumber() {
        String phoneNumber = data.get("phoneNumber");
        data.put("phoneNumber", data.get("newPhoneNumber"));
        if (!alreadyPhoneNumber().equals("invalid")) {
            return "invalid";
        }
        data.put("phoneNumber", phoneNumber);
        String users = DataBase.getSingleTone().getController("ClientAccounts").readFile();
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
        DataBase.getSingleTone().getController("ClientAccounts").writeFile(ans.toString(), true);
        return "valid";
    }

    String editEmail() {
        String users = DataBase.getSingleTone().getController("ClientAccounts").readFile();
        String[] split = users.split("\n");
        StringBuilder ans = new StringBuilder();
        for (String str : split) {
            if (str.startsWith(data.get("phoneNumber"))) {
                String[] update = str.split(", ");
                update[3] = data.get("newEmail");
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
        DataBase.getSingleTone().getController("ClientAccounts").writeFile(ans.toString(), true);
        return "valid";
    }

    String editPassword() {
        String returnValue = "";
        returnValue += !alreadyPhoneNumber().split(", ")[2].equals(data.get("oldPassword")) ?
                "invalid-" : "valid-";
        returnValue += !data.get("newPassword").equals(data.get("confirmPassword")) ? "invalid" : "valid";
        if (returnValue.equals("valid-valid")) {
            String users = DataBase.getSingleTone().getController("ClientAccounts").readFile();
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
            DataBase.getSingleTone().getController("ClientAccounts").writeFile(ans.toString(), true);
        }
        return returnValue;
    }
}
