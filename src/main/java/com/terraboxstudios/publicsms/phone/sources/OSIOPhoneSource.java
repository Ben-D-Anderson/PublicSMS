package com.terraboxstudios.publicsms.phone.sources;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.terraboxstudios.publicsms.message.Message;
import com.terraboxstudios.publicsms.phone.PhoneSource;
import com.terraboxstudios.publicsms.phone.PublicPhone;
import com.terraboxstudios.publicsms.utils.HttpUtility;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

public class OSIOPhoneSource implements PhoneSource {

    @Override
    public Collection<PublicPhone> getPhoneNumbers() throws IOException {
        String countriesResponseStr = HttpUtility.readSingleLineRespone(HttpUtility.sendGetRequest("https://onlinesim.io/api/getFreeList?lang=en"));
        JsonArray countries = JsonParser.parseString(countriesResponseStr).getAsJsonObject().get("countries").getAsJsonArray();
        Collection<Integer> countryNumberCodes = new HashSet<>();
        for (JsonElement country : countries) {
            countryNumberCodes.add(country.getAsJsonObject().get("country").getAsInt());
        }
        Collection<PublicPhone> publicPhones = new HashSet<>();
        for (int numCode : countryNumberCodes) {
            String numbersResponseStr = HttpUtility.readSingleLineRespone(HttpUtility.sendGetRequest("https://onlinesim.io/api/getFreeList?lang=en&country=" + numCode));
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
    public Collection<Message> getMessages(PublicPhone receivingPhone) throws IOException {
        return null;
    }

}
