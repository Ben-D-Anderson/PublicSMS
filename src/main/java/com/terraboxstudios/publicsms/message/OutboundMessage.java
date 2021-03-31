package com.terraboxstudios.publicsms.message;

import com.terraboxstudios.publicsms.phone.Phone;
import com.terraboxstudios.publicsms.phone.PublicPhone;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OutboundMessage {

    private final PublicPhone sender;
    private final Phone receiver;
    private final String body;

    public static OutboundMessage from(PublicPhone sender, Phone receiver, String body) {
        if (sender == null || receiver == null) throw new NullPointerException();
        return new OutboundMessage(sender, receiver, body);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OutboundMessage inboundMessage = (OutboundMessage) o;
        return sender.equals(inboundMessage.sender) &&
                receiver.equals(inboundMessage.receiver) &&
                body.equals(inboundMessage.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sender, receiver, body);
    }

}
