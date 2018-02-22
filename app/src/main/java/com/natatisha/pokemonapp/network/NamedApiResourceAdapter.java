package com.natatisha.pokemonapp.network;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.natatisha.pokemonapp.data.model.NamedApiResource;

import java.lang.reflect.Type;

public class NamedApiResourceAdapter implements JsonDeserializer<NamedApiResource> {

    private int urlToId(String url) {
        String[] segments = url.split("/");
        String idStr = segments[segments.length - 1];
        return Integer.parseInt(idStr);
    }

    @Override
    public NamedApiResource deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        return new NamedApiResource(jsonObject.get("name").getAsString(),
                urlToId(jsonObject.get("url").getAsString()),
                jsonObject.get("url").getAsString());
    }
}
