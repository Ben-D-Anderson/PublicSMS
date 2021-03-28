package com.terraboxstudios.publicsms.phone;

import java.util.Objects;

public class PublicPhone extends Phone {

    private final PhoneSource phoneSource;

    public PublicPhone(String number, String countryCode, PhoneSource phoneSource) {
        super(number, countryCode);
        if (phoneSource == null) throw new NullPointerException();
        this.phoneSource = phoneSource;
    }

    public PhoneSource getPhoneSource() {
        return phoneSource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PublicPhone that = (PublicPhone) o;
        return phoneSource.equals(that.phoneSource)
                && getCountryCode().equals(that.getCountryCode())
                && getNumber().equals(that.getNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), phoneSource);
    }
}