package fr.lernejo.navy_battle.utilities;

import org.json.JSONException;
import org.json.JSONObject;

public class ServerParser {
    private final String id;
    private final String url;
    private final String message;
    public ServerParser(String id, String url, String message) {
        this.id = id;
        this.url = url;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public JSONObject toJSON(){
        JSONObject obj = new JSONObject();
        obj.put("id", id);
        obj.put("url", url);
        obj.put("message", message);
        System.out.println(obj);
        return obj;
    }

    public static ServerParser fromJSON(JSONObject object) throws JSONException {
        return new ServerParser(
                object.getString("id"),
                object.getString("url"),
                object.getString("message")
        );
    }
}
