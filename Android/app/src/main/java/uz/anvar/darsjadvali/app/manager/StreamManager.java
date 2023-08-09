package uz.anvar.darsjadvali.app.manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class StreamManager {

    public String ReadStream(InputStream stream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder builder = new StringBuilder();

        try {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                // if (reader.ready()) // builder.append('\n');
            }
            reader.close();

            return builder.toString();
        } catch (IOException e) {
            return null;
        }
    }
}
