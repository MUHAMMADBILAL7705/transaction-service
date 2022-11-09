package com.transaction.deserialzier;

import com.transaction.domain.Account;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

class AccountCreationTopicDeserializerTest {

    private AccountCreationTopicDeserializer deserializer;

    @BeforeEach
    void setup() {
        this.deserializer = new AccountCreationTopicDeserializer();
    }

    @Test
    void givenAccountBytesShouldReturnAccount() {
        JSONObject obj = new JSONObject();
        obj.put("id", 123);
        obj.put("name", "bilal");
        obj.put("email", "test@test.com");
        byte[] account = obj.toString().getBytes(StandardCharsets.UTF_8);
        Account deserializeAccount = deserializer.deserialize("", account);
        Assertions.assertNotNull(deserializeAccount);
        Assertions.assertEquals(123, deserializeAccount.getId());
        Assertions.assertEquals("bilal", deserializeAccount.getName());
        Assertions.assertEquals("test@test.com", deserializeAccount.getEmail());
    }

}