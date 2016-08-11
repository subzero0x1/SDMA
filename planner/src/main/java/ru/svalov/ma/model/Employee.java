package ru.svalov.ma.model;

public class Employee {

    private String login;

    private String name;

    private boolean permanent;

    private boolean dailyDuty;

    private boolean architectDailyDuty;

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
    public int hashCode() {
        if (login == null) {
            return super.hashCode();
        } else {
            return login.hashCode();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (permanent != employee.permanent) return false;
        if (dailyDuty != employee.dailyDuty) return false;
        if (!login.equals(employee.login)) return false;
        if (!name.equals(employee.name)) return false;
        if (!email.equals(employee.email)) return false;
        return gmail.equals(employee.gmail);

    }

    @Override
    public String toString() {
        return "Employee{" +
                "login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", permanent=" + permanent +
                ", dailyDuty=" + dailyDuty +
                ", email='" + email + '\'' +
                ", gmail='" + gmail + '\'' +
                '}';
    }
}
