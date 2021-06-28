import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RefreshFile {
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

        File file10 = new File("D:\\Code\\DataBase\\Client\\ClientActiveOrdersData.txt");
        File file11 = new File("D:\\Code\\DataBase\\Client\\ClientActiveOrdersFoodNames.txt");
        File file12 = new File("D:\\Code\\DataBase\\Client\\ClientActiveOrdersNumbers.txt");
        File file13 = new File("D:\\Code\\DataBase\\Client\\ClientOrdersHistoryData.txt");
        File file14 = new File("D:\\Code\\DataBase\\Client\\ClientOrdersHistoryFoodNames.txt");
        File file15 = new File("D:\\Code\\DataBase\\Client\\ClientOrdersHistoryNumbers.txt");

        File file16 = new File("D:\\Code\\DataBase\\Restaurant\\RestaurantTopTenFoods.txt");

        File file17 = new File("D:\\Code\\DataBase\\Client\\ClientAccounts.txt");
        File file18 = new File("D:\\Code\\DataBase\\Client\\ClientFavRestaurants.txt");
        File file20 = new File("D:\\Code\\DataBase\\Client\\ClientAddresses.txt");
        File file19 = new File("D:\\Code\\DataBase\\Restaurant\\RestaurantComments.txt");
        File file21 = new File("D:\\Code\\DataBase\\Client\\ClientComments.txt");
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
            FileWriter fileWriter17 = new FileWriter(file17);
            FileWriter fileWriter18 = new FileWriter(file18);
            FileWriter fileWriter19 = new FileWriter(file19);
            FileWriter fileWriter20 = new FileWriter(file20);
            FileWriter fileWriter21 = new FileWriter(file21);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
