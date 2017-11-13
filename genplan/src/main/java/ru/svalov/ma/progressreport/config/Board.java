package ru.svalov.ma.progressreport.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class Board {

    // properties


    private String name;
    private String uri;

    // lists

    private Done done;

    // labels

    private Small small;
    private Middle middle;
    private Large large;
    private Week week;

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @XmlElement
    public Done getDone() {
        return done;
    }

    public void setDone(Done done) {
        this.done = done;
    }

    @XmlElement
    public Small getSmall() {
        return small;
    }

    public void setSmall(Small small) {
        this.small = small;
    }

    @XmlElement
    public Middle getMiddle() {
        return middle;
    }

    public void setMiddle(Middle middle) {
        this.middle = middle;
    }

    @XmlElement
    public Large getLarge() {
        return large;
    }

    public void setLarge(Large large) {
        this.large = large;
    }

    @XmlElement
    public Week getWeek() {
        return week;
    }

    public void setWeek(Week week) {
        this.week = week;
    }
}
