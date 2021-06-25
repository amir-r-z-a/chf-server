import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ClientAccount {
    public static void main(String[] args) {
//        System.out.println("Restaurant-RestaurantSignUp-arman-09198612878-Arman123-10-21-FastFood".length());
//        System.out.println("Restaurant-RestaurantSignIn-09198612878-Arman123".length());
//        System.out.println("Restaurant-EditProfile-name-09198612878-ghazal".length());
//        System.out.println("Restaurant-EditProfile-password-09198612877-Arman123-Amirreza123".length());
//        System.out.println("Restaurant-EditProfile-email-09198612878-armanheids@gmail.com".length());
        File file = new File("D:\\Code\\DataBase\\Restaurant\\RestaurantAccounts.txt");
        File file1 = new File("D:\\Code\\DataBase\\Restaurant\\RestaurantCategories.txt");
        File file2 = new File("D:\\Code\\DataBase\\Restaurant\\RestaurantFoodNames.txt");
        File file3 = new File("D:\\Code\\DataBase\\Restaurant\\RestaurantFoodDetails.txt");
        try {
            FileWriter fileWriter = new FileWriter(file);
            FileWriter fileWriter1 = new FileWriter(file1);
            FileWriter fileWriter2 = new FileWriter(file2);
            FileWriter fileWriter3 = new FileWriter(file3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
