package com.employee.system.enums;

public enum EmployeeStatus {
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    ON_LEAVE("On Leave"),
    SUSPENDED("Suspended"),
    TERMINATED("Terminated");

    private final String statusDescription;

    EmployeeStatus(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public String getDescription() {
        return statusDescription;
    }
}
