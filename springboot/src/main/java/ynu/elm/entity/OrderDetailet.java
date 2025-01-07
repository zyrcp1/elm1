package ynu.elm.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class OrderDetailet {
    @TableId
    private Integer odId;
    private Integer orderId;
    private Integer foodId;
    private Integer quantity;

    private Food food;



}
