package com.terraboxstudios.publicsms.message.outbound.senders;

import com.clockworksms.ClockWorkSmsService;
import com.clockworksms.ClockworkException;
import com.clockworksms.ClockworkSmsResult;
import com.clockworksms.SMS;
import com.terraboxstudios.publicsms.message.outbound.OutboundMessage;
import com.terraboxstudios.publicsms.message.outbound.OutboundMessageSender;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ClockworkOutboundMessageSender implements OutboundMessageSender<ClockworkSmsResult> {

    private final String apiKey;

    @Override
    public ClockworkSmsResult send(OutboundMessage outboundMessage) throws ClockworkException {
        ClockWorkSmsService clockWorkSmsService = new ClockWorkSmsService(apiKey);
        SMS smsMessage = new SMS();
        smsMessage.setTo(outboundMessage.getReceiver().getNumber());
        smsMessage.setFrom(outboundMessage.getSender().getNumber());
        if (outboundMessage.getBody().length() > 160) smsMessage.setLongMessage(true);
        smsMessage.setMessage(outboundMessage.getBody());
        return clockWorkSmsService.send(smsMessage);
    }

}
