package com.techdevsolutions.springBoot.controllers;

import com.techdevsolutions.common.beans.Filter;
import com.techdevsolutions.common.beans.Search;
import com.techdevsolutions.common.service.generic.GenericService;
import com.techdevsolutions.springBoot.beans.Response;
import com.techdevsolutions.springBoot.beans.ResponseList;
import com.techdevsolutions.springBoot.service.GenericServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@EnableAutoConfiguration
@RequestMapping("/api/v1/generic")
@Tag(name = "generic", description = "Generic CRUD")
public class GenericController extends BaseController {
    private GenericService genericService;

    @Autowired
    public GenericController(GenericServiceImpl genericService) {
        this.genericService = genericService;
    }

    @ResponseBody
    @RequestMapping(value = "search", method = RequestMethod.GET)
    public Object search(HttpServletRequest request,
                        @RequestParam(value = "term") Optional<String> term,
                        @RequestParam(value = "size") Optional<Integer> size,
                        @RequestParam(value = "page") Optional<Integer> page,
                        @RequestParam(value = "sort") Optional<String> sort,
                        @RequestParam(value = "order") Optional<String> order,
                        @RequestParam(value = "filters") Optional<String> filters,
                        @RequestParam(value = "filterLogic") Optional<String> filterLogic) {
        try {
            Search search = new Search();
            search.setTerm((term.isPresent()) ? term.get() : "");
            search.setSize((size.isPresent()) ? size.get() : Search.DEFAULT_SIZE);
            search.setPage((page.isPresent()) ? page.get() : Search.DEFAULT_PAGE);
            search.setSort((sort.isPresent()) ? sort.get() : Search.DEFAULT_SORT);
            search.setOrder((order.isPresent()) ? order.get() : Search.DEFAULT_ORDER);
            search.setFilters((filters.isPresent()) ? filters.get() : "");
            search.setFilterLogic((filterLogic.isPresent()) ? filterLogic.get() : Search.DEFAULT_FILTER_LOGIC);

            List<Map> list = this.genericService.search(search);
            return new ResponseList(list, this.getTimeTook(request));
        } catch (Exception e) {
            e.printStackTrace();
            return this.generateErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR, e.toString());
        }
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object getAll(HttpServletRequest request,
                         @RequestParam(value = "size") Optional<Integer> size,
                         @RequestParam(value = "page") Optional<Integer> page,
                         @RequestParam(value = "sort") Optional<String> sort,
                         @RequestParam(value = "order") Optional<String> order,
                         @RequestParam(value = "filters") Optional<String> filters,
                         @RequestParam(value = "filterLogic") Optional<String> filterLogic) {
        try {
            Search search = new Search();
            search.setSize((size.isPresent()) ? size.get() : Search.DEFAULT_SIZE);
            search.setPage((page.isPresent()) ? page.get() : Search.DEFAULT_PAGE);
            search.setSort((sort.isPresent()) ? sort.get() : Search.DEFAULT_SORT);
            search.setOrder((order.isPresent()) ? order.get() : Search.DEFAULT_ORDER);
            search.setFilters((filters.isPresent()) ? filters.get() : "");
            search.setFilterLogic((filterLogic.isPresent()) ? filterLogic.get() : Search.DEFAULT_FILTER_LOGIC);

            List<Map> list = this.genericService.search(search);
            return new ResponseList(list, this.getTimeTook(request));
        } catch (Exception e) {
            e.printStackTrace();
            return this.generateErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR, e.toString());
        }
    }

    @ResponseBody
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Object get(HttpServletRequest request, @PathVariable String id, HttpServletResponse response) {
        try {
            Map item = this.genericService.get(id);

            if (item != null) {
                return new Response(item, this.getTimeTook(request));
            } else {
                return this.generateErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR,
                        "GenericController - get - Item was not found using ID: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return this.generateErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR, e.toString());
        }
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Object put(HttpServletRequest request, @RequestBody Map data) {
        try {
            Map newItem = this.genericService.create(data);
            return new Response(newItem, this.getTimeTook(request));
        } catch (Exception e) {
            e.printStackTrace();
            return this.generateErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR, e.toString());
        }
    }

    @ResponseBody
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Object update(HttpServletRequest request, @PathVariable String id, @RequestBody Map data) {
        try {
            Map updatedItem = this.genericService.update(data);

            if (updatedItem != null) {
                return new Response(updatedItem, this.getTimeTook(request));
            } else {
                return new Response("Unable to find item: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return this.generateErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR, e.toString());
        }
    }

    @ResponseBody
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public Object delete(HttpServletRequest request, @PathVariable String id,
                         @RequestParam(value = "false") Optional<String> delete) {
        try {
            if (delete != null && delete.isPresent()) {
                this.genericService.delete(id);
            } else {
                this.genericService.remove(id);
            }

            return new Response(null, this.getTimeTook(request));
        } catch (Exception e) {
            e.printStackTrace();
            return this.generateErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR, e.toString());
        }
    }
}
