<template>
	<div class="a">
		<!-- header部分 -->
		<header>
			<p>在线支付</p>
		</header>
		<!-- 订单信息部分 -->
		<h3>订单信息：</h3>
		<div class="order-info">
			<p @click="isShow()">
				{{orders.business.businessName}}
				<i class="fa fa-caret-down" id="showBtn"></i>
			</p>
			<p>&#165;{{orders.orderTotal}}</p>
		</div>

		<!-- 订单明细部分 -->
		<ul class="order-detailet" id="detailetBox" v-show="isShowDetailet">
			<li v-for="item in orders.list" :key="item.food.foodId">
				<p>{{item.food.foodName}} x {{item.quantity}}</p>
				<p>&#165;{{item.food.foodPrice*item.quantity}}</p>
			</li>
			<li>
				<p>配送费</p>
				<p>&#165;{{orders.business.deliveryPrice}}</p>
			</li>
		</ul>

		<!-- 支付方式部分 -->
		<ul class="payment-type">
			<li @click="loaclnum()">
				<img src="../assets/alipay.png">
				<i class="fa fa-check-circle" v-show="loacl"></i>
			</li>
			<li @click="loaclnum()">
				<img src="../assets/wechat.png">
				<i class="fa fa-check-circle" v-show="!loacl"></i>
			</li>
		</ul>
		<div class="payment-button">
			<button @click="confirmPayment">确认支付</button>
		</div>

		<!-- 底部菜单部分 -->
		<Footer></Footer>
	</div>
</template>

<script setup>
	//OrderController/getOrdersById
	import Footer from '../components/Footer.vue';
	import qs from 'qs';
	import {
		useRouter,
		useRoute
	} from 'vue-router';
	import {
		ref,
		inject,
		computed
	} from 'vue';
	import {
		getLocalStorage,
		getSessionStorage
	} from '../common.js';

	const axios = inject("axios");
	const router = useRouter();
	const route = useRoute();
	const orderId = route.query.orderId;
	console.log("OrderId:" + orderId);
	const orders = ref({
		business: {}
	})
	const isShowDetailet = ref(false);
	const isShow = ()=>{
		isShowDetailet.value = !isShowDetailet.value
	}
	// 选择支付
	const loacl = ref(true);
	const loaclnum = () => {
		if (loacl.value == true) {
			loacl.value = false;
		} else {
			loacl.value = true;
		}
	}
	// 定义方法  用于获取后端信息
	const init = () => {
		// 根据订单编号查询订单信息
		axios.get(`/elm-api/OrdersController/getOrdersById?orderId=${orderId}`).then((response) => {
			console.log("订单信息");
			console.log(response.data);
			orders.value = response.data;
			console.log(response.data.list);
		}).catch((error) => {
			console.log(error);
		});
	}

  const confirmPayment = () => {
    axios.get(`/elm-api/OrdersController/updateOrderState?orderId=${orderId}`)
        .then((response) => {
          router.push({
            path: "/",

          });
        })
        .catch((error) => {
          console.error("支付失败", error);
          alert("支付失败，请稍后重试！");
        });
  };


  // 调用方法，初始化商家和商品信息
	init();


	const user = getSessionStorage('user');
	const deliveryAddress = getLocalStorage(user.userId);
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
	.a h3 {
		margin-top: 12vw;
		box-sizing: border-box;
		padding: 4vw 4vw 0;

		font-size: 4vw;
		font-weight: 300;
		color: #999;
	}

	.a .order-info {
		box-sizing: border-box;
		padding: 4vw;
		font-size: 4vw;
		color: #666;

		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.a .order-info p:last-child {
		color: orangered;
	}

	/****************** 订单明细部分 ******************/
	.a .order-detailet {
		width: 100%;
	}

	.a .order-detailet li {
		width: 100%;
		box-sizing: border-box;
		padding: 1vw 4vw;

		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.a .order-detailet li p {
		font-size: 3vw;
		color: #666;
	}

	/****************** 支付方式部分 ******************/
	.a .payment-type {
		width: 100%;
	}

	.a .payment-type li {
		width: 100%;
		box-sizing: border-box;
		padding: 4vw;

		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.a .payment-type li img {
		width: 33vw;
		height: 8.9vw;
	}

	.a .payment-type li .fa-check-circle {
		font-size: 5vw;
		color: #38CA73;
	}

	.a .payment-button {
		width: 100%;
		box-sizing: border-box;
		padding: 4vw;
	}

	.a .payment-button button {
		width: 100%;
		height: 10vw;
		border: none;
		/*去掉外轮廓线*/
		outline: none;
		border-radius: 4px;
		background-color: #38CA73;
		color: #fff;
	}
</style>