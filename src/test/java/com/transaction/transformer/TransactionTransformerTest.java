package com.transaction.transformer;

import com.transaction.domain.Transaction;
import com.transaction.enums.TransactionType;
import com.transaction.model.TransactionModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

class TransactionTransformerTest {

    private TransactionTransformer transactionTransformer;

    @BeforeEach
    void setup() {
        this.transactionTransformer = new TransactionTransformer();
    }

    @Test
    void whenNullModelGivenShouldReturnNull() {
        //given model = null;
        //when
        Transaction entity = transactionTransformer.toEntity(null);
        //then
        Assertions.assertNull(entity);
    }

    @Test
    void whenModelGivenShouldReturnEntity() {
        TransactionModel model = TransactionModel.builder()
                .amount(new BigDecimal(100))
                .description("test")
                .purpose("USB")
                .creditAccount("1234")
                .transactionType(TransactionType.DEBIT)
                .build();
        Transaction entity = transactionTransformer.toEntity(model);
        Assertions.assertNotNull(entity);
        Assertions.assertEquals("test", entity.getDescription());
        Assertions.assertEquals("USB", entity.getPurpose());
        Assertions.assertEquals("1234", entity.getCreditAccount());
        Assertions.assertEquals(TransactionType.DEBIT, entity.getType());
        Assertions.assertNotNull(entity.getReference());
        Assertions.assertNotNull(entity.getTransactionDate());
    }

    @Test
    void whenNullEntityGivenShouldReturnNull() {
        //given entity = null;
        //when
        TransactionModel model = transactionTransformer.toModel(null);
        //then
        Assertions.assertNull(model);
    }

    @Test
    void whenEntityGivenShouldReturnModel() {
        Transaction entity = Transaction.builder()
                .amount(new BigDecimal(100))
                .description("test")
                .purpose("USB")
                .creditAccount("1234")
                .type(TransactionType.DEBIT)
                .reference("123")
                .transactionDate(LocalDateTime.now())
                .id(1L)
                .build();
        TransactionModel model = transactionTransformer.toModel(entity);
        Assertions.assertNotNull(model);
        Assertions.assertEquals("test", model.getDescription());
        Assertions.assertEquals("USB", model.getPurpose());
        Assertions.assertEquals("1234", model.getCreditAccount());
        Assertions.assertEquals(TransactionType.DEBIT, model.getTransactionType());
    }

}