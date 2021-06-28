import java.util.HashMap;

public class Comments {
    private final HashMap<String, String> data;

    public Comments(HashMap<String, String> data) {
        this.data = data;
    }

    public HashMap<String, String> getData() {
        return data;
    }

    String addComment() {
        String id = commentsIDGenerator();
        DataBase.getSingleTone().getController("ClientComments")
                .writeFile(data.get("phoneNumber") + ":" + id + ":" + " {, " + data.get("question") + ", " + data.get("answer")
                        + ", " + data.get("foodName") + ", " + data.get("PhoneNumberRest") + ", " + data.get("date") + ", }");
        DataBase.getSingleTone().getController("RestaurantComments")
                .writeFile(data.get("PhoneNumberRest") + ":" + id + ":" + " {, " + data.get("question") + ", " + data.get("answer")
                        + ", " + data.get("foodName") + ", " + data.get("phoneNumber") + ", " + data.get("date") + ", }");
        return "valid";
    }

    String RestaurantComments() {
        String[] restaurantComments = DataBase.getSingleTone().getController("RestaurantComments").readFile().split("\n");
        StringBuilder ans = new StringBuilder();
        for (String str : restaurantComments) {
            if (str.startsWith(data.get("phoneNumber"))) {
                ans.append(str).append("\n");
            }
        }
        return ans.toString();
    }

    String commentsIDGenerator() {
        String[] comments = DataBase.getSingleTone().getController("ClientComments").readFile().split("\n");
        boolean flag;
        String randomID;
        do {
            flag = false;
            boolean key = false;
            randomID = '#' + data.get("foodName").charAt(0) +
                    data.get("phoneNumber")
                            .substring(data.get("phoneNumber").length() - 4) +
                    '-' +
                    ((int) (Math.random() * 89 + 10));
            for (String str : comments) {
                if (!str.equals("") && str.substring(str.indexOf(":") + 1, str.lastIndexOf(":")).equals(randomID)) {
                    flag = true;
                    break;
                }
            }
        } while (flag);
        return randomID;
    }

    String reply() {
        String[] restaurantComments = DataBase.getSingleTone().getController("RestaurantComments").readFile().split("\n");
        String[] clientComments = DataBase.getSingleTone().getController("ClientComments").readFile().split("\n");
        StringBuilder ans = new StringBuilder();
        String clientPhoneNumber = "";
        for (String str : restaurantComments) {
            if (str.startsWith(data.get("phoneNumber") + ":" + data.get("id"))) {
                String[] strings = str.split(", ");
                clientPhoneNumber = strings[4];
                StringBuilder stringBuilder = new StringBuilder();
                strings[2] = data.get("newAnswer");
                int n = strings.length;
                for (int i = 0; i < n - 1; i++) {
                    stringBuilder.append(strings[i]).append(", ");
                }
                stringBuilder.append(strings[n - 1]);
                str = stringBuilder.toString();
            }
            ans.append(str).append("\n");
        }
        System.out.println("client is: " + clientPhoneNumber);
        DataBase.getSingleTone().getController("RestaurantComments").writeFile(ans.toString(), true);
        StringBuilder clientAns = new StringBuilder();
        for (String str : clientComments) {
            if (str.startsWith(clientPhoneNumber + ":" + data.get("id"))) {
                String[] strings = str.split(", ");
                StringBuilder stringBuilder = new StringBuilder();
                strings[2] = data.get("newAnswer");
                int n = strings.length;
                for (int i = 0; i < n - 1; i++) {
                    stringBuilder.append(strings[i]).append(", ");
                }
                stringBuilder.append(strings[n - 1]);
                str = stringBuilder.toString();
            }
            clientAns.append(str).append("\n");
        }
        DataBase.getSingleTone().getController("ClientComments").writeFile(clientAns.toString(), true);
        return "valid";
    }
}
