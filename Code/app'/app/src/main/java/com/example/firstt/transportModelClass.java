package com.example.firstt;

public class transportModelClass {
    String name;
    String desc;
    String img;
    String price;
    String tts;
    public transportModelClass(String name, String desc, String img, String price,String tts) {
        this.name = name;
        this.desc = desc;
        this.img = img;
        this.price = price;
        this.tts=tts;
    }

    public transportModelClass() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTts() {
        return tts;
    }

    public void setTts(String tts) {
        this.tts = tts;
    }
}
