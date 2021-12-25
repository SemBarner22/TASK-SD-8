package com.sd.utils;

import java.util.Objects;

// Rpm currently can't be more then
// int due to it being size of a container,
// But for next generations it could be good
public class Rpm {
    private final int value;

    public int getIntValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rpm rpm = (Rpm) o;
        return value == rpm.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public Rpm(int value) {
        this.value = value;
    }

}
