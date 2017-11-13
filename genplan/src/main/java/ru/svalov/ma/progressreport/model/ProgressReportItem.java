package ru.svalov.ma.progressreport.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProgressReportItem {
    private String desc;
    private List<String> idLabels = new ArrayList<>();
    private List<ProgressReportLabel> labels = new ArrayList<>();
    private String idList;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<String> getIdLabels() {
        return idLabels;
    }

    public void setIdLabels(List<String> idLabels) {
        this.idLabels = idLabels;
    }

    public List<ProgressReportLabel> getLabels() {
        return labels;
    }

    public void setLabels(List<ProgressReportLabel> labels) {
        this.labels = labels;
    }

    public String getIdList() {
        return idList;
    }

    public void setIdList(String idList) {
        this.idList = idList;
    }
}