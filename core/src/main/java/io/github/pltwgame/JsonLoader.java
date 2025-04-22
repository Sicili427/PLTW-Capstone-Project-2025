package io.github.pltwgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class JsonLoader {
    public static JsonValue getJson (String jsonFile) {
        String filePath = "configs/" + jsonFile;
        FileHandle jsonFilePath = Gdx.files.internal(filePath);
        JsonReader jsonReader = new JsonReader();
        return jsonReader.parse(jsonFilePath);
    }
}
