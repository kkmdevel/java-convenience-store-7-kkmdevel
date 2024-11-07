package store.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class DataReaderTest {

    private File tempFile;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = File.createTempFile("test", ".txt");
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("첫 번째 줄\n");
            writer.write("두 번째 줄\n");
            writer.write("세 번째 줄\n");
        }
    }

    @AfterEach
    void tearDown() {
        if (tempFile != null && tempFile.exists()) {
            boolean deleted = tempFile.delete();
            if (!deleted) {
                System.err.println("Fail"+tempFile.getAbsolutePath());
            }
        }
    }

    @Test
    @DisplayName("파일의 특정 줄 읽기 테스트")
    void testReadLineFromFileData() {
        String filePath = tempFile.getAbsolutePath();
        Optional<String> line = DataReader.readLineFromFileData(filePath, 2);
        assertTrue(line.isPresent());
        assertEquals("두 번째 줄", line.get());
    }

    @Test
    @DisplayName("존재하지 않는 줄 읽기 테스트")
    void testReadLineFromFileData_NonExistentLine() {
        String filePath = tempFile.getAbsolutePath();
        Optional<String> line = DataReader.readLineFromFileData(filePath, 10);
        assertFalse(line.isPresent());
    }

    @Test
    @DisplayName("잘못된 파일 경로 테스트")
    void testReadLineFromFileData_InvalidPath() {
        Optional<String> line = DataReader.readLineFromFileData("잘못된경로.txt", 1);
        assertFalse(line.isPresent());
    }
}
