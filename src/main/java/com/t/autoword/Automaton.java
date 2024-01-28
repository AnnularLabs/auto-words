package com.t.autoword;

import com.spire.doc.fields.TextRange;
import com.t.autoword.processor.DocumentReader;
import com.t.autoword.processor.TextProcessor;
import com.t.autoword.utils.ConfigReader;
import com.t.autoword.utils.TextUtil;

import java.util.*;

public class Automaton {

    private final String filePath;
    private final int colorRGB;
    private final String targetFilePath;

    public Automaton() {
        ConfigReader configReader = new ConfigReader();
        this.filePath = configReader.getFilePath();
        this.targetFilePath = configReader.getFileTargetPath();
        this.colorRGB = configReader.getTextColorRGB();
    }

    public void start() {

        DocumentReader reader = new DocumentReader(filePath);
        List<TextRange> textRanges = reader.readDocument();

        TextProcessor processor = new TextProcessor(targetFilePath, colorRGB);
        Queue<String> extractedWords = processor.processText(textRanges);

        TextUtil.writerFile(targetFilePath, extractedWords);
    }

}