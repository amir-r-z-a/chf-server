import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class DataBase {
    private final HashMap<String, Table> dataBase = new HashMap<>();
    static private DataBase singleTone;

    static public DataBase getSingleTone() {
        if (singleTone == null) {
            singleTone = new DataBase();
        }
        return singleTone;
    }

    void addDataBase(String str, Table table) {
        dataBase.put(str, table);
    }

    Table getController(String string) {
        return dataBase.get(string);
    }
}

class Table {
    File file;
    FileReader fileReader;
    FileWriter fileWriter;

    public Table(String directory) {
        this.file = new File(directory);
        try {
            this.fileReader = new FileReader(file);
            this.fileWriter = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String readFile() {
        StringBuilder recovery = new StringBuilder("");
        int i;
        try {
            while ((i = fileReader.read()) != -1) {
                recovery.append((char) i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.valueOf(recovery);
    }

    void writeFile(String str) {
        StringBuilder stringBuilder = new StringBuilder(this.readFile());
        try {
            fileWriter.write(String.valueOf(stringBuilder.append(str)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String getRow(String id) {
        String[] split = this.readFile().split("\n");
        for (String str : split) {
            if (str.startsWith(id)) {
                return str;
            }
        }
        return "Could not found id: " + id;
    }
}
