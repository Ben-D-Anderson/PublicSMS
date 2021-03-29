package com.terraboxstudios.publicsms.phone;

import com.terraboxstudios.publicsms.message.Message;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

public class PublicPhone extends Phone {

    private final PublicPhoneSource publicPhoneSource;

    public PublicPhone(String number, String countryCode, PublicPhoneSource publicPhoneSource) {
        super(number, countryCode);
        if (publicPhoneSource == null) throw new NullPointerException();
        this.publicPhoneSource = publicPhoneSource;
    }

    public Collection<Message> getMessages() throws IOException {
        return publicPhoneSource.getMessages(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PublicPhone that = (PublicPhone) o;
        return publicPhoneSource.equals(that.publicPhoneSource)
                && getCountryCode().equals(that.getCountryCode())
                && getNumber().equals(that.getNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), publicPhoneSource);
    }

}
