package tn.esprit.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SmsSender {

    public static final String ACCOUNT_SID = "AC6e028f0e867140359433f31b5644381c";
    public static final String AUTH_TOKEN = "d0d0159e0a38344560f74fa5547bfffd";
    public static final String TWILIO_PHONE_NUMBER = "+15414352225";


    public static void sendSMS(String toPhoneNumber, String message) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message.creator(
                        new PhoneNumber(toPhoneNumber),
                        new PhoneNumber(TWILIO_PHONE_NUMBER),
                        message)
                .create();
    }
}