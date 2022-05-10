package pl.vertty.nomenhc.handlers;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class ConfigLoader {
    private static HashMap<String, JSONObject> files = new HashMap<>();

    public static JSONObject getLoadedFile(String name) {
        for (Map.Entry<String, JSONObject> file : files.entrySet()) {
            if (((String)file.getKey()).equals(name))
                return file.getValue();
        }
        return null;
    }

    public static JSONObject load(String file) {
        try {
            JSONObject object = new JSONObject(new String(Files.readAllBytes(Paths.get(file, new String[0])), StandardCharsets.UTF_8));
            files.put(file, object);
            return object;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
