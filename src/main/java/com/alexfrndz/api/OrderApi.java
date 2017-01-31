package com.alexfrndz.api;

import com.alexfrndz.pojo.Order;
import com.alexfrndz.pojo.response.handlers.PaginationSearchRequest;
import com.alexfrndz.pojo.response.handlers.SearchResponse;
import com.alexfrndz.pojo.response.handlers.SuccessMessageResponse;
import com.alexfrndz.service.OrderServiceImpl;
import com.alexfrndz.validator.OrderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController("OrderAPI")
@RequestMapping("/api/v1")
public class OrderApi extends BaseApi {

    @Autowired
    private OrderServiceImpl orderService;

    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    @ResponseBody
    public SuccessMessageResponse create(@RequestBody @Valid Order order) {
        return orderService.acceptOrder(order);
    }

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    @ResponseBody
    public SearchResponse<Order> getOrders(
            @RequestParam(value = "query", required = false, defaultValue = "") String query,
            @ModelAttribute @Validated PaginationSearchRequest searchRequest) {
        return orderService.search(query, searchRequest);
    }

    @RequestMapping(value = "orders/{orderId}", method = RequestMethod.GET)
    @ResponseBody
    public Order getMessage(@PathVariable Long orderId) {
        return orderService.getOrder(orderId);
    }

    @InitBinder("order")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(new OrderValidator());
    }
}