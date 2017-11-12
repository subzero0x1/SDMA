package ru.svalov.ma.progressreport.config;

public class Board {

    private URI uri;

    private Done done;

    private Small small;
    private Middle middle;
    private Large large;

    public URI getUrl() {
        return url;
    }

    public void setUrl(URI url) {
        this.url = url;
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
}
