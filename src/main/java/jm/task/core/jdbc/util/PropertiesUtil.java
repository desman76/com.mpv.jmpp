package jm.task.core.jdbc.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

final class PropertiesUtil {
    private static final Properties PROPERTIES = new Properties();

    private PropertiesUtil() {
    }

    static{
        loadProperties();
    }

    private static void loadProperties() {
        try(InputStream stream = Util.class.getClassLoader().getResourceAsStream("application.properties");) {
            PROPERTIES.load(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }
}
