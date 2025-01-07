package ynu.elm.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import ynu.elm.entity.DeliveryAddress;
import java.util.List;
import ynu.elm.service.DeliveryAddressService;
import ynu.elm.mapper.IDeliveryAddressMapper;


@Service
public class DeliveryAddressServiceImpl implements DeliveryAddressService {

    @Resource
    private IDeliveryAddressMapper deliveryAddressMapper;

    @Override
    public List<DeliveryAddress> listDeliveryAddressByUserId(String userId) {
        return deliveryAddressMapper.listDeliveryAddressByUserId(userId);
    }

    @Override
    public DeliveryAddress getDeliveryAddressById(Integer daId) {
        return deliveryAddressMapper.getDeliveryAddressById(daId);
    }

    @Override
    public int saveDeliveryAddress(DeliveryAddress deliveryAddress) {
        return deliveryAddressMapper.saveDeliveryAddress(deliveryAddress);
    }

    @Override
    public int updateDeliveryAddress(DeliveryAddress deliveryAddress) {
        return deliveryAddressMapper.updateDeliveryAddress(deliveryAddress);
    }

    @Override
    public int removeDeliveryAddress(Integer daId) {
        return deliveryAddressMapper.removeDeliveryAddress(daId);
    }

}
