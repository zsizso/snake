package accounting;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class AccountManager implements Serializable {
    private static final String FILENAME = "bests.ser";
    private static final String FILENOTFOUND = "File not found";
    private static final String IOEXCEPTION = "File is empty or there is a file io problem";
    private HashMap<String, Gamer> bestTen;

    public void addOne(Gamer g) {
        bestTen.put(g.getName(), g);
    }

    public Gamer getGamer(Gamer gmr) {
        Gamer gamer = bestTen.get(gmr.getName());
        if (gamer == null) {
            addOne(gmr);
            gamer = gmr;
        }
        return gamer;
    }

    public List<Gamer> sorted() {
        return bestTen
                .values()
                .stream()
                .sorted(Comparator.comparing(Gamer::getHighScore).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    public void save() {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        List<Gamer> list = sorted();
        try {
            fos = new FileOutputStream(FILENAME);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
        } catch (FileNotFoundException e) {
            System.out.println(FILENOTFOUND);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void load() {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            if (!Files.exists(Paths.get(FILENAME))) {
                Files.createFile(Paths.get(FILENAME));
            }
            bestTen = new HashMap<>();
            fis = new FileInputStream(FILENAME);
            ois = new ObjectInputStream(fis);
            List<Gamer> l = (List<Gamer>) ois.readObject();
            for (Gamer g : l) {
                bestTen.put(g.getName(), g);
            }
        } catch (FileNotFoundException e) {
            System.out.println(FILENOTFOUND);
        } catch (IOException e) {
            System.out.println(IOEXCEPTION);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
