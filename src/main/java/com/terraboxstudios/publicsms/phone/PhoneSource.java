package com.terraboxstudios.publicsms.phone;

import com.terraboxstudios.publicsms.message.Message;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

public interface PhoneSource {

     /**
      * Method used to collect available PublicPhone(s) from
      * the phone source.
      * @return Collection of PublicPhone(s) objects which are available
      * and provided by the phone source.
      * @throws IOException may be thrown when communicating with a website or api.
      */
     Collection<PublicPhone> getPhoneNumbers() throws IOException;

     /**
      * Method used to collect available PublicPhone(s) which belong to the country
      * defined by the code from the phone source.
      *
      * @param countryCode code associated with the country
      *                    of the PublicPhone(s) which are being retrieved.
      * @return Collection of PublicPhone(s) objects, which are available and who's
      * country code matches the countryCode parameter, provided by the phone source.
      * @throws IOException may be thrown when communicating with a website or api.
      */
     default Collection<PublicPhone> getPhoneNumbers(String countryCode) throws IOException {
          return getPhoneNumbers().stream().filter(phone -> phone.getCountryCode().equalsIgnoreCase(countryCode)).collect(Collectors.toSet());
     }

     /**
      * Method used to retrieve a Collection of Message(s) sent to the PublicPhone
      * provided by the phone source.
      * @param receivingPhone PublicPhone to retrieve messages from.
      * @return Collection of Message(s) objects, which are provided by the phone source
      * and were sent to the receivingPhone.
      * @throws IOException may be thrown when communicating with a website or api.
      */
     Collection<Message> getMessages(PublicPhone receivingPhone) throws IOException;

}
