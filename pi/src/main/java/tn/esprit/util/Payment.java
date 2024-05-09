package tn.esprit.util;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;

public class Payment {
    public static void main(String[] args) {
// Set your secret key here
        Stripe.apiKey = "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";

        try {
// Retrieve your account information
            Account account = Account.retrieve();
            System.out.println("Account ID: " + account.getId());
// Print other account information as needed
        } catch (StripeException e) {
            e.printStackTrace();
        }
    }
}
