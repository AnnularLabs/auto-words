package com.t.autoword.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * ClassName: TextUtil
 * Description:
 *
 * @Author agility6
 * @Create 2024/1/28 19:16
 * @Version: 1.0
 */
public class TextUtil {


    /**
     * 通过目标路径获取已经存在的单词
     * @param targetFilePath
     * @return 返回Map集合，存在：1 不存在：0
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

    /**
     * 将提取出的单词写入目标文件中
     * @param targetFilePath
     * @param words
     */
    public static void writerFile(String targetFilePath, Queue<String> words) {
        File output = new File(targetFilePath);
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;

        try {

            fileWriter = new FileWriter(output, true);
            bufferedWriter = new BufferedWriter(fileWriter);
            for (String str : words) {
                bufferedWriter.write(str);
                bufferedWriter.newLine();
            }
            bufferedWriter.newLine();

            System.out.println("Finish");

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {

            if (null != bufferedWriter) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (null != fileWriter) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
