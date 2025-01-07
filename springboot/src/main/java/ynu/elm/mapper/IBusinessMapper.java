package ynu.elm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import ynu.elm.entity.Business;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IBusinessMapper extends BaseMapper<Business> {

    List<Business> listByOrderTypeId(Integer orderTypeId);

    @Select("SELECT * FROM business")
    List<Business> SelectBusiness();

    @Select("SELECT * FROM business WHERE businessId = #{businessId}")
    Business queryBusinessById(@Param("businessId") Integer businessId);


}
