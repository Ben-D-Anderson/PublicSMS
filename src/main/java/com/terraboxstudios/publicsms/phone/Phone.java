package com.terraboxstudios.publicsms.phone;

import java.util.Objects;

public class Phone {

    private final String number, countryCode;

    public Phone(String number, String countryCode) {
        if (number == null || countryCode == null) throw new NullPointerException();
        this.number = number;
        this.countryCode = countryCode;
    }

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
        Phone that = (Phone) o;
        return number.equals(that.number) &&
                countryCode.equals(that.countryCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, countryCode);
    }

}
