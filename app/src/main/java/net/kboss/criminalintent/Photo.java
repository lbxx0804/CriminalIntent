package net.kboss.criminalintent;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Scan on 2016-02-19.
 */
public class Photo {

    private final String JSON_FILENAME = "filename";
    private String fileName;

    public Photo(String fileName) {
        this.fileName = fileName;
    }

    public Photo(JSONObject json) throws JSONException {
        fileName = json.getString(JSON_FILENAME);
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_FILENAME, fileName);
        return json;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


}
