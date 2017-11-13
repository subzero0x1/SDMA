package ru.svalov.ma.progressreport.config;

import javax.xml.bind.annotation.XmlElement;
import java.net.URI;

public class Board {

    // properties

    @XmlElement
    private String name;
    @XmlElement
    private URI uri;

    // lists

    @XmlElement
    private Done done;

    // labels

    @XmlElement
    private Small small;
    @XmlElement
    private Middle middle;
    @XmlElement
    private Large large;
    @XmlElement
    private Week week;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public Done getDone() {
        return done;
    }

    public void setDone(Done done) {
        this.done = done;
    }

    public Small getSmall() {
        return small;
    }

    public void setSmall(Small small) {
        this.small = small;
    }

    public Middle getMiddle() {
        return middle;
    }

    public void setMiddle(Middle middle) {
        this.middle = middle;
    }

    public Large getLarge() {
        return large;
    }

    public void setLarge(Large large) {
        this.large = large;
    }

    public Week getWeek() {
        return week;
    }

    public void setWeek(Week week) {
        this.week = week;
    }
}
