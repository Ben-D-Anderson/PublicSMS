package com.terraboxstudios.publicsms.phone;

import com.terraboxstudios.publicsms.message.Message;

import java.io.IOException;
import java.util.Collection;

public interface PhoneSource {

     Collection<PublicPhone> getPhoneNumbers() throws IOException;
     Collection<PublicPhone> getPhoneNumbers(String countryCode) throws IOException;
     Collection<Message> getMessages(PublicPhone receivingPhone) throws IOException;

}
