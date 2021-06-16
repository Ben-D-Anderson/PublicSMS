package com.terraboxstudios.publicsms.message.outbound;

public interface OutboundMessageSender<T> {

    <E extends Exception> T send(OutboundMessage outboundMessage) throws E;

}
