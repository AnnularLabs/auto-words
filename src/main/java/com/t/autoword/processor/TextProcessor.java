package com.t.autoword.processor;

import com.spire.doc.fields.TextRange;
import com.t.autoword.utils.TextUtil;

import java.util.*;

/**
 * ClassName: TextProcessor
 * Description:
 *
 * @Author agility6
 * @Create 2024/1/28 18:48
 * @Version: 1.0
 */
public class TextProcessor {

    private int colorRGB;
    private String targetFilePath;

    public TextProcessor(String targetFilePath, int colorRGB) {
        this.targetFilePath = targetFilePath;
        this.colorRGB = colorRGB;
    }

    /**
     * 处理数据
     * @param textRanges
     * @return 将新单词队列返回
     */
    public Queue<String> processText(List<TextRange> textRanges) {

        Queue<String> words = new LinkedList<>();
        // 获取目标文件中已经存在的单词
        Map<String, Integer> existWords = TextUtil.getTxtContainsWords(targetFilePath);

        for (TextRange textRange : textRanges) {
            int textColorRGB = textRange.getCharacterFormat().getTextColor().getRGB();
            if (textColorRGB == colorRGB) {
                // 转化小写，如果获取的Text连续需要分割单词
                String[] word = textRange.getText().toLowerCase().split(" ");
                // 过滤重复的单词
                if (word.length >= 1 && !existWords.containsKey(word[0])) {
                    words.addAll(Arrays.asList(word));
                }
            }
        }

        return words;
    }
}
