package com.terraboxstudios.publicsms.services;

import com.terraboxstudios.publicsms.message.Message;
import com.terraboxstudios.publicsms.phone.PublicPhone;

public interface PublicPhoneReceiveMessageListener {

    void onReceiveMessage(PublicPhone publicPhone, Message message);

}
