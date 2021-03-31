package com.terraboxstudios.publicsms.message;

import com.terraboxstudios.publicsms.phone.Phone;
import com.terraboxstudios.publicsms.phone.PublicPhone;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.Objects;

@Getter
@AllArgsConstructor (access = AccessLevel.PRIVATE)
public class InboundMessage {

    private final Phone sender;
    private final PublicPhone receiver;
    private final Date date;
    private final String body;

    public static InboundMessage from(Phone sender, PublicPhone receiver, Date date, String body) {
        if (sender == null || receiver == null || date == null) throw new NullPointerException();
        return new InboundMessage(sender, receiver, date, body);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InboundMessage inboundMessage = (InboundMessage) o;
        return sender.equals(inboundMessage.sender) &&
                receiver.equals(inboundMessage.receiver) &&
                date.equals(inboundMessage.date) &&
                body.equals(inboundMessage.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sender, receiver, date, body);
    }

}
