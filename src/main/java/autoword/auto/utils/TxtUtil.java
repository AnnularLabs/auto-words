package autoword.auto.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TxtUtil {

    /**
     * 获取TXT文本中的所有单词
     * @param targetFilePath TXT目标路径
     * @return
     */
    public static Map<String, Integer> getTxtContainsWords(String targetFilePath) {

        Path path = Paths.get(targetFilePath);
        Map<String, Integer> TxtWords = new HashMap<>();

        if (Files.exists(path)) {
            try {
                // txt格式为UTF_8
                List<String> words = Files.readAllLines(path, StandardCharsets.UTF_8);

                for (String word : words) {
                    TxtWords.put(word, 1);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return TxtWords;
    }

}
