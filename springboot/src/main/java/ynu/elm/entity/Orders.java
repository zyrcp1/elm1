package ynu.elm.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.util.List;


@Data
public class Orders {
    @TableField("orderId")
    private Integer orderId;
    private String userId;
    private Integer businessId;
    private String orderDate;
    private Double orderTotal;
    private Integer daId;
    private Integer orderState; //订单状态0未支付，1已支付
    @TableField(exist = false)
    private Business business;
    @TableField(exist = false)
    private List<OrderDetailet> list;

}
