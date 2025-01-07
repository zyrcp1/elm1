package ynu.elm.service;

import ynu.elm.entity.Orders;

import java.util.List;

public interface OrdersService {

    public int createOrders(String userId, Integer businessId, Integer daId, Double orderTotal);

    public Orders getOrdersById(Integer OrderId);

    public List<Orders> listOrdersByUserId(String userId);

    public int updateOrder(Orders orders);
}
