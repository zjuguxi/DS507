import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;
import lec2assignment.App;

public class AppTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void testGuessCorrectly() {
        // 模拟用户输入
        ByteArrayInputStream in = new ByteArrayInputStream("apple\n".getBytes());
        System.setIn(in);

        App.main(new String[]{});

        // 验证输出是否包含 "Bingo" 和 "Congratulations"
        assertTrue(outContent.toString().contains("Bingo"));
        assertTrue(outContent.toString().contains("Congratulations"));
    }

    @Test
    void testExceedMaxAttempts() throws IOException {
        // 模拟用户输入
        ByteArrayInputStream in = new ByteArrayInputStream("incorrect\n".getBytes());
        System.setIn(in);

        // 检查是否有输入可供读取
        if (System.in.available() > 0) {
            App.main(new String[]{});
        }

        // 验证输出是否包含 "You have reached the maximum attempts!"
        assertTrue(outContent.toString().contains("You have reached the maximum attempts!"));
    }

    @Test
    void testGuessIncorrectly() {
        // 模拟用户输入
        ByteArrayInputStream in = new ByteArrayInputStream("incorrect1\nincorrect2\nincorrect3\nincorrect4\n".getBytes());
        System.setIn(in);

        App.main(new String[]{});

        // 验证输出是否包含 "Try it again!"
        assertTrue(outContent.toString().contains("Try it again!"));
    }
}