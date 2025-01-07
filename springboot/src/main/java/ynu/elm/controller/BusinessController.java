package ynu.elm.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import ynu.elm.entity.Business;
import ynu.elm.service.BusinessService;

import java.util.List;

@RestController
@RequestMapping(value = "/BusinessController")
public class BusinessController {
    @Resource
    private BusinessService businessService;

    //根据点餐分类编号查询所属商家信息
    @PostMapping(value = "/listBusinessByOrderTypeId")
    public List<Business> listBusinessesByOrderTypeId(@RequestParam(value = "orderTypeId") Integer orderTypeId) {
        return businessService.listByOrderTypeId(orderTypeId);
    }

    //根据商家编号查询商家信息
    @GetMapping(value = "getBusinessById")
    public Business GetBusinessById(@RequestParam(value = "businessId") Integer businessId) {
        return businessService.queryBusinessById(businessId);
    }

}
