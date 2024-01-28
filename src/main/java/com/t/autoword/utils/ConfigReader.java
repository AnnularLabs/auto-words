package com.t.autoword.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ClassName: ConfigReader
 * Description:
 *
 * @Author agility6
 * @Create 2024/1/28 20:17
 * @Version: 1.0
 */
public class ConfigReader {

    private final Properties properties;

    public ConfigReader() {
        properties = new Properties();
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 获取字体颜色
     * @return
     */
    public int getTextColorRGB() {
        String colorRGB = properties.getProperty("text.color.rgb");
        return Integer.parseInt(colorRGB);
    }

    /**
     * 获取word文档路径
     * @return
     */
    public String getFilePath() {
        return properties.getProperty("file.path");

    }

    /**
     * 获取目标路径
     * @return
     */
    public String getFileTargetPath() {
        String targetFilePath = properties.getProperty("file.targetPath");
        return targetFilePath.isEmpty() ? "words.txt" : targetFilePath + "/words.txt";
    }

}
