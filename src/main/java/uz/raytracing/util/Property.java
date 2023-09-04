package uz.raytracing.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class Property {
    private static final String propertiesFilePath = "./src/main/resources/application.properties";
    private static final File propertiesFile;
    private static final Properties properties;

    static {
        propertiesFile = new File(propertiesFilePath);
        properties = new Properties();
        try {
            if (!propertiesFile.createNewFile()) {
                properties.load(new FileInputStream(propertiesFile));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void set(HashMap<String, String> propertiesTable) {
        try {
            properties.putAll(propertiesTable);
            properties.store(new FileOutputStream(propertiesFile), "Main Application Properties"); // This comment will be updated
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int get(String key) {
        String value = properties.getProperty(key);
        if (value.isEmpty())
            return 0;
        return Integer.parseInt(value);
    }

}
