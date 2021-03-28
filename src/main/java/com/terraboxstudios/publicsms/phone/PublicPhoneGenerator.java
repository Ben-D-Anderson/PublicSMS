package com.terraboxstudios.publicsms.phone;

import java.io.IOException;
import java.util.*;

public class PublicPhoneGenerator {

    private final Collection<PublicPhone> publicPhones = new HashSet<>();

    public PublicPhoneGenerator(PhoneSource... phoneSources) {
        for (PhoneSource phoneSource : phoneSources) {
            try {
                Collection<PublicPhone> phones = phoneSource.getPhoneNumbers();
                publicPhones.addAll(phones);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public PublicPhoneGenerator(Locale country, PhoneSource... phoneSources) {
        for (PhoneSource phoneSource : phoneSources) {
            try {
                Collection<PublicPhone> phones = phoneSource.getPhoneNumbers(country.getCountry());
                publicPhones.addAll(phones);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Collection<PublicPhone> getPublicPhones() {
        return publicPhones;
    }

    public Optional<PublicPhone> getRandomPublicPhone() {
        return publicPhones.stream().skip(new Random().nextInt(publicPhones.size())).findFirst();
    }

}
