package net.askearly.utils;

import net.askearly.Main;
import net.askearly.exceptions.PropertyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtils {

    private static final Logger logger = LogManager.getLogger(PropertyUtils.class);

    public static Properties getProperties(String propertiesFileName) {
        Properties properties = new Properties();

        try (InputStream stream = Main.class.getClassLoader().getResourceAsStream(propertiesFileName)) {
            if (stream == null) {
                logger.error("Sorry, unable to find {}", propertiesFileName);
                throw new PropertyException("Sorry, unable to find " + propertiesFileName);
            }
            properties.load(stream);
        } catch (IOException ex) {
            logger.error("Unable to load {}", propertiesFileName, ex);
            throw new PropertyException("Unable to load " + propertiesFileName, ex);
        }
        return properties;
    }
}
