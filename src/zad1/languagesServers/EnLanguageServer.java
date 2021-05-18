package zad1.languagesServers;

import java.io.FileInputStream;
import java.util.Properties;

public class EnLanguageServer extends LanguageServer {

    @Override
    public String translate(String word) throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream("src/zad1/dictionaries/en.properties"));

        return properties.getProperty(word);
    }
}
