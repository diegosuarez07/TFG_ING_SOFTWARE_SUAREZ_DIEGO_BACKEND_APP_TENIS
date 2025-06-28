package com.siglo21.tfg.enums;

public enum TimeslotStatus {
    AVAILABLE("AVAILABLE", "DISPONIBLE"),
    BOOKED("BOOKED", "OCUPADO");

    private final String dbValue;
    private final String displayValue;

    TimeslotStatus(String dbValue, String displayValue) {
        this.dbValue = dbValue;
        this.displayValue = displayValue;
    }

    public String getDbValue() {
        return dbValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public static TimeslotStatus fromDisplayValue(String displayValue) {
        for (TimeslotStatus status : values()) {
            if (status.displayValue.equalsIgnoreCase(displayValue)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Estado no válido: " + displayValue);
    }

    public static TimeslotStatus fromDbValue(String dbValue) {
        for (TimeslotStatus status : values()) {
            if (status.dbValue.equalsIgnoreCase(dbValue)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Estado no válido: " + dbValue);
    }
}
