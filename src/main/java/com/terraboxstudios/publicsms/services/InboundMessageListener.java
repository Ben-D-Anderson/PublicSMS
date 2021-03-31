package com.terraboxstudios.publicsms.services;

import com.terraboxstudios.publicsms.message.InboundMessage;
import com.terraboxstudios.publicsms.phone.PublicPhone;

public interface InboundMessageListener {

    void onReceiveMessage(PublicPhone publicPhone, InboundMessage inboundMessage);

}
