package com.terraboxstudios.publicsms.message.inbound;

import com.terraboxstudios.publicsms.phone.PublicPhone;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class InboundMessageService implements Runnable {

    private final ScheduledExecutorService scheduledExecutorService;
    private final Collection<InboundMessageListener> listeners;
    private final PublicPhone publicPhone;

    public InboundMessageService(PublicPhone publicPhone) {
        this(1, null, publicPhone);
    }

    public InboundMessageService(Collection<InboundMessageListener> listeners, PublicPhone publicPhone) {
        this(1, listeners, publicPhone);
    }

    public InboundMessageService(int corePoolSize, Collection<InboundMessageListener> listeners, PublicPhone publicPhone) {
        this.listeners = Collections.synchronizedCollection(new HashSet<>());
        if (listeners != null) this.listeners.addAll(listeners);
        this.publicPhone = publicPhone;
        scheduledExecutorService = Executors.newScheduledThreadPool(corePoolSize);
    }

    public void start(long initialDelay, long delay, TimeUnit timeUnit) {
        scheduledExecutorService.scheduleWithFixedDelay(this, initialDelay, delay, timeUnit);
    }

    Collection<InboundMessage> receivedInboundMessages = new HashSet<>();
    @Override
    public void run() {
        try {
            Collection<InboundMessage> inboundMessages = getPublicPhone().getMessages();
            inboundMessages.stream().filter(msg -> !receivedInboundMessages.contains(msg)).forEach(msg -> {
                notifyListeners(getPublicPhone(), msg);
                receivedInboundMessages.add(msg);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addListener(InboundMessageListener listener) {
        synchronized (listeners) {
            listeners.add(listener);
        }
    }

    public void removeListener(InboundMessageListener listener) {
        synchronized (listeners) {
            listeners.remove(listener);
        }
    }

    public Collection<InboundMessageListener> getListeners() {
        synchronized (listeners) {
            return listeners;
        }
    }

    public void notifyListeners(PublicPhone publicPhone, InboundMessage inboundMessage) {
        synchronized (listeners) {
            for (InboundMessageListener listener : listeners) {
                listener.onReceiveMessage(publicPhone, inboundMessage);
            }
        }
    }

    public PublicPhone getPublicPhone() {
        synchronized (publicPhone) {
            return publicPhone;
        }
    }

}
