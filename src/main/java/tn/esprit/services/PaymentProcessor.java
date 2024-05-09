package tn.esprit.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerRetrieveParams;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.TokenCreateParams;

import java.util.HashMap;
import java.util.Map;

public class PaymentProcessor {

    private static final String STRIPE_API_KEY = "sk_test_51OopjxL84P7BeAdh8bDGXMt3jzWbTsE8c0NSmP3M8jutSVYdYXQlnkElOuiB9PLSPkPSR9BKZ9ARMXE7lTASquzc00T2ZhCb5c"; // your Stripe test API key (replace with live key for production)

    public static boolean processPayment(float amount, String currency, String email) throws StripeException {
        boolean result = false;

        // Convert amount to cents for Stripe API (adjust conversion if needed)
        Long conversion = (long) (amount * 100);

        // Set your secret key
        Stripe.apiKey = STRIPE_API_KEY;

        // Check if the customer exists
        Customer existingCustomer = getCustomerByEmail(email);
        if (existingCustomer == null) {
            // Create a new customer if they do not exist
            CustomerCreateParams customerCreateParams = CustomerCreateParams.builder()
                    .setEmail(email)
                    .setPaymentMethod("pm_card_visa")
                    .build();
            existingCustomer = Customer.create(customerCreateParams);
        }

        // Create a PaymentIntent with the amount and currency
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(conversion) // Amount in cents
                .setCurrency(currency)
                .setPaymentMethod("pm_card_visa")
                .setCustomer(existingCustomer.getId()) // Use the existing or newly created customer
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);

        // Confirm the PaymentIntent
        Map<String, Object> confirmParams = new HashMap<>();
        confirmParams.put("payment_method", "pm_card_visa"); // Use the default payment method for now
        confirmParams.put("receipt_email", email);
        confirmParams.put("return_url", "https://google.com");
        paymentIntent = paymentIntent.confirm(confirmParams);

        // Check if the payment was successful
        if (paymentIntent.getStatus().equals("succeeded")) {
            System.out.println("Payment successful!");
            result = true;
        } else {
            System.out.println("Payment failed!");
        }
        return result;
    }

    private static Customer getCustomerByEmail(String email) throws StripeException {
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        CustomerCollection customers = Customer.list(params);
        if (customers.getData().isEmpty()) {
            return null;
        } else {
            return customers.getData().get(0);
        }
    }
}