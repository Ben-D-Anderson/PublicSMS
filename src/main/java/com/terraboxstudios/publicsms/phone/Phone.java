package com.terraboxstudios.publicsms.phone;

import com.sun.istack.internal.Nullable;

import java.util.Objects;

public class Phone {

    private final String number;
    private final String countryCode;

    public Phone(String number, String countryCode) {
        if (number == null) throw new NullPointerException();
        this.number = number;
        this.countryCode = countryCode;
    }

    @Nullable
    public String getCountryCode() {
        return countryCode;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone = (Phone) o;
        return number.equals(phone.number) && Objects.equals(countryCode, phone.countryCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, countryCode);
    }

}
