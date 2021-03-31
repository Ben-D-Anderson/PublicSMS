package com.terraboxstudios.publicsms.message;

public interface OutboundMessageSender<T> {

    <E extends Exception> T send(OutboundMessage outboundMessage) throws E;

}
