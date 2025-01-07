package ynu.elm.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import ynu.elm.entity.Business;
import java.util.List;
import ynu.elm.service.BusinessService;
import ynu.elm.mapper.IBusinessMapper;

@Service
public class BusinessServiceImpl implements BusinessService {

    @Resource
    private IBusinessMapper businessMapper;

    @Override
    public List<Business> listByOrderTypeId(Integer orderTypeId) {
        return businessMapper.listByOrderTypeId(orderTypeId);
    }

    @Override
    public Business queryBusinessById(Integer businessId) {
        return businessMapper.queryBusinessById(businessId);
    }

}
