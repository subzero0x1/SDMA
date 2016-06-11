package ru.svalov.ma.genplan.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectReport {

    private String summary;
    private String phase;
    private LocalDate planedDate;
    private String bankName;
    private String projectCode;
    private String dashboardUrl;
    private String managedBy;
    private List<String> activityStatus = new ArrayList<>();

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public LocalDate getPlanedDate() {
        return planedDate;
    }

    public void setPlanedDate(LocalDate planedDate) {
        this.planedDate = planedDate;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getDashboardUrl() {
        return dashboardUrl;
    }

    public void setDashboardUrl(String dashboardUrl) {
        this.dashboardUrl = dashboardUrl;
    }

    public String getManagedBy() {
        return managedBy;
    }

    public void setManagedBy(String managedBy) {
        this.managedBy = managedBy;
    }

    public List<String> getActivityStatus() {
        return activityStatus;
    }
}
