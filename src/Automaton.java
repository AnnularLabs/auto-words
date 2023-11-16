import com.spire.doc.Document;
import com.spire.doc.documents.Paragraph;
import com.spire.doc.fields.TextRange;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Automaton {

    private String filePath;
    private int colorRGB;
    private String targetFilePath;
    private Queue<String> words;
    private int sum;
    private Map<String, Integer> txtWords;
    private List<String> repeatWords;

    public Automaton(String filePath, int colorRGB, String targetFilePath) {
        this(filePath, colorRGB, targetFilePath, new LinkedList<>(), 0, new HashMap<>(), new ArrayList<>());
    }

    public Automaton(String filePath, int colorRGB, String targetFilePath, Queue<String> words, int sum, Map<String, Integer> txtWords, List<String> repeatWords) {
        this.filePath = filePath;
        this.colorRGB = colorRGB;
        this.targetFilePath = targetFilePath.isEmpty() ? "words.txt" : targetFilePath+"/words.txt";
        this.words = words;
        this.sum = sum;
        this.txtWords = txtWords;
        this.repeatWords = repeatWords;
    }

    public void start() {

        getTxtContainsWords();

        Document document = new Document();
        document.loadFromFile(filePath);

        /** 遍历section以及paragraph */
        for (int i = 0; i < document.getSections().getCount(); i++) {
            for (int j = 0; j < document.getSections().get(i).getParagraphs().getCount(); j++) {

                Paragraph paragraph = document.getSections().get(i).getParagraphs().get(j);

                for (Object element : paragraph.getChildObjects()) {
                    /** 如果当前类型u是TextRange继续循环 */
                    if (!(element instanceof TextRange)) continue;

                    TextRange textRange = (TextRange) element;
                    int textColorRGB = textRange.getCharacterFormat().getTextColor().getRGB();
                    if (textColorRGB == colorRGB) {
                        // 转换小写，获取的Text连续需要分开为一个单词
                        String[] word = textRange.getText().toLowerCase().split(" ");
                        if (!txtWords.containsKey(word[0])) {
                            words.addAll(Arrays.asList(word));
                        } else {
                            repeatWords.addAll(Arrays.asList(word));
                        }
                    }
                }
            }
        }

        writer();
    }

    /**
     * 获取.txt中包含的单词
     */
    private void getTxtContainsWords() {

        Path path = Paths.get(targetFilePath);
        if (Files.exists(path)) {
            try {
                List<String> strings = Files.readAllLines(path, StandardCharsets.UTF_8);
                for (String str : strings) {
                    txtWords.put(str, 1);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 将队列中存储的标记单词加入到.txt文本中
     * 最后输出标记单词与.txt重复的单词
     */
    private void writer() {

        File output = new File(targetFilePath);
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;

        try {

           fileWriter = new FileWriter(output, true);
           bufferedWriter = new BufferedWriter(fileWriter);
            for (String str : words) {
                sum++;
                bufferedWriter.write(str);
                bufferedWriter.newLine();
            }
            bufferedWriter.newLine();

            System.out.println("Add words ===> " + sum);
            System.out.println("Finish");

            System.out.println("========== RepeatWords  ==========");
            for (String word : repeatWords) {
                System.out.println(word);
            }

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


    /** ---------- get/set ---------- */

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getColorRGB() {
        return colorRGB;
    }

    public void setColorRGB(int colorRGB) {
        this.colorRGB = colorRGB;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public String getTargetFilePath() {
        return targetFilePath;
    }

    public void setTargetFilePath(String targetFilePath) {
        this.targetFilePath = targetFilePath;
    }
}
