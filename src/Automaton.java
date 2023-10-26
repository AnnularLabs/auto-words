import com.spire.doc.Document;
import com.spire.doc.documents.Paragraph;
import com.spire.doc.fields.TextRange;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Automaton {

    private String filePath;
    private int colorRGB;
    private String targetFilePath;
    private Queue<String> words;


    public Automaton(String filePath, int targetColor, String targetFilePath) {
        this.filePath = filePath;
        this.colorRGB = targetColor;
        this.targetFilePath = targetFilePath;
        this.words = new LinkedList<>();
    }

    public void start() {

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
                        words.addAll(Arrays.asList(word));
                    }
                }
            }
        }

        writer();
    }

    /**
     * 将队列中存储的标记单词加入到.txt文本中
     */
    private void writer() {
        targetFilePath = targetFilePath.isEmpty() ? "words.txt" : targetFilePath+"/words.txt";
        File output = new File(targetFilePath);
        try {
            FileWriter fileWriter = new FileWriter(output);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (String str : words) {
                bufferedWriter.write(str);
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Finish");
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
}
