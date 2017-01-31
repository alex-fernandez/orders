package com.alexfrndz.service;

import com.alexfrndz.converter.utils.ConverterBasedTransformer;
import com.alexfrndz.converter.utils.XMLConverterUtils;
import com.alexfrndz.domain.OrderEntity;
import com.alexfrndz.domain.OrderLineEntity;
import com.alexfrndz.pojo.Order;
import com.alexfrndz.pojo.OrderLine;
import com.alexfrndz.pojo.exception.NotFoundException;
import com.alexfrndz.pojo.response.handlers.PaginationSearchRequest;
import com.alexfrndz.pojo.response.handlers.SearchResponse;
import com.alexfrndz.pojo.response.handlers.SuccessMessageResponse;
import com.alexfrndz.repository.OrderRepository;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@Data
public class OrderServiceImpl extends AbstractService implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ConverterBasedTransformer<OrderEntity, Order> orderEntityOrderConverterBasedTransformer;

    @Qualifier("pojoToXMLMarshaller")
    @Autowired
    public Jaxb2Marshaller xmlMarshaller;

    @Resource
    private Environment env;


    /**
     * Validation are done by spring using javax.validation
     * @param order
     * @return
     */
    @Override
    public SuccessMessageResponse acceptOrder(Order order) {

        String basePath = env.getRequiredProperty("app.order.path");
        String fileExtension = env.getRequiredProperty("app.order.fileExt");

        String fileName = String.format("%s/%s.%s", basePath, UUID.randomUUID(), fileExtension);
        log.info("FileName {}", fileName);
        try {
            XMLConverterUtils.objectToXML(xmlMarshaller, fileName, order);
        } catch (IOException e) {
            log.error("Unable to create XML marshaller: ", e);
            throw new IllegalArgumentException("Unable to generate the file {}" + fileName, e);
        }
        return new SuccessMessageResponse("Ok", "Order accepted.");
    }

    @Override
    @Transactional
    public OrderEntity createOrder(Order order) {
        OrderEntity orderTargetEntity = everyClickConversionService.convert(order, OrderEntity.class);
        orderTargetEntity = orderRepository.save(orderTargetEntity);

        List<OrderLineEntity> contactEntitySet = Lists.newArrayList();
        if (order.getOrderLines() != null) {
            for (OrderLine orderLineSource : order.getOrderLines()) {
                OrderLineEntity targetLineEntity = everyClickConversionService.convert(orderLineSource, OrderLineEntity.class);
                targetLineEntity.setOrder(orderTargetEntity);
                contactEntitySet.add(targetLineEntity);
            }
            orderTargetEntity.setOrderLines(contactEntitySet);
        }
        return orderRepository.save(orderTargetEntity);
    }


    @Override
    @Transactional(readOnly = true)
    public Order getOrder(long userId) {
        OrderEntity userEntity = orderRepository.findOne(userId);
        if (userEntity == null) {
            throw new NotFoundException("404", "No order with that id=" + userId);
        }
        return everyClickConversionService.convert(userEntity, Order.class);
    }

    @Override
    public SearchResponse<Order> search(String query, PaginationSearchRequest pagination) {
        SearchResponse<OrderEntity> entitySearchResponse = orderRepository.search(query, pagination);
        return searchResponseConverter.buildSearchResponse(entitySearchResponse, orderEntityOrderConverterBasedTransformer);
    }
}
