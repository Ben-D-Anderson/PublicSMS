package com.terraboxstudios.publicsms;

import com.terraboxstudios.publicsms.phone.PhoneSource;
import com.terraboxstudios.publicsms.phone.PublicPhone;
import com.terraboxstudios.publicsms.phone.PublicPhoneGenerator;
import com.terraboxstudios.publicsms.phone.sources.OSIOPhoneSource;
import com.terraboxstudios.publicsms.phone.sources.RFSPhoneSource;

import java.util.Locale;
import java.util.Optional;

public class UKPhoneNumberSelector {

    public static void main(String[] args) {

        PhoneSource rfsPhoneSource = new RFSPhoneSource();
        PhoneSource osioPhoneSource = new OSIOPhoneSource();

        PublicPhoneGenerator publicPhoneGenerator = new PublicPhoneGenerator(Locale.UK.getCountry(), rfsPhoneSource, osioPhoneSource);
        Optional<PublicPhone> publicPhone = publicPhoneGenerator.getRandomPublicPhone();
        publicPhone.ifPresent(phone -> System.out.println(phone.getNumber()));

    }

}
