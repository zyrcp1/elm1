<template>
	<div class="a">
	  <!-- header部分 -->
	  <header>
		<p>确认订单</p>
	  </header>
	  
	  <!-- 订单信息部分 -->
	  <div class="order-info">
		<h5>订单配送至：</h5>
		<div class="order-info-address" @click="toUserAddress">
		  <p>{{ deliveryaddress != null ? deliveryaddress.address : '请选择送货地址' }}</p>
		  <i class="fa fa-angle-right"></i>
		</div>
		<p>{{ user.userName }} {{ userSexFilter(user.userSex) }} {{ user.userId }}</p>
	  </div>
	  
	  <h3>{{ business.businessName }}</h3>
	  
	  <!-- 订单明细部分 -->
	  <ul class="order-detailed">
		<li v-for="item in cartArr" :key="item.cartId">
		  <div class="order-detailed-left">
			<img :src="item.food.foodImg">
			<p>{{ item.food.foodName }} x {{ item.quantity }}</p>
		  </div>
		  <p>&#165;{{ item.food.foodPrice * item.quantity }}</p>
		</li>
	  </ul>
	  <div class="order-deliveryfee">
		<p>配送费</p>
		<p>&#165;{{ business.deliveryPrice }}</p>
	  </div>
	  
	  <!-- 合计部分 -->
	  <div class="total">
		<div class="total-left">
		  &#165;{{ totalPrice }}
		</div>
		<div class="total-right" @click="toPayment">
		  去支付
		</div>
	  </div>
	</div>
  </template>
   
  <script>
  import { ref, computed, onMounted } from 'vue';
  import axios from 'axios';
  import qs from 'qs';
  import router from '@/router';
  import {
		useRouter,
		useRoute
	} from 'vue-router';
  import {
		getLocalStorage,
		getSessionStorage
  } from '../common.js';

   
  export default {
	name: 'Order',
	setup() {
	  const route = useRoute();
	  const businessId = route.query.businessId;
	  const business = ref({});
	  const user = getSessionStorage('user');
	  const cartArr = ref([]);
	  const deliveryaddress = getLocalStorage(user.userId);
   
	  const userSexFilter = (value) => {
		return value === 1 ? '先生' : '女士';
	  };
   
	  const totalPrice = computed(() => {
		let total = 0;
		for (let cartItem of cartArr.value) {
		  total += cartItem.food.foodPrice * cartItem.quantity;
		}
		total += business.value.deliveryPrice;
		return total;
	  });
   
	  onMounted(() => {
		// const user = getSessionStorage('user');
		// const userId = user.userId;
		// const deliveryaddress = getLocalStorage(user.userId); // 假设你存储地址的方式有所调整
   
		// 查询当前商家
		axios.get(`/elm-api/BusinessController/getBusinessById?businessId=${businessId}`).then((response) => {
			// console.log(response.data);
			business.value = response.data;
		}).catch(error => {
			console.error(error);
		  });
   
		// 查询当前用户在购物车中的当前商家食品列表
		axios.get(`/elm-api/CartController/listCart?businessId=${businessId}&userId=${user.userId}`).then(response => {
			cartArr.value = response.data;
		  })
		  .catch(error => {
			console.error(error);
		  });
	  });
   
	  const toUserAddress = () => {
		router.push({
			path: "/userAddress",
			query: {
				businessId: businessId
			}
		})
	}
   
	  const toPayment = () => {
		if (!deliveryaddress) {
		  alert('请选择送货地址！');
		  return;
		}
   
		// 创建订单
		console.log(user.userId)
		axios.post('/elm-api/OrdersController/createOrders', qs.stringify({
			userId:user.userId,
			businessId: businessId,
			orderTotal: totalPrice.value,
			daId: deliveryaddress.daId
		})).then(response => {
		  let orderId = response.data;
		  if (orderId > 0) {
			router.push({
					path: "/payment",
					query: {
						orderId: orderId
					}
				});
		  } else {
			alert('创建订单失败！');
		  }
		})
		.catch(error => {
		  console.error(error);
		});
	  };
   
	  return {
		businessId,
		business,
		user,
		cartArr,
		deliveryaddress,
		userSexFilter,
		totalPrice,
		toUserAddress,
		toPayment
	  };
	}
  };
  </script>
<style scoped>
	/****************** 总容器 ******************/
	.a {
		width: 100%;
		height: 100%;
	}

	/****************** header部分 ******************/
	.a header {
		width: 100%;
		height: 12vw;
		background-color: #0097FF;
		color: #fff;
		font-size: 4.8vw;

		position: fixed;
		left: 0;
		top: 0;
		z-index: 1000;

		display: flex;
		justify-content: center;
		align-items: center;
	}

	/****************** 订单信息部分 ******************/
	.a .order-info {
		/*注意这里，不设置高，靠内容撑开。因为地址有可能折行*/
		width: 100%;
		margin-top: 12vw;
		background-color: #0097EF;
		box-sizing: border-box;
		padding: 2vw;
		color: #fff;
	}

	.a .order-info h5 {
		font-size: 3vw;
		font-weight: 300;
	}

	.a .order-info .order-info-address {
		width: 100%;
		display: flex;
		justify-content: space-between;
		align-items: center;

		font-weight: 700;
		user-select: none;
		cursor: pointer;
		margin: 1vw 0;
	}

	.a .order-info .order-info-address p {
		width: 90%;
		font-size: 5vw;
	}

	.a .order-info .order-info-address i {
		font-size: 6vw;
	}

	.a .order-info p {
		font-size: 3vw;
	}

	.a h3 {
		box-sizing: border-box;
		padding: 3vw;
		font-size: 4vw;
		color: #666;
		border-bottom: solid 1px #DDD;
	}

	/****************** 订单明细部分 ******************/
	.a .order-detailed {
		width: 100%;
	}

	.a .order-detailed li {
		width: 100%;
		height: 16vw;
		box-sizing: border-box;
		padding: 3vw;
		color: #666;

		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.a .order-detailed li .order-detailed-left {
		display: flex;
		align-items: center;
	}

	.a .order-detailed li .order-detailed-left img {
		width: 10vw;
		height: 10vw;
	}

	.a .order-detailed li .order-detailed-left p {
		font-size: 3.5vw;
		margin-left: 3vw;
	}

	.a .order-detailed li p {
		font-size: 3.5vw;
	}

	.a .order-deliveryfee {
		width: 100%;
		height: 16vw;
		box-sizing: border-box;
		padding: 3vw;
		color: #666;
		display: flex;
		justify-content: space-between;
		align-items: center;
		font-size: 3.5vw;
	}

	/****************** 订单合计部分 ******************/
	.a .total {
		width: 100%;
		height: 14vw;

		position: fixed;
		left: 0;
		bottom: 0;

		display: flex;
	}

	.a .total .total-left {
		flex: 2;
		background-color: #505051;
		color: #fff;
		font-size: 4.5vw;
		font-weight: 700;
		user-select: none;

		display: flex;
		justify-content: center;
		align-items: center;
	}

	.a .total .total-right {
		flex: 1;
		background-color: #38CA73;
		color: #fff;
		font-size: 4.5vw;
		font-weight: 700;
		user-select: none;
		cursor: pointer;

		display: flex;
		justify-content: center;
		align-items: center;
	}
</style>