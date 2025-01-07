package ynu.elm.service;

import ynu.elm.entity.Business;

import java.util.List;

public interface BusinessService {
    public List<Business> listByOrderTypeId(Integer orderTypeId);
    public Business queryBusinessById( Integer businessId);
}


