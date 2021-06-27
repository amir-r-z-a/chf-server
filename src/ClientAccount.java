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
        DataBase.getSingleTone().getController("ClientCommentsQuestion").writeFile(data.get("phoneNumber") + ": {, }\n");
        DataBase.getSingleTone().getController("ClientCommentsAnswer").writeFile(data.get("phoneNumber") + ": {, }\n");
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

    String getClientCommentsQuestion() {
        return DataBase.getSingleTone().getController("ClientCommentsQuestion").getRow(data.get("phoneNumber"));
    }

    String getClientCommentsAnswer() {
        return DataBase.getSingleTone().getController("ClientCommentsAnswer").getRow(data.get("phoneNumber"));
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
}
