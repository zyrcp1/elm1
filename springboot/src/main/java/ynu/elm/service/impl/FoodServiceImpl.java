package ynu.elm.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import ynu.elm.entity.Food;
import java.util.List;
import ynu.elm.service.FoodService;
import ynu.elm.mapper.IFoodMapper;


@Service
public class FoodServiceImpl implements FoodService {

    @Resource
    private IFoodMapper foodMapper;

    @Override
    public List<Food> selectFoodsByBusinessId(Integer businessId) {
        return foodMapper.selectFoodsByBusinessId(businessId);
    }


}
