package com.terraboxstudios.publicsms.phone;

import java.io.IOException;
import java.util.*;

public class PublicPhoneGenerator {

    private final Collection<PublicPhone> publicPhones = new HashSet<>();

    /**
     * Fills the PublicPhoneGenerator with PublicPhone(s)
     * gathered from the PhoneSource(s).
     * @param phoneSources PhoneSource(s) to use to get PublicPhone(s)
     */
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

    /**
     * Fills the PublicPhoneGenerator with PublicPhone(s)
     * gathered from the PhoneSource(s) which match the specified country.
     * @param country The country where the phone numbers should be located.
     * @param phoneSources PhoneSource(s) to use to get PublicPhone(s)
     */
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

    /**
     * Method to get all the PublicPhone(s) loaded into the
     * generator from provided PhoneSource(s).
     * @return Collection containing all PublicPhone(s). If there
     * are no PublicPhone(s) available then the method returns
     * an empty Collection.
     */
    public Collection<PublicPhone> getPublicPhones() {
        return publicPhones;
    }

    /**
     * Method to get a random PublicPhone from the PublicPhone(s)
     * loaded into the generator.
     * @return An optional representing the randomly selected
     * PublicPhone from those loaded into the generator.
     * If there are no PublicPhone(s) loaded into the
     * generator then the method will return an empty Optional.
     */
    public Optional<PublicPhone> getRandomPublicPhone() {
        if (publicPhones.size() == 0) return Optional.empty();
        return publicPhones.stream().skip(new Random().nextInt(publicPhones.size())).findFirst();
    }

}
