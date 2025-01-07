<template>
	<div class="a">
		<!-- header部分 -->
		<header>
			<p>商家列表</p>
		</header>

		<!-- 商家列表部分 -->
		<ul class="business">
			<!-- 根据后端返回数据来遍历，点击事件 -->
			<li v-for="business in businessArr" :key="business.businessId" @click="toBusinessInfo(business.businessId)">
				<div class="business-img">
					<img :src="business.businessImg">
					<div class="business-img-quantity" v-show="business.quantity > 0">{{business.quantity}}</div> <!-- 点餐数量跟购物车关联 -->
				</div>
				<div class="business-info">
					<h3>{{business.businessName}}</h3>
					<p>&#165;{{business.starPrice}}起送 | &#165;{{business.deliveryPrice}}配送</p>
					<p v-text="business.businessExplain"></p>
				</div>
			</li>
		</ul>

		<!-- 底部菜单部分 -->
		<Footer></Footer>

	</div>
</template>

<script setup>
	// 底部菜单组件
	import Footer from '../components/Footer.vue';
	// qs 传参序列化
	import qs from 'qs';
	// 路由跳转
	import {
		useRouter,
		useRoute
	} from 'vue-router';
	// 响应数据，多级传参
	import {
		ref,
		inject
	} from 'vue';
	import {
		getSessionStorage
	} from '../common.js';

	// 接收上级组件的axios
	const axios = inject("axios");
	// 路由跳转
	const router = useRouter();
	// 路由传参，接收参数
	const route = useRoute();
	// 接收分类参数
	const orderTypeId = route.query.orderTypeId;
	// 声明一个数量类型的数据，用于接收服务端拉取的响应数据
	const businessArr = ref([]);
	// 取得登录信息session
	const user = getSessionStorage('user');

	// 定义方法  用于获取后端商家信息
	const init = () => { // http://localhost:8080/elm-api/BusinessController/listBusinessByOrderTypeId?orderTypeId=1
		axios.post('/elm-api/BusinessController/listBusinessByOrderTypeId', qs.stringify({
			orderTypeId: orderTypeId
		})).then((response) => {
			console.log(response.data);
			businessArr.value = response.data;

			if (user != null) {
				console.log("登录了");
				listCart();
			}

		}).catch((error) => {
			console.log(error);
		});
	}
	// 调用方法 初始化商家信息
	init();

	const toBusinessInfo = (businessId) => {
		router.push({
			path: "/businessInfo",
			query: {
				businessId: businessId
			}
		});
	}

	// 查询购物车信息
	const listCart = () => {
		axios.get(`elm-api/CartController/listCart?userId=${user.userId}`).then((response) => {
			// 购物车数据
			let cartArr = response.data;
			// 食品列表的数据
			for (let businessItem of businessArr.value) {
				businessItem.quantity = 0;
				for (let cartItem of cartArr) {
					if (cartItem.businessId == businessItem.businessId) {
						businessItem.quantity += cartItem.quantity;
					}
				}
				
			}
			console.log(response.data);
		}).catch((error) => {
			console.log(error);
		});
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

	/****************** 商家列表部分 ******************/
	.a .business {
		width: 100%;
		margin-top: 12vw;
		margin-bottom: 14vw;
	}

	.a .business li {
		width: 100%;
		box-sizing: border-box;
		padding: 2.5vw;
		border-bottom: solid 1px #DDD;
		user-select: none;
		cursor: pointer;

		display: flex;
		align-items: center;
	}

	.a .business li .business-img {
		/*这里设置为相当定位，成为business-img-quantity元素的父元素*/
		position: relative;
	}

	.a .business li .business-img img {
		width: 20vw;
		height: 20vw;
	}

	.a .business li .business-img .business-img-quantity {
		width: 5vw;
		height: 5vw;
		background-color: red;
		color: #fff;
		font-size: 3.6vw;
		border-radius: 2.5vw;

		display: flex;
		justify-content: center;
		align-items: center;

		/*设置成绝对定位，不占文档流空间*/
		position: absolute;
		right: -1.5vw;
		top: -1.5vw;
	}

	.a .business li .business-info {
		margin-left: 3vw;
	}

	.a .business li .business-info h3 {
		font-size: 3.8vw;
		color: #555;
	}

	.a .business li .business-info p {
		font-size: 3vw;
		color: #888;
		margin-top: 2vw;
	}
</style>