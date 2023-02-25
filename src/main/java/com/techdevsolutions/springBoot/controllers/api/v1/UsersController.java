package com.techdevsolutions.springBoot.controllers.api.v1;

import com.techdevsolutions.springBoot.beans.Response;
import com.techdevsolutions.springBoot.beans.ResponseList;
import com.techdevsolutions.springBoot.beans.Search;
import com.techdevsolutions.springBoot.beans.auditable.User;
import com.techdevsolutions.springBoot.controllers.BaseController;
import com.techdevsolutions.springBoot.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "users-controller", description = "User CRUD")
public class UsersController extends BaseController {
    private final UserServiceImpl userService;

    @Autowired
    public UsersController(final UserServiceImpl userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    @Operation(summary = "Search")
    public ResponseList search(HttpServletRequest request,
                               @RequestParam(value = "term", required = false) Optional<String> term,
                               @RequestParam(value = "size", required = false) Optional<Integer> size,
                               @RequestParam(value = "page", required = false) Optional<Integer> page,
                               @RequestParam(value = "sort", required = false) Optional<String> sort,
                               @RequestParam(value = "order", required = false) Optional<String> order,
                               @RequestParam(value = "filters", required = false) Optional<String> filters,
                               @RequestParam(value = "filterLogic", required = false) Optional<String> filterLogic) {
        Search search = new Search();
        search.setTerm(term.orElse(""));
        search.setSize(size.orElse(Search.DEFAULT_SIZE));
        search.setPage(page.orElse(Search.DEFAULT_PAGE));
        search.setSort(sort.orElse("@timestamp"));
        search.setOrder(order.orElse(Search.DEFAULT_ORDER));
        search.setFilters(filters.orElse(""));
        search.setFilterLogic(filterLogic.orElse(Search.DEFAULT_FILTER_LOGIC));

        List<User> list = this.userService.search(search);
        return new ResponseList(list, this.getTimeTook(request));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @Operation(summary = "Get")
    public Response get(HttpServletRequest request,
                        @PathVariable String id,
                        HttpServletResponse response) {
        Optional<User> item = this.userService.get(id);

        if (item.isEmpty()) {
            throw new NoSuchElementException("Unable to find user by ID: " + id);
        }

        return new Response(item.get(), this.getTimeTook(request));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    @Operation(summary = "Create")
    public Response put(HttpServletRequest request,
                        @PathVariable String id,
                        @RequestBody User data) {
        User newItem = this.userService.create(id, data);
        return new Response(newItem, this.getTimeTook(request));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @Operation(summary = "Update")
    public Response update(HttpServletRequest request,
                           @PathVariable String id,
                           @RequestBody User data) {
        User updatedItem = this.userService.update(id, data);
        return new Response(updatedItem, this.getTimeTook(request));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @Operation(summary = "Delete")
    public Response delete(HttpServletRequest request,
                         @PathVariable String id,
                         @RequestParam(value = "false", required = false) Optional<String> delete) {
        if (delete != null && delete.isPresent()) {
            this.userService.delete(id);
        } else {
            this.userService.remove(id);
        }

        return new Response(null, this.getTimeTook(request));
    }
}
