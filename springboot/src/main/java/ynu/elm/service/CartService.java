package ynu.elm.service;

import ynu.elm.entity.Cart;

import java.util.List;

public interface CartService {
    public List<Cart> listCartByUserIdAndOptionalBusinessId(String userId, Integer businessId);

    public int saveCart(Cart cart);

    public int updateCart(Cart cart);

    public int removeCart(Cart cart);
}
