import org.apache.commons.codec.digest.Crypt;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Stream;

public class Crack {
    private final User[] users;
    private final String dictionary;

    public Crack(String shadowFile, String dictionary) throws FileNotFoundException {
        this.dictionary = dictionary;
        dictionary = "C:\\Users\\Aaron\\IdeaProjects\\Java-Lab-009\\resources\\englishSmall.dic";
        this.users = Crack.parseShadow(shadowFile);
    }

    public void crack(String dictPath) throws FileNotFoundException {
        Path filePath = Paths.get(dictPath);
        File myFile = filePath.toFile();
        FileReader fr = new FileReader(myFile);
        BufferedReader br = new BufferedReader(fr);
        User u = new User();
        String currentLine;
        try {
            while ((currentLine = br.readLine()) != null) {
                if (currentLine.contains("$")) {
                    String[] passArray = new String[]{currentLine};
                    String hash = Crypt.crypt(currentLine, u.getPassHash());
                    for (int i = 0; i < users.length; i++) {
                        if (Objects.equals(hash, u.getPassHash())) {
                            System.out.printf("Found password %s for user %s", hash, users);
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getLineCount(String path) {
        int lineCount = 0;
        try (Stream<String> stream = Files.lines(Path.of(path), StandardCharsets.UTF_8)) {
            lineCount = (int) stream.count();
        } catch (IOException ignored) {
        }
        return lineCount;
    }

    public static User[] parseShadow(String shadowFile) throws FileNotFoundException {
        String currentLine = String.valueOf(getLineCount(shadowFile));
        Path filePath = Paths.get(shadowFile);
        File myFile = filePath.toFile();
        FileReader fr = new FileReader(myFile);
        BufferedReader br = new BufferedReader(fr);
        try {
            while ((currentLine = br.readLine()) != null) {
                String[] arrOfStr = currentLine.split(":", 2);
                String[] users = Arrays.copyOf(arrOfStr, 2);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new User[0];
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Type the path to your shadow file: ");
        String shadowPath = sc.nextLine();
        System.out.print("Type the path to your dictionary file: ");
        String dictPath = sc.nextLine();
        Crack c = new Crack(shadowPath, dictPath);
        c.crack(c.dictionary);
    }
}
