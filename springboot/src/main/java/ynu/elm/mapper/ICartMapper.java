package ynu.elm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import ynu.elm.entity.Cart;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ICartMapper extends BaseMapper<Cart> {

    //根据userId和businessId（可选）查询购物车信息
    List<Cart> listCartByUserIdAndOptionalBusinessId(@Param("userId") String userId, @Param("businessId") Integer businessId);

    //向购物车表中添加一条记录
    @Insert("insert into cart values(null,#{foodId},#{businessId},#{userId},1)")
    int saveCart(Cart cart);

    //根据用户编号、商家编号、食品编号更新数量
    @Update("update cart set quantity=#{quantity} where foodId=#{foodId} and businessId=#{businessId} and userId=#{userId}")
    int updateCart(Cart cart);

    //根据用户编号、商家编号、食品编号删除购物车表中的一条食品记录
    // 根据用户编号、商家编号删除购物车表中的多条条记录
    int removeCart(Cart cart);


    void deleteByUserIdAndBusinessId(@Param("userId") String userId, @Param("businessId") Integer businessId);

    public List<Cart> listCart(Cart cart);

}





