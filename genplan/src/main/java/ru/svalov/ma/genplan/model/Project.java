package ru.svalov.ma.genplan.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Project {

    private String id;
    private String name;
    private String desc;
    private String idList;
    private List<String> idLabels;

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIdList() {
        return idList;
    }

    public void setIdList(String idList) {
        this.idList = idList;
    }

    public List<String> getIdLabels() {
        return idLabels;
    }

    public void setIdLabels(List<String> idLabels) {
        this.idLabels = idLabels;
    }
}
