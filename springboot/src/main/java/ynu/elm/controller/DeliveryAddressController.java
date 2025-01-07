package ynu.elm.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import ynu.elm.entity.DeliveryAddress;
import ynu.elm.service.DeliveryAddressService;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/DeliveryAddressController")
public class DeliveryAddressController {

    @Resource
    private DeliveryAddressService deliveryAddressService;

    @GetMapping("/listDeliveryAddressByUserId")
    public List<DeliveryAddress> listDeliveryAddressByUserId(@RequestParam(value = "userId") String userId) throws Exception{
        return deliveryAddressService.listDeliveryAddressByUserId(userId);
    }

    @GetMapping("/getDeliveryAddressById")
    public DeliveryAddress getDeliveryAddressById(@RequestParam(value = "daId") Integer daId) throws Exception{
        return deliveryAddressService.getDeliveryAddressById(daId);
    }

    @PostMapping("/saveDeliveryAddress")
    public int saveDeliveryAddress(@RequestBody DeliveryAddress deliveryAddress) throws Exception{

        return deliveryAddressService.saveDeliveryAddress(deliveryAddress);
    }

    @PostMapping("/updateDeliveryAddress")
    public int updateDeliveryAddress(@RequestBody DeliveryAddress deliveryAddress) throws Exception{

        return deliveryAddressService.updateDeliveryAddress(deliveryAddress);
    }



    @PostMapping("/removeDeliveryAddress")
    public int removeDeliveryAddress(@RequestBody Map<String, Integer> request) throws Exception {
        Integer daId = request.get("daId");
        return deliveryAddressService.removeDeliveryAddress(daId);
    }
}
