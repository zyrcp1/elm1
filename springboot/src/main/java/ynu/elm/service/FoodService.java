package ynu.elm.service;

import ynu.elm.entity.Food;

import java.util.List;

public interface FoodService {

    public List<Food> selectFoodsByBusinessId( Integer businessId);

}
