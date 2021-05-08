package com.terraboxstudios.publicsms;

import com.terraboxstudios.publicsms.phone.PublicPhone;
import com.terraboxstudios.publicsms.phone.PublicPhoneGenerator;
import com.terraboxstudios.publicsms.phone.PublicPhoneSource;
import com.terraboxstudios.publicsms.phone.sources.OSIOPublicPhoneSource;
import com.terraboxstudios.publicsms.phone.sources.RFSPublicPhoneSource;
import com.terraboxstudios.publicsms.services.InboundMessageListener;
import com.terraboxstudios.publicsms.services.InboundMessageService;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class PhoneListenerTest {

    public static void main(String[] args) {

        PublicPhoneSource rfsPublicPhoneSource = new RFSPublicPhoneSource();
        PublicPhoneSource osioPublicPhoneSource = new OSIOPublicPhoneSource();

        Collection<PublicPhoneSource> publicPhoneSources = new HashSet<>();
        publicPhoneSources.add(rfsPublicPhoneSource);
        publicPhoneSources.add(osioPublicPhoneSource);

        PublicPhoneGenerator publicPhoneGenerator = new PublicPhoneGenerator(publicPhoneSources);
        Optional<PublicPhone> publicPhone = publicPhoneGenerator.getRandomPublicPhone();
        publicPhone.ifPresent(phone -> System.out.println(phone.getNumber()));

        InboundMessageListener listener = (publicPhone1, inboundMessage) -> System.out.println(inboundMessage.getSender().getNumber() + " -> " + inboundMessage.getBody());
        Collection<InboundMessageListener> listeners = new HashSet<>();
        listeners.add(listener);
        InboundMessageService inboundMessageService = new InboundMessageService(1, listeners, publicPhone.get());
        inboundMessageService.start(0, 5, TimeUnit.SECONDS);
    }

}
