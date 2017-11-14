package com.smodj.app.quranplayer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChapterModelEnAr {

    @SerializedName("Number")
    @Expose
    private String number;
    @SerializedName("Name_En")
    @Expose
    private String nameEn;
    @SerializedName("Name_Ar")
    @Expose
    private String nameAr;
    @SerializedName("Translation")
    @Expose
    private String translation;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

}