<template>
	<div class="a">

		<!-- header部分 -->
		<header>
			<p>我的订单</p>
		</header>

		<!-- 订单列表部分 -->
		<h3>未支付订单信息：</h3>
		<ul class="order">
			<li v-for="item in orderUnpaidArr" :key="item.orderId">
				<div class="order-info">
					<p @click="detailetShow(item)">
						{{item.business.businessName}}
						<i class="fa fa-caret-down"></i>
					</p>
					<div class="order-info-right">
						<p>&#165;{{item.orderTotal}}</p>
						<div class="order-info-right-icon" @click="toPayment(item.orderId)">去支付</div>
					</div>
				</div>
				<ul class="order-detailet" v-show="item.isShowDetailet">
					<li v-for="odItem in  item.list" :key="odItem.foodId">
						<p>{{odItem.food.foodName}} x {{odItem.quantity}}</p>
						<p>&#165;{{odItem.food.foodPrice*odItem.quantity}}</p>
					</li>
					<li>
						<p>配送费</p>
						<p>&#165;{{item.business.deliveryPrice}}</p>
					</li>
				</ul>
			</li>
		</ul>

		<h3>已支付订单信息：</h3>
		<ul class="order">
			<li v-for="item in orderPaidArr" :key="item.orderId">
				<div class="order-info">
					<p>
						{{item.business.businessName}}
						<i class="fa fa-caret-down" @click="detailetShow(item)"></i>
					</p>
					<div class="order-info-right">
						<p>&#165;{{item.orderTotal}}</p>
					</div>
				</div>
				<ul class="order-detailet" v-show="item.isShowDetailet">
					<li v-for="odItem in  item.list" :key="odItem.foodId">
						<p>{{odItem.food.foodName}} x {{odItem.quantity}}</p>
						<p>&#165;{{odItem.food.foodPrice*odItem.quantity}}</p>
					</li>
				</ul>
			</li>
		</ul>
		<!-- 底部菜单部分 -->
		<Footer></Footer>
	</div>
</template>

<script setup>
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
	const user = getSessionStorage('user');
	// 已经支付的订单
	const orderPaidArr = ref([]);
	// 未支付订单
	const orderUnpaidArr = ref([]);

	// 定义方法  用于获取后端历史信息
	const init = () => {
		// 根据订单编号查询历史信息
		axios.get(`/elm-api/OrdersController/listOrdersByUserId?userId=${user.userId}`).then((response) => {
			console.log("历史订单信息");
			console.log(response.data);
			let result = response.data;

			for (let wc of result) {
				// 控制详单是否显示 添加一个属性来控制
				wc.isShowDetailet = false;
				// 判断是否支付
				if (wc.orderState == 0) {
					orderUnpaidArr.value.push(wc);
				} else if (wc.orderState == 1) {
					orderPaidArr.value.push(wc);
				}
			}
		}).catch((error) => {
			console.log(error);
		});
	}
	// 调用
	init();

	// 定义方法 用于跳转到支付页面
	const toPayment = (orderId) => {
		router.push({
			path: "/payment",
			query: {
				orderId: orderId
			}
		});
	}

	const detailetShow = (orders) => {
		orders.isShowDetailet = !orders.isShowDetailet;
	}
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

	/****************** 历史订单列表部分 ******************/
	.a h3 {
		margin-top: 12vw;
		box-sizing: border-box;
		padding: 4vw;
		font-size: 4vw;
		font-weight: 300;
		color: #999;
	}

	.a .order {
		width: 100%;
	}

	.a .order li {
		width: 100%;
	}

	.a .order li .order-info {
		box-sizing: border-box;
		padding: 2vw 4vw;
		font-size: 4vw;
		color: #666;

		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.a .order li .order-info .order-info-right {
		display: flex;
	}

	.a .order li .order-info .order-info-right .order-info-right-icon {
		background-color: #f90;
		color: #fff;
		border-radius: 3px;
		margin-left: 2vw;
		user-select: none;
		cursor: pointer;
	}

	.a .order li .order-detailet {
		width: 100%;
	}

	.a .order li .order-detailet li {
		width: 100%;
		box-sizing: border-box;
		padding: 1vw 4vw;
		color: #666;
		font-size: 3vw;

		display: flex;
		justify-content: space-between;
		align-items: center;
	}
</style>