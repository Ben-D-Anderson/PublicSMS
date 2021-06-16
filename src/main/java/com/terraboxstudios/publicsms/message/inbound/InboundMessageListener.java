package com.terraboxstudios.publicsms.message.inbound;

import com.terraboxstudios.publicsms.phone.PublicPhone;

public interface InboundMessageListener {

    void onReceiveMessage(PublicPhone publicPhone, InboundMessage inboundMessage);

}
