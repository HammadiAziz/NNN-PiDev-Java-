package tn.esprit.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SmsSender {

    public static final String ACCOUNT_SID = "ACcf4ca0cdba0661499e054841b9d86b76";
    public static final String AUTH_TOKEN = "a059e35e37eb3b3950129fe7ac274a4f";
    public static final String TWILIO_PHONE_NUMBER = "+14052945351";



    public static void sendSMS(String toPhoneNumber, String message) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message.creator(
                        new PhoneNumber(toPhoneNumber),
                        new PhoneNumber(TWILIO_PHONE_NUMBER),
                        message)
                .create();
    }
}