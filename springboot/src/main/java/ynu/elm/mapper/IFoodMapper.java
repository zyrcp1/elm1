package ynu.elm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import ynu.elm.entity.Food;

import java.util.List;


@Mapper
public interface IFoodMapper extends BaseMapper<Food> {

    @Select("SELECT * FROM food WHERE businessId = #{businessId}")
    List<Food> selectFoodsByBusinessId(@Param("businessId") Integer businessId);

    @Select("select * from food where foodId=#{foodId}")
    public Food getFoodById(Integer foodId);

}


