package ynu.elm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import ynu.elm.entity.OrderDetailet;

import java.util.List;

@Mapper
public interface IOrderDetailetMapper extends BaseMapper<OrderDetailet> {

    public int saveOrderDetailetBatch(OrderDetailet orderDetailet);

    public List<OrderDetailet> listOrderDetailetByOrderId(Integer orderId);


}
