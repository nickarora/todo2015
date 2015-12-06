package nick.arora.todo2015.data.deserializers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.List;

import nick.arora.todo2015.data.models.Parse;

public class ParseDeserializer implements JsonDeserializer<List<Parse>>{

    private static final String KEY = "success";

    @Override
    public List<Parse> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray result = new JsonArray();
        JsonArray contentArray = json.getAsJsonArray();

        for (int i = 0; i < contentArray.size(); i++) {
            JsonElement content = contentArray.get(i).getAsJsonObject().get(KEY);
            result.add(content);
        }

        return new Gson().fromJson(result, typeOfT);
    }
}
