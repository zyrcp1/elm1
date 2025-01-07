package ynu.elm.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import ynu.elm.entity.Food;
import ynu.elm.service.FoodService;

import java.util.List;

@RestController
@RequestMapping(value = "/FoodController")
public class FoodController {

    @Resource
    private FoodService foodService;


    @GetMapping(value = "/listFoodByBusinessId")
    public List<Food> ListFoodByBusinessId(@RequestParam(value = "businessId") Integer businessId){
        return foodService.selectFoodsByBusinessId(businessId);
    }



}
