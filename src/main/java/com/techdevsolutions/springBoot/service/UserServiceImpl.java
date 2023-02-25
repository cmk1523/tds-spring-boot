package com.techdevsolutions.springBoot.service;

import com.techdevsolutions.springBoot.beans.Search;
import com.techdevsolutions.springBoot.beans.auditable.User;
import com.techdevsolutions.springBoot.dao.memory.InMemoryGenericDaoImpl;
import com.techdevsolutions.springBoot.dao.memory.InMemoryUserDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements GenericService<User> {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    protected final InMemoryUserDaoImpl dao;
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Autowired
    public UserServiceImpl(final InMemoryUserDaoImpl dao) {
        this.dao = dao;
    }

    @Override
    public List<User> search(final Search search) throws IllegalArgumentException {
        return this.dao.search(search);
    }

    @Override
    public List<User> getAll(final Search search) throws IllegalArgumentException {
        return this.dao.search(search);
    }

    @Override
    public Optional<User> get(final String id) throws IllegalArgumentException {
        return this.dao.get(id);
    }

    @Override
    public User create(final String id, final User item) throws IllegalArgumentException, NoSuchElementException {
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
    public User update(final String id, final User item) throws IllegalArgumentException, NoSuchElementException {
        Set<ConstraintViolation<Object>> violations = this.validator.validate(item);

        if (violations.size() > 0) {
            throw new IllegalArgumentException("Invalid item: " + violations);
        }

        return this.dao.update(id, item);
    }

    @Override
    public User upsert(final String id, final User item) throws IllegalArgumentException, NoSuchElementException {
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
