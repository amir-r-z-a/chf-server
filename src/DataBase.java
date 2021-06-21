import java.io.*;
import java.util.HashMap;

public class DataBase {
    private final HashMap<String, Controller> dataBase = new HashMap<>();
    static private DataBase singleTone;

    static public DataBase getSingleTone() {
        if (singleTone == null) {
            singleTone = new DataBase();
        }
        return singleTone;
    }

    void addDataBase(String str, Controller controller) {
        dataBase.put(str, controller);
    }

    Controller getController(String str) {
        return dataBase.get(str);
    }
}

class Controller {
    private final File file;
    private FileReader raf;
    private FileWriter fw;

    public Controller(String str) {
        file = new File(str);
        try {
            System.out.println(file.createNewFile());
            raf = new FileReader(file);
            String last = readFile();
            fw = new FileWriter(file);
            writeFile(last);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String readFile() {
        StringBuilder recovery = new StringBuilder();
        int i;
        try {
            while ((i = raf.read()) != -1) {
                recovery.append((char) i);
            }
//            raf=new FileReader(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return recovery.toString();
    }

    void writeFile(String str/*, boolean... flag*/) {
        System.out.println("str: " + str);
//        System.out.println("len: " + flag.length);
        try {
            fw.write(/*flag.length == 0 ? readFile() + str :*/ str);
//            fw.flush();
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

    void removeId(String id) {

    }
}


//import java.io.*;
//import java.util.HashMap;
//
//public class DataBase {
//    private final HashMap<String, Controller> dataBase = new HashMap<>();
//    static private DataBase singleTone;
//
//    static public DataBase getSingleTone() {
//        if (singleTone == null) {
//            singleTone = new DataBase();
//        }
//        return singleTone;
//    }
//
//    void addDataBase(String str, Controller controller) {
//        dataBase.put(str, controller);
//    }
//
//    Controller getController(String str) {
//        return dataBase.get(str);
//    }
//}
//
//class Controller {
//    private final File file;
//    private RandomAccessFile raf;
//    private FileWriter fw;
//
//    public Controller(String str) {
//        file = new File(str);
//        try {
//            System.out.println(file.createNewFile());
//            raf = new RandomAccessFile(file, "rw");
//            String last = readFile();
//            fw = new FileWriter(file);
//            writeFile(last);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    String readFile() {
//        StringBuilder recovery = new StringBuilder();
//        String i;
//        try {
//            while ((i = raf.readLine()) != null) {
//                recovery.append(i).append("\n");
//            }
//            raf.seek(0);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return recovery.toString();
//    }
//
//    void writeFile(String str/*, boolean... flag*/) {
//        System.out.println("str: " + str);
////        System.out.println("len: " + flag.length);
//        try {
//            fw.write(/*flag.length == 0 ? readFile() + str :*/ str);
//            fw.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    String getRow(String id) {
//        String[] split = this.readFile().split("\n");
//        for (String str : split) {
//            if (str.startsWith(id)) {
//                return str;
//            }
//        }
//        return "Could not found id: " + id;
//    }
//
//    void removeId(String id) {
//
//    }
//}