package com.techdevsolutions.springBoot.service;

import com.techdevsolutions.springBoot.beans.Search;
import com.techdevsolutions.springBoot.dao.memory.InMemoryGenericDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.*;

@Service
public class GenericServiceImpl implements GenericService {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    protected final InMemoryGenericDaoImpl dao;
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Autowired
    public GenericServiceImpl(final InMemoryGenericDaoImpl dao) {
        this.dao = dao;
    }

    @Override
    public List<Object> search(final Search search) throws IllegalArgumentException {
        return this.dao.search(search);
    }

    @Override
    public List<Object> getAll(final Search search) throws IllegalArgumentException {
        return this.dao.search(search);
    }

    @Override
    public Optional<Object> get(final String id) throws IllegalArgumentException {
        return this.dao.get(id);
    }

    @Override
    public Object create(final String id, final Object item) throws IllegalArgumentException, NoSuchElementException {
        Set<ConstraintViolation<Object>> violations = this.validator.validate(item);

        if (violations.size() > 0) {
            if (violations.size() != 1 && !violations.toString().contains("must not be blank', propertyPath=id")) {
                throw new IllegalArgumentException("Invalid item: " + violations);
            }
        }

        return this.dao.create(id, item);
    }

    @Override
    public void remove(final String id) throws IllegalArgumentException, NoSuchElementException {
        this.dao.remove(id);
    }

    @Override
    public void delete(final String id) throws IllegalArgumentException, NoSuchElementException {
        this.dao.delete(id);
    }

    @Override
    public Object update(final String id, final Object item) throws IllegalArgumentException, NoSuchElementException {
        Set<ConstraintViolation<Object>> violations = this.validator.validate(item);

        if (violations.size() > 0) {
            throw new IllegalArgumentException("Invalid item: " + violations);
        }

        return this.dao.update(id, item);
    }

    @Override
    public Object upsert(final String id, final Object item) throws IllegalArgumentException, NoSuchElementException {
        Set<ConstraintViolation<Object>> violations = this.validator.validate(item);

        if (violations.size() > 0) {
            throw new IllegalArgumentException("Invalid item: " + violations);
        }

        return this.dao.upsert(id, item);
    }

    @Override
    public void install() {
        this.dao.install();
    }
}
