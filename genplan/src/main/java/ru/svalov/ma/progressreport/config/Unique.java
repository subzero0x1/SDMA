package ru.svalov.ma.progressreport.config;

import javax.xml.bind.annotation.XmlAttribute;

public abstract class Unique {


    private String id;

    @XmlAttribute
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
