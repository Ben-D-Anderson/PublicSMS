package com.terraboxstudios.publicsms.phone.sources;

import com.google.gson.*;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.terraboxstudios.publicsms.message.Message;
import com.terraboxstudios.publicsms.phone.Phone;
import com.terraboxstudios.publicsms.phone.PublicPhoneSource;
import com.terraboxstudios.publicsms.phone.PublicPhone;
import com.terraboxstudios.publicsms.utils.HttpUtility;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class RFSPublicPhoneSource implements PublicPhoneSource {

    @Override
    public Collection<PublicPhone> getPhoneNumbers() throws IOException {
        String response = HttpUtility.readSingleLineRespone(HttpUtility.sendGetRequest("https://receive-free-sms.com/api/number/phonepagelist"));
        JsonArray numbers = JsonParser.parseString(response).getAsJsonArray();
        Collection<PublicPhone> publicPhones = new HashSet<>();
        numbers.forEach(phone -> {
            if (phone.getAsJsonObject().get("status").getAsString().equalsIgnoreCase("active")) {
                publicPhones.add(new PublicPhone(phone.getAsJsonObject().get("number").getAsString(), phone.getAsJsonObject().get("shortCode").getAsString(), this));
            }
        });
        return publicPhones;
    }

    @Override
    public Collection<Message> getMessages(PublicPhone receivingPhone) throws IOException {
        JsonObject payload = new JsonObject();
        payload.addProperty("number", receivingPhone.getNumber());
        HttpURLConnection httpURLConnection = HttpUtility.sendPostRequest("https://receive-free-sms.com/api/number/phone", payload);
        String response = HttpUtility.readSingleLineRespone(httpURLConnection);
        JsonArray messagesJson = JsonParser.parseString(response).getAsJsonArray().get(0).getAsJsonObject().get("messages").getAsJsonArray();
        final List<Message> messages = new LinkedList<>();
        messagesJson.forEach(msgJson -> {
            String fromStr = msgJson.getAsJsonObject().get("from").getAsString();
            Phone sender;
            try {
                sender = new Phone(fromStr, PhoneNumberUtil.getInstance().getRegionCodeForNumber(PhoneNumberUtil.getInstance().parse(fromStr, receivingPhone.getCountryCode())));
            } catch (NumberParseException e) {
                sender = new Phone(fromStr, receivingPhone.getCountryCode());
            }
            Date date = null;
            try {
                date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(msgJson.getAsJsonObject().get("sentAt").getAsString().replace("T", " ").replace("Z", ""));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            messages.add(Message.from(sender, receivingPhone, date, msgJson.getAsJsonObject().get("body").getAsString()));
        });
        Collections.reverse(messages);
        return messages;
    }

}
