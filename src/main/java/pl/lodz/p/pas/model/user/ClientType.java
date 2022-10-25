package pl.lodz.p.pas.model.user;

import lombok.Getter;

@Getter
public enum ClientType {

    STUDENT(20, 2, 0.5, "Student"),
    OUTSIDER(10, 1, 1, "Outsider"),
    UNIVERSITY_EMPLOYEE(30, 3, 0.2, "University Employee");

    private int maxDays;
    private int maxItems;
    private double penalty;
    private String typeInfo;


    ClientType(int maxDays, int maxItems, double penalty, String typeInfo) {
        this.maxDays = maxDays;
        this.maxItems = maxItems;
        this.penalty = penalty;
        this.typeInfo = typeInfo;
    }
}

