package ynu.elm.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Cart {
	@TableId
	private Integer cartId;
	private Integer foodId;
	private Integer businessId;
	private String userId;
	private Integer quantity;

	private Food food;
	private Business business;

	public Integer getQuantity() {
		return this.quantity;
	}

	public Integer getFoodId() {
		return this.foodId;
	}
}
