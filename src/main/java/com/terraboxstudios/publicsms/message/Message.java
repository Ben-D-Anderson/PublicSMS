package com.terraboxstudios.publicsms.message;

import com.terraboxstudios.publicsms.phone.Phone;
import com.terraboxstudios.publicsms.phone.PublicPhone;

import java.util.Date;
import java.util.Objects;

public class Message {

    private final Phone sender;
    private final PublicPhone receiver;
    private final Date date;
    private final String body;

    public static Message from(Phone sender, PublicPhone receiver, Date date, String body) {
        if (sender == null || receiver == null || date == null) throw new NullPointerException();
        return new Message(sender, receiver, date, body);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return sender.equals(message.sender) &&
                receiver.equals(message.receiver) &&
                date.equals(message.date) &&
                body.equals(message.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sender, receiver, date, body);
    }

    public Date getDate() {
        return date;
    }

    public Phone getReceiver() {
        return receiver;
    }

    public Phone getSender() {
        return sender;
    }

    public String getBody() {
        return body;
    }

    private Message(Phone sender, PublicPhone receiver, Date date, String body) {
        this.sender = sender;
        this.receiver = receiver;
        this.date = date;
        this.body = body;
    }

}
