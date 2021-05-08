package com.terraboxstudios.publicsms.phone;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Phone {

    private final String number;
    private final String countryCode;

    public Phone(String number, String countryCode) {
        if (number == null) throw new NullPointerException();
        this.number = number;
        this.countryCode = countryCode;
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
