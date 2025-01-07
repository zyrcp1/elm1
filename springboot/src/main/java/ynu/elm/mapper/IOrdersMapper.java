package ynu.elm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import ynu.elm.entity.Orders;
import org.apache.ibatis.annotations.Param;


import java.util.List;

@Mapper
public interface IOrdersMapper extends BaseMapper<Orders> {

    int saveOrders(Orders orders);

    Orders getOrdersById(@Param("orderId") Integer orderId);

    List<Orders> listOrdersByUserId(@Param("userId") String userId);

    @Update("update orders set orderState=#{orderState} where orderId=#{orderId}")
    int update(Orders orders);
}
