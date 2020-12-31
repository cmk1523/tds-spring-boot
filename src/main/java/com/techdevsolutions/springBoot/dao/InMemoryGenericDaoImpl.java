package com.techdevsolutions.springBoot.dao;

import com.techdevsolutions.common.beans.Search;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InMemoryGenericDaoImpl extends com.techdevsolutions.common.dao.memory.InMemoryGenericDaoImpl {
    @Override
    public List<Map> search(Search search) throws Exception {
        List<Map> list = new ArrayList<>(this.data.values());

        if (search.getSize() >= 0) {
            list = list.stream().limit(search.getSize()).collect(Collectors.toList());
        }

        return list;
    }
}
