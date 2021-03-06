package nick.arora.todo2015.data.deserializers;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.List;

import nick.arora.todo2015.data.models.Todo;

public class TodosDeserializer implements JsonDeserializer<List<Todo>> {

    public static final String KEY = "results";

    @Override
    public List<Todo> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement content = json.getAsJsonObject().get(KEY);
        return new Gson().fromJson(content, typeOfT);
    }

}
