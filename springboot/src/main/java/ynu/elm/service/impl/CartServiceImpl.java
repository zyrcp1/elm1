package ynu.elm.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import ynu.elm.entity.Cart;
import java.util.List;
import ynu.elm.service.CartService;
import ynu.elm.mapper.ICartMapper;

@Service
public class CartServiceImpl implements CartService {

    @Resource
    private ICartMapper cartMapper;

    @Override
    public List<Cart> listCartByUserIdAndOptionalBusinessId(String userId, Integer businessId){
        return cartMapper.listCartByUserIdAndOptionalBusinessId(userId,businessId);
    }

    @Override
    public int saveCart(Cart cart) {
        return cartMapper.saveCart(cart);
    }

    @Override
    public int updateCart(Cart cart) {
        return cartMapper.updateCart(cart);
    }

    @Override
    public int removeCart(Cart cart) {
        return cartMapper.removeCart(cart);
    }

}
