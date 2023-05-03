package com.begoingto.springbootapi.api.account;

import com.begoingto.springbootapi.base.providers.AccountRelationProvider;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AccountMapper extends AccountRelationProvider {

}
