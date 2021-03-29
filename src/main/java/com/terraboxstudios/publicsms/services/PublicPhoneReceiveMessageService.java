package com.terraboxstudios.publicsms.services;

import com.terraboxstudios.publicsms.message.Message;
import com.terraboxstudios.publicsms.phone.PublicPhone;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PublicPhoneReceiveMessageService implements Runnable {

    private final ScheduledExecutorService scheduledExecutorService;
    private final Collection<PublicPhoneReceiveMessageListener> listeners;
    private final PublicPhone publicPhone;

    public PublicPhoneReceiveMessageService(int corePoolSize, Collection<PublicPhoneReceiveMessageListener> listeners, PublicPhone publicPhone) {
        this.listeners = Collections.synchronizedCollection(new HashSet<>());
        if (listeners != null) this.listeners.addAll(listeners);
        this.publicPhone = publicPhone;
        scheduledExecutorService = Executors.newScheduledThreadPool(corePoolSize);
    }

    public void start(long initialDelay, long delay, TimeUnit timeUnit) {
        scheduledExecutorService.scheduleWithFixedDelay(this, initialDelay, delay, timeUnit);
    }

    Collection<Message> receivedMessages = new HashSet<>();
    @Override
    public void run() {
        try {
            Collection<Message> messages = getPublicPhone().getMessages();
            messages.stream().filter(msg -> !receivedMessages.contains(msg)).forEach(msg -> {
                callReceiveMessageEvent(getPublicPhone(), msg);
                receivedMessages.add(msg);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addListener(PublicPhoneReceiveMessageListener listener) {
        synchronized (listeners) {
            listeners.add(listener);
        }
    }

    public void removeListener(PublicPhoneReceiveMessageListener listener) {
        synchronized (listeners) {
            listeners.remove(listener);
        }
    }

    public Collection<PublicPhoneReceiveMessageListener> getListeners() {
        synchronized (listeners) {
            return listeners;
        }
    }

    public void callReceiveMessageEvent(PublicPhone publicPhone, Message message) {
        synchronized (listeners) {
            for (PublicPhoneReceiveMessageListener listener : listeners) {
                listener.onReceiveMessage(publicPhone, message);
            }
        }
    }

    public PublicPhone getPublicPhone() {
        synchronized (publicPhone) {
            return publicPhone;
        }
    }

}
