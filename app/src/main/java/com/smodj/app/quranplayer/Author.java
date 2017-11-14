
package com.smodj.app.quranplayer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import java.util.Map;

public class Author {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("Server")
    @Expose
    private String server;
    @SerializedName("rewaya")
    @Expose
    private String rewaya;
    @SerializedName("count")
    @Expose
    private String count;
    @SerializedName("letter")
    @Expose
    private String letter;
    @SerializedName("suras")
    @Expose
    private String suras;

    private String[]_suras;

    public Map<String, String> chapters = new HashMap<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getRewaya() {
        return rewaya;
    }

    public void setRewaya(String rewaya) {
        this.rewaya = rewaya;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getSuras() {
        return suras;
    }

    public void setSuras(String suras) {
        this.suras = suras;
    }

    public String[] get_Suras() {
        return _suras;
    }

    public void set_Suras(String suras) {
            _suras = suras.split("\\s*,\\s*");
    }
    public Map<String, String> getChapters(){return chapters;}

    public String getChaptersValue(String key){return chapters.get(key);}

    public void setChapters(String key, String value){
        chapters.put(key, value);
    }

}
