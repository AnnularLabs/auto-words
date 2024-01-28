package com.t.autoword.processor;

import com.spire.doc.Document;
import com.spire.doc.documents.Paragraph;
import com.spire.doc.fields.TextRange;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: DocumentReader
 * Description:
 *
 * @Author agility6
 * @Create 2024/1/28 18:48
 * @Version: 1.0
 */
public class DocumentReader {

    private final String filePath;

    public DocumentReader(String filePath) {
        this.filePath = filePath;
    }

    /**
     * 读取word数据
     * @return
     */
    public List<TextRange> readDocument() {

        Document document = new Document();
        List<TextRange> textRanges = new ArrayList<>();

        document.loadFromFile(filePath);
        /** 遍历section以及paragraph */
        for (int i = 0; i < document.getSections().getCount(); i++) {
            for (int j = 0; j < document.getSections().get(i).getParagraphs().getCount(); j++) {

                Paragraph paragraph = document.getSections().get(i).getParagraphs().get(j);

                for (Object element : paragraph.getChildObjects()) {
                    /** 如果当前类型u是TextRange继续循环 */
                    if (!(element instanceof TextRange)) continue;
                    textRanges.add((TextRange) element);
                }
            }
        }

        return textRanges;
    }

}
