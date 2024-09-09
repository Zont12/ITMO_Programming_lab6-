package server.Adapters;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class NumberDeserializer implements JsonDeserializer<Number> {
    @Override
    public Number deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            return json.getAsNumber();
        } catch (JsonParseException e) {
            return null; // Возможно, лучше бросить исключение дальше или залогировать его
        }
    }
}
