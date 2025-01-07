package ynu.elm.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import ynu.elm.entity.Orders;
import ynu.elm.service.OrdersService;

import java.util.List;

@RestController
@RequestMapping(value = "/OrdersController")
public class OrdersController {

    @Resource
    OrdersService ordersService;


    @PostMapping(value = "/createOrders")
    public int createOrders(@RequestParam(value = "userId")String userId,
                            @RequestParam(value = "businessId")Integer businessId,
                            @RequestParam(value = "daId")Integer daId,
                            @RequestParam(value = "orderTotal")Double orderTotal){
        return ordersService.createOrders(userId,businessId,daId,orderTotal);
    }

    @GetMapping(value = "/getOrdersById")
    public Orders getOrdersById(@RequestParam(value = "orderId") Integer orderId){
        return ordersService.getOrdersById(orderId);
    }

    @GetMapping(value = "/listOrdersByUserId")
    public List<Orders> listOrdersByUserId(@RequestParam(value = "userId") String userId){
        return ordersService.listOrdersByUserId(userId);
    }

    @GetMapping("/updateOrderState")
    public int updateOrderState(@RequestParam(value = "orderId") Integer orderId){
        System.out.println(orderId);
        Orders order = ordersService.getOrdersById(orderId);
          order.setOrderState(1);
        return ordersService.updateOrder(order);
    }


}
