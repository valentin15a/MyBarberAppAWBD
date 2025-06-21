package com.awbd.mybarberapp.mappers;


import com.awbd.mybarberapp.dtos.AccountDTO;
import com.awbd.mybarberapp.domain.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "accountNonExpired", ignore = true)
    @Mapping(target = "accountNonLocked", ignore = true)
    @Mapping(target = "credentialsNonExpired", ignore = true)
    Account toAccount(AccountDTO dto);

    @Mapping(target = "confirmPassword", ignore = true)
    AccountDTO toDto(Account account);
}
