package com.transaction.transformer;


import com.transaction.domain.Account;
import com.transaction.model.AccountModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccountTransformerTest {

    private AccountTransformer accountTransformer;

    @BeforeEach
    void setup() {
        accountTransformer = new AccountTransformer();
    }

    @Test
    void whenNullAccountModelShouldReturnNullEntity() {
        //given accountModel = null;
        //when
        Account account = accountTransformer.toEntity(null);
        //then
        Assertions.assertNull(account);
    }

    @Test
    void whenAccountModelShouldReturnNull() {
        //given
        AccountModel accountModel = AccountModel.builder()
                .name("bilal")
                .email("test@test.com")
                .build();
        //when
        Account account = accountTransformer.toEntity(accountModel);
        //then
        Assertions.assertNotNull(account);
        Assertions.assertEquals("bilal", account.getName());
        Assertions.assertEquals("test@test.com", account.getEmail());
    }

    @Test
    void whenNullAccountShouldReturnEmptyModel() {
        //given
        Account account = null;
        //when
        AccountModel accountModel = accountTransformer.toModel(account);
        //then
        Assertions.assertNull(accountModel);
    }

    @Test
    void whenAccountShouldReturnConvertedModel() {
        //given
        Account account = Account.builder()
                .id(123L)
                .name("bilal")
                .email("test@test.com")
                .build();
        //when
        AccountModel accountModel = accountTransformer.toModel(account);
        //then
        Assertions.assertNotNull(accountModel);
        Assertions.assertEquals(123L, accountModel.getId());
        Assertions.assertEquals("bilal", accountModel.getName());
        Assertions.assertEquals("test@test.com", accountModel.getEmail());
    }

}