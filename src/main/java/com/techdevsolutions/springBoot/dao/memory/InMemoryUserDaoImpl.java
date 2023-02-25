package com.techdevsolutions.springBoot.dao.memory;

import com.techdevsolutions.springBoot.beans.Search;
import com.techdevsolutions.springBoot.beans.auditable.User;
import com.techdevsolutions.springBoot.dao.DaoCrudInterface;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryUserDaoImpl implements DaoCrudInterface<User> {
    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    protected Map<String, User> data = new HashMap<>();

    @Override
    public List<User> search(final Search search) throws IllegalArgumentException {
        if (search == null) {
            throw new IllegalArgumentException("Search object is null or empty");
        }

        return new ArrayList<>(this.data.values());
    }

    @Override
    public Optional<User> get(final String id) throws IllegalArgumentException {
        if (StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException("ID is null or empty");
        }

        return this.data.get(id) != null
                ? Optional.of(this.data.get(id))
                : Optional.empty();
    }

    @Override
    public User create(final String id, final User item) throws IllegalArgumentException {
        if (item == null) {
            throw new IllegalArgumentException("User is null");
        } else if (this.get(id).isPresent()) {
            throw new IllegalArgumentException("Item already exists");
        }

        this.data.put(id, item);

        return item;
    }

    @Override
    public void remove(final String id) throws IllegalArgumentException,NoSuchElementException {
        if (StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException("ID is null");
        } else if (this.get(id).isEmpty()) {
            throw new NoSuchElementException("Unable to find user");
        }

        this.delete(id);
    }

    @Override
    public void delete(final String id) throws IllegalArgumentException,NoSuchElementException {
        if (StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException("ID is null");
        } else if (this.get(id).isEmpty()) {
            throw new NoSuchElementException("Unable to find user");
        }

        this.data.remove(id);
    }

    @Override
    public User update(final String id, final User item) throws IllegalArgumentException,NoSuchElementException {
        if (item == null) {
            throw new IllegalArgumentException("User is null");
        }

        Optional<User> optional = this.get(id);

        if (optional.isEmpty()) {
            throw new NoSuchElementException("Unable to find user");
        }

        this.data.put(id, item);
        return item;
    }

    @Override
    public User upsert(final String id, final User item) throws IllegalArgumentException,NoSuchElementException {
        if (item == null) {
            throw new IllegalArgumentException("User is null");
        }

        Optional<User> optional = this.get(id);

        if (optional.isEmpty()) {
            this.create(id, item);
        } else {
            this.update(id, item);
        }

        return item;
    }

    @Override
    public Boolean verifyRemoval(final String id) throws IllegalArgumentException {
        Optional<User> optional = this.get(id);
        return optional.isEmpty();
    }

    @Override
    public void install() {
        this.data = new HashMap<>();
    }
}
