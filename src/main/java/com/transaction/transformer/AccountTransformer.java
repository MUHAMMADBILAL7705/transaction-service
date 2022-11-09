package com.transaction.transformer;

import com.transaction.domain.Account;
import com.transaction.model.AccountModel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class AccountTransformer implements Transformer<AccountModel, Account> {

    @Override
    public AccountModel toModel(Account entity) {
        if (entity == null)
            return null;
        else
            return AccountModel.builder()
                    .id(entity.getId())
                    .email(entity.getEmail())
                    .name(entity.getName())
                    .build();
    }

    @Override
    public Account toEntity(AccountModel model) {
        if (model == null)
            return null;
        else
            return Account.builder()
                    .email(model.getEmail())
                    .name(model.getName())
                    .build();
    }
}
