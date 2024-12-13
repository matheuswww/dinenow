package com.github.matheuswwwp.dinenow.controller.order.ws;

import com.github.matheuswwwp.dinenow.DTO.order.GetOrderReqDTO;
import com.github.matheuswwwp.dinenow.conf.jwt.JwtTokenProvider;
import com.github.matheuswwwp.dinenow.service.order.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetOrder {
    private static final Logger logger = LoggerFactory.getLogger(GetOrder.class);
    @Autowired
    private OrderService orderService;
    @Autowired
    private JwtTokenProvider jwtProvider;

    @MessageMapping("/getOrderWithoutStatus")
    @SendTo("/notification/getOrderWithoutStatus")
    public ResponseEntity<?> GetOrderWithoutStatus(@Payload GetOrderReqDTO orderRequest) {
        logger.info("GetOrderWithoutStatus - init GetOrderWithoutStatus");
        return orderService.GetOrder(null, orderRequest.getPages(), orderRequest.getItems(), false);
    }

    @MessageMapping("/getOrderWaiting")
    @SendTo("/notification/getOrderWaiting")
    public ResponseEntity<?> GetOrderWaiting(@Payload GetOrderReqDTO orderRequest) {
        logger.info("GetOrderWaiting - init GetOrderWaiting");
        return orderService.GetOrder("waiting", orderRequest.getPages(), orderRequest.getItems(), false);
    }

    @MessageMapping("/getOrderPreparing")
    @SendTo("/notification/getOrderPreparing")
    public ResponseEntity<?> GetOrderPreparing(@Payload GetOrderReqDTO orderRequest) {
        logger.info("GetOrderPreparing - init GetOrderPreparing");
        return orderService.GetOrder( "preparing", orderRequest.getPages(), orderRequest.getItems(), false);
    }

    @MessageMapping("/getOrderRoute")
    @SendTo("/notification/getOrderRoute")
    public ResponseEntity<?> GetOrderRoute(@Payload GetOrderReqDTO orderRequest) {
        logger.info("GetOrderRoute - init GetOrderRoute");
        return orderService.GetOrder("route", orderRequest.getPages(), orderRequest.getItems(), false);
    }

    @MessageMapping("/getOrderFinished")
    @SendTo("/notification/getOrderFinished")
    public ResponseEntity<?> GetOrderFinished(@Payload GetOrderReqDTO orderRequest) {
        logger.info("GetOrderFinished - init GetOrderFinished");
        return orderService.GetOrder( "finished", orderRequest.getPages(), orderRequest.getItems(), false);
    }
}