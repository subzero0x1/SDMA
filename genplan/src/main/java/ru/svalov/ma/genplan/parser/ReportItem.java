package ru.svalov.ma.genplan.parser;

public enum ReportItem {

    HEAD(11),
    COMMENT(1),
    TAIL(3);

    /**
     * number of delimiters found in a line
     */
    private int count;

    ReportItem(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }
}
