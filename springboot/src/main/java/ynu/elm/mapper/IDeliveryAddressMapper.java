package ynu.elm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import ynu.elm.entity.DeliveryAddress;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IDeliveryAddressMapper extends BaseMapper<DeliveryAddress> {

    //根据用户编号查询所属送货地址
    @Select("select * from deliveryAddress where userId=#{userId} order by daId")
    public List<DeliveryAddress> listDeliveryAddressByUserId(@Param("userId") String userId);

    //根据送货地址编号查询送货地址
    @Select("select * from deliveryAddress where daId=#{daId}")
    public DeliveryAddress getDeliveryAddressById(@Param("daId") Integer daId);

    //向送货地址表中添加一条记录
    @Insert("insert into deliveryAddress values(null,#{contactName},#{contactSex},#{contactTel},#{address},#{userId})")
    public int saveDeliveryAddress(DeliveryAddress deliveryAddress);

    //根据送货地址编号更新送货地址信息
    @Update("update deliveryAddress set contactName=#{contactName},contactSex=#{contactSex},contactTel=#{contactTel},address=#{address} where daId=#{daId}")
    public int updateDeliveryAddress(DeliveryAddress deliveryAddress);

    //根据送货地址编号删除一条记录
    @Delete("delete from deliveryAddress where daId=#{daId}")
    public int removeDeliveryAddress(@Param("daId") Integer daId);
}
