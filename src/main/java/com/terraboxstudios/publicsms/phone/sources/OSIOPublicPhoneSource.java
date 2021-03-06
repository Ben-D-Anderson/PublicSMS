package com.terraboxstudios.publicsms.phone.sources;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.terraboxstudios.publicsms.message.inbound.InboundMessage;
import com.terraboxstudios.publicsms.phone.Phone;
import com.terraboxstudios.publicsms.phone.PublicPhoneSource;
import com.terraboxstudios.publicsms.phone.PublicPhone;
import com.terraboxstudios.publicsms.utils.HttpUtility;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

public class OSIOPublicPhoneSource implements PublicPhoneSource {

    @Override
    public Collection<PublicPhone> getPhoneNumbers() throws IOException {
        String countriesResponseStr = HttpUtility.readSingleLineResponse(HttpUtility.sendGetRequest("https://onlinesim.io/api/getFreeList?lang=en"));
        JsonArray countries = JsonParser.parseString(countriesResponseStr).getAsJsonObject().get("countries").getAsJsonArray();
        Collection<Integer> countryNumberCodes = new HashSet<>();
        for (JsonElement country : countries) {
            countryNumberCodes.add(country.getAsJsonObject().get("country").getAsInt());
        }
        Collection<PublicPhone> publicPhones = new HashSet<>();
        for (int numCode : countryNumberCodes) {
            String numbersResponseStr = HttpUtility.readSingleLineResponse(HttpUtility.sendGetRequest("https://onlinesim.io/api/getFreeList?lang=en&country=" + numCode));
            JsonObject numbers = JsonParser.parseString(numbersResponseStr).getAsJsonObject().get("numbers").getAsJsonObject();
            for (String key : numbers.keySet()) {
                String numStr = numbers.get(key).getAsJsonObject().get("full_number").getAsString();
                PublicPhone publicPhone;
                try {
                    publicPhone = new PublicPhone(numStr, PhoneNumberUtil.getInstance().getRegionCodeForNumber(PhoneNumberUtil.getInstance().parse(numStr, "")), this);
                    publicPhones.add(publicPhone);
                } catch (NumberParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return publicPhones;
    }

    @Override
    public Collection<InboundMessage> getMessages(PublicPhone receivingPhone) throws IOException {
        int countryCodeInt = PhoneNumberUtil.getInstance().getCountryCodeForRegion(receivingPhone.getCountryCode());
        String countryCodeStr = "+" + countryCodeInt;
        String shortenedNumber = receivingPhone.getNumber().replace(countryCodeStr, "");
        String responseStr = HttpUtility.readSingleLineResponse(HttpUtility.sendGetRequest("https://onlinesim.io/api/getFreeList?lang=en&country=" + countryCodeInt + "&number=" + shortenedNumber));
        JsonArray messagesJson = JsonParser.parseString(responseStr).getAsJsonObject().get("messages").getAsJsonObject().get("data").getAsJsonArray();
        Collection<InboundMessage> inboundMessages = new HashSet<>();
        for (JsonElement msgElement : messagesJson) {
            JsonObject msgObj = msgElement.getAsJsonObject();
            String fromStr = msgObj.get("in_number").getAsString();
            Phone sender;
            try {
                sender = new Phone(fromStr, PhoneNumberUtil.getInstance().getRegionCodeForNumber(PhoneNumberUtil.getInstance().parse(fromStr, receivingPhone.getCountryCode())));
            } catch (NumberParseException e) {
                sender = new Phone(fromStr, receivingPhone.getCountryCode());
            }
            Date date = null;
            try {
                date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(msgObj.get("created_at").getAsString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            InboundMessage inboundMessage = InboundMessage.from(sender, receivingPhone, date, msgObj.get("text").getAsString());
            inboundMessages.add(inboundMessage);
        }
        return inboundMessages;
    }

}
