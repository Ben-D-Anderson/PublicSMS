package com.terraboxstudios.publicsms;

import com.terraboxstudios.publicsms.phone.PublicPhoneSource;
import com.terraboxstudios.publicsms.phone.PublicPhone;
import com.terraboxstudios.publicsms.phone.PublicPhoneGenerator;
import com.terraboxstudios.publicsms.phone.sources.OSIOPublicPhoneSource;
import com.terraboxstudios.publicsms.phone.sources.RFSPublicPhoneSource;

import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;

public class UKPhoneNumberSelector {

    public static void main(String[] args) {

        PublicPhoneSource rfsPublicPhoneSource = new RFSPublicPhoneSource();
        PublicPhoneSource osioPublicPhoneSource = new OSIOPublicPhoneSource();

        Collection<PublicPhoneSource> publicPhoneSources = new HashSet<>();
        publicPhoneSources.add(rfsPublicPhoneSource);
        publicPhoneSources.add(osioPublicPhoneSource);

        PublicPhoneGenerator publicPhoneGenerator = new PublicPhoneGenerator(Locale.UK.getCountry(), publicPhoneSources);
        Optional<PublicPhone> publicPhone = publicPhoneGenerator.getRandomPublicPhone();
        publicPhone.ifPresent(phone -> System.out.println(phone.getNumber()));

    }

}
