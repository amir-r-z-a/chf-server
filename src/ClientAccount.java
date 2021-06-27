import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ClientAccount {
    public static void main(String[] args) {
        File file = new File("D:\\Code\\DataBase\\Restaurant\\RestaurantAccounts.txt");
        File file1 = new File("D:\\Code\\DataBase\\Restaurant\\RestaurantCategories.txt");
        File file2 = new File("D:\\Code\\DataBase\\Restaurant\\RestaurantFoodNames.txt");
        File file3 = new File("D:\\Code\\DataBase\\Restaurant\\RestaurantFoodDetails.txt");

        File file4 = new File("D:\\Code\\DataBase\\Restaurant\\RestaurantActiveOrdersData.txt");
        File file5 = new File("D:\\Code\\DataBase\\Restaurant\\RestaurantActiveOrdersFoodNames.txt");
        File file6 = new File("D:\\Code\\DataBase\\Restaurant\\RestaurantActiveOrdersNumbers.txt");
        File file7 = new File("D:\\Code\\DataBase\\Restaurant\\RestaurantOrdersHistoryData.txt");
        File file8 = new File("D:\\Code\\DataBase\\Restaurant\\RestaurantOrdersHistoryFoodNames.txt");
        File file9 = new File("D:\\Code\\DataBase\\Restaurant\\RestaurantOrdersHistoryNumbers.txt");

        File file10 = new File("D:\\Code\\DataBase\\Restaurant\\ClientActiveOrdersData.txt");
        File file11 = new File("D:\\Code\\DataBase\\Restaurant\\ClientActiveOrdersFoodNames.txt");
        File file12 = new File("D:\\Code\\DataBase\\Restaurant\\ClientActiveOrdersNumbers.txt");
        File file13 = new File("D:\\Code\\DataBase\\Restaurant\\ClientOrdersHistoryData.txt");
        File file14 = new File("D:\\Code\\DataBase\\Restaurant\\ClientOrdersHistoryFoodNames.txt");
        File file15 = new File("D:\\Code\\DataBase\\Restaurant\\ClientOrdersHistoryNumbers.txt");

        File file16 = new File("D:\\Code\\DataBase\\Restaurant\\RestaurantTopTenFoods.txt");
        try {
            FileWriter fileWriter = new FileWriter(file);
            FileWriter fileWriter1 = new FileWriter(file1);
            FileWriter fileWriter2 = new FileWriter(file2);
            FileWriter fileWriter3 = new FileWriter(file3);

            FileWriter fileWriter4 = new FileWriter(file4);
            FileWriter fileWriter5 = new FileWriter(file5);
            FileWriter fileWriter6 = new FileWriter(file6);
            FileWriter fileWriter7 = new FileWriter(file7);
            FileWriter fileWriter8 = new FileWriter(file8);
            FileWriter fileWriter9 = new FileWriter(file9);

            FileWriter fileWriter10 = new FileWriter(file10);
            FileWriter fileWriter11 = new FileWriter(file11);
            FileWriter fileWriter12 = new FileWriter(file12);
            FileWriter fileWriter13 = new FileWriter(file13);
            FileWriter fileWriter14 = new FileWriter(file14);
            FileWriter fileWriter15 = new FileWriter(file15);
            FileWriter fileWriter16 = new FileWriter(file16);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
