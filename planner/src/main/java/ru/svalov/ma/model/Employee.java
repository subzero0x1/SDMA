package ru.svalov.ma.model;

public class Employee {

    private String login;

    private String name;

    private boolean permanent;

    private boolean dailyDuty;

    private boolean architectDailyDuty;

    private boolean retailDailyDuty;

    private boolean retailFrontDailyDuty;

    private String email;

    private String gmail;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPermanent() {
        return permanent;
    }

    public void setPermanent(boolean permanent) {
        this.permanent = permanent;
    }

    public boolean isDailyDuty() {
        return dailyDuty;
    }

    public void setDailyDuty(boolean dailyDuty) {
        this.dailyDuty = dailyDuty;
    }

    public boolean isArchitectDailyDuty() {
        return architectDailyDuty;
    }

    public void setArchitectDailyDuty(boolean architectDailyDuty) {
        this.architectDailyDuty = architectDailyDuty;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (permanent != employee.permanent) return false;
        if (dailyDuty != employee.dailyDuty) return false;
        if (architectDailyDuty != employee.architectDailyDuty) return false;
        if (retailDailyDuty != employee.retailDailyDuty) return false;
        if (retailFrontDailyDuty != employee.retailFrontDailyDuty) return false;
        if (login != null ? !login.equals(employee.login) : employee.login != null) return false;
        if (name != null ? !name.equals(employee.name) : employee.name != null) return false;
        if (email != null ? !email.equals(employee.email) : employee.email != null) return false;
        return gmail != null ? gmail.equals(employee.gmail) : employee.gmail == null;

    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (permanent ? 1 : 0);
        result = 31 * result + (dailyDuty ? 1 : 0);
        result = 31 * result + (architectDailyDuty ? 1 : 0);
        result = 31 * result + (retailDailyDuty ? 1 : 0);
        result = 31 * result + (retailFrontDailyDuty ? 1 : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (gmail != null ? gmail.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", permanent=" + permanent +
                ", dailyDuty=" + dailyDuty +
                ", architectDailyDuty=" + architectDailyDuty +
                ", retailDailyDuty=" + retailDailyDuty +
                ", retailFrontDailyDuty=" + retailFrontDailyDuty +
                ", email='" + email + '\'' +
                ", gmail='" + gmail + '\'' +
                '}';
    }

    public boolean isRetailDailyDuty() {
        return retailDailyDuty;
    }

    public void setRetailDailyDuty(boolean retailDailyDuty) {
        this.retailDailyDuty = retailDailyDuty;
    }

    public boolean isRetailFrontDailyDuty() {
        return retailFrontDailyDuty;
    }

    public void setRetailFrontDailyDuty(boolean retailFrontDailyDuty) {
        this.retailFrontDailyDuty = retailFrontDailyDuty;
    }
}
