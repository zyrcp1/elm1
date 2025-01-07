package ynu.elm.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ynu.elm.service.OrdersService;
import ynu.elm.mapper.IOrderDetailetMapper;
import ynu.elm.mapper.ICartMapper;
import ynu.elm.mapper.IOrdersMapper;
import ynu.elm.entity.Orders;
import ynu.elm.entity.Cart;
import ynu.elm.entity.OrderDetailet;

import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Resource
    private IOrdersMapper ordersMapper;

    @Resource
    private IOrderDetailetMapper orderDetailetMapper;

    @Resource
    private ICartMapper cartMapper;

    @Override
    @Transactional
    public int createOrders(String userId, Integer businessId, Integer daId, Double orderTotal) {
        Orders orders = new Orders();
        orders.setUserId(userId);
        orders.setBusinessId(businessId);
        orders.setDaId(daId);
        orders.setOrderTotal(orderTotal);

        // 创建订单（返回生成的订单编号）
        int saved = ordersMapper.saveOrders(orders);
        if (saved == 0) {
            throw new RuntimeException("订单保存失败");
        }
        int orderId = orders.getOrderId();


        List<Cart> cartList = cartMapper.listCartByUserIdAndOptionalBusinessId(userId, businessId);
        for (Cart cartItem : cartList) {
            OrderDetailet detail = new OrderDetailet();
            detail.setOrderId(orderId);
            detail.setFoodId(cartItem.getFoodId());
            detail.setQuantity(cartItem.getQuantity());
            orderDetailetMapper.saveOrderDetailetBatch(detail);
        }


        // 清空购物车
        cartMapper.deleteByUserIdAndBusinessId(userId, businessId);

        return orderId;

    }

    @Override
    public Orders getOrdersById(Integer orderId){
        return ordersMapper.getOrdersById(orderId);
    }

    @Override
    public List<Orders> listOrdersByUserId(String userId){
        return ordersMapper.listOrdersByUserId(userId);
    }

    @Override
    public int updateOrder(Orders orders) {
        return  ordersMapper.update(orders);
    }
}
