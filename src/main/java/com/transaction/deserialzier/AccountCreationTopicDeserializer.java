package com.transaction.deserialzier;

import com.google.gson.Gson;
import com.transaction.domain.Account;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;

public class AccountCreationTopicDeserializer implements Deserializer {

    @Override
    public Account deserialize(String s, byte[] bytes) {
        try {
            if (bytes == null) {
                return null;
            }
            String data = new String(bytes, StandardCharsets.UTF_8);
            return new Gson().fromJson(data, Account.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing");
        }
    }


}
