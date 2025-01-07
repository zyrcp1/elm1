package ynu.elm.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import ynu.elm.entity.Cart;
import ynu.elm.service.CartService;

import java.util.List;

@RestController
@RequestMapping(value = "/CartController")
public class CartController {

    @Resource
    private CartService cartService;

    @GetMapping(value = "/listCart")
    public List<Cart> listCart(@RequestParam(value = "userId") String userId, @RequestParam(value = "businessId",required = false) Integer businessId) throws Exception{
        return cartService.listCartByUserIdAndOptionalBusinessId(userId, businessId); // 这里不需要检查businessId是否为null，因为MyBatis的<if>标签已经处理了这种情况
    }

    @PostMapping("/saveCart")
    public int saveCart(@RequestBody Cart cart) throws Exception{
        return cartService.saveCart(cart);
    }

    @PostMapping("/updateCart")
    public int updateCart(@RequestBody Cart cart) throws Exception{
        return cartService.updateCart(cart);
    }

    @PostMapping("/removeCart")
    public int removeCart(@RequestBody Cart cart) throws Exception{
        return cartService.removeCart(cart);
    }

}
