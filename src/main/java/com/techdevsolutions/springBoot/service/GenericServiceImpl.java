package com.techdevsolutions.springBoot.service;

import com.techdevsolutions.common.dao.DaoCrudInterface;
import com.techdevsolutions.common.service.core.Timer;
import com.techdevsolutions.springBoot.dao.ElasticsearchGenericDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Map;
import java.util.Set;

@Service
public class
GenericServiceImpl extends com.techdevsolutions.common.service.generic.GenericServiceImpl {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Autowired
    public GenericServiceImpl(ElasticsearchGenericDaoImpl dao) {
        this.dao = dao;
    }

    @Override
    public Map create(Map item) throws Exception {
        Timer timer = new Timer().start();

        if (item == null) {
            throw new Exception("item is null");
        }

        Set<ConstraintViolation<Map<String, Object>>> violations = this.validator.validate(item);

        if (violations.size() > 0) {
            if (violations.size() != 1 && !violations.toString().contains("must not be blank', propertyPath=id")) {
                throw new Exception("Invalid item: " + violations.toString());
            }
        }

        Map<String, Object> created = this.dao.create(item);
        this.logger.info("Created item by ID: " + item.get("id") + " in " + timer.stopAndGetDiff() + " ms");
        return created;
    }
}
