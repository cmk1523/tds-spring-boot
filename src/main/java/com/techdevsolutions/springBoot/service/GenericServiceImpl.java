package com.techdevsolutions.springBoot.service;

import com.techdevsolutions.common.dao.DaoCrudInterface;
import com.techdevsolutions.springBoot.dao.InMemoryGenericDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GenericServiceImpl extends com.techdevsolutions.common.service.generic.GenericServiceImpl {

    @Autowired
    public GenericServiceImpl(InMemoryGenericDaoImpl dao) {
        super(dao);
    }
}
