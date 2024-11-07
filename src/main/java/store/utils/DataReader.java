package store.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

public class DataReader {
    public static Optional<String> readLineFromFileData(String filePath, int lineNumber) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            return br.lines().skip(lineNumber - 1).findFirst();
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
