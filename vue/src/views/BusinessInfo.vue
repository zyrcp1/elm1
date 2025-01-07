<template>
	<div class="a">
		<!-- header部分 -->
		<header>
			<p>商家信息</p>
		</header>

		<!-- 商家logo部分 -->
		<div class="business-logo">
			<img :src="business.businessImg">
		</div>

		<!-- 商家信息部分 -->
		<div class="business-info">
			<h3>{{business.businessName}}</h3>
			<p>&#165;{{business.starPrice}}起送 | &#165;{{business.deliveryPrice}}配送</p>
			<p v-text="business.businessExplain"></p>
		</div>

		<!-- 食品列表部分 -->
		<ul class="food">
			<li v-for="(food,index) in foodArr" :key="food.foodId">
				<div class="food-left">
					<img :src="food.foodImg">
					<div class="food-left-info">
						<h3>{{food.foodName}}</h3>
						<p>{{food.foodExplain}}</p>
						<p>&#165;{{food.foodPrice}}</p>
					</div>
				</div>
				<!-- 食品选择数量 -->
				<div class="food-right">
					<div>
						<i class="fa fa-minus-circle" @click="minus(index)" v-show="food.quantity>0"></i>
					</div>
					<p><span v-show="food.quantity>0">{{food.quantity}}</span></p>
					<div>
						<i class="fa fa-plus-circle" @click="add(index)"></i>
					</div>
				</div>
			</li>
		</ul>

		<!-- 购物车部分 -->
		<div class="cart">
			<div class="cart-left">
				<div class="cart-left-icon"
					:style="totalQuantity == 0?'background-color:#505051':'background-color:#3190E8'">
					<i class="fa fa-shopping-cart"></i>
					<!-- 商品数量 红点显示 -->
					<div class="cart-left-icon-quantity" v-show="totalQuantity != 0">
						{{totalQuantity}}
					</div>
				</div>
				<div class="cart-left-info">
					<!-- 商品总价格 -->
					<p>&#165;{{totalPrice}}</p>
					<p>另需配送费{{business.deliveryPrice}}元</p>
				</div>
			</div>
			<div class="cart-right">
				<!-- 不够起送费business.starPrice -->
				<div class="cart-right-item" v-show="totalPrice < business.starPrice">
					&#165;{{business.starPrice}}起送
				</div>
				<!-- 达到起送费 -->
				<div class="cart-right-item" v-show="totalPrice >= business.starPrice" @click="toOrder()">
					去结算
				</div>
			</div>
		</div>
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
		inject,
		computed
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

	const businessId = route.query.businessId;
	// 如果没取到就是空的,如果取到就是登录成功。
	const user = getSessionStorage('user');

	// 商家信息
	const business = ref({});
	// 食品信息
	let foodArr = ref([]);


	// 计算属性
	// 食品总价格
	const totalPrice = computed(() => {
		let total = 0;
		for (let item of foodArr.value) {
			total += item.foodPrice * item.quantity;
		}
		return total;
	});
	// 食品总数量
	const totalQuantity = computed(() => {
		let quantity = 0;
		for (let item of foodArr.value) {
			quantity += item.quantity;
		}
		return quantity;
	});
	// 结算总价格
	const tdotalSettle = computed(() => {
		return totalPrice.value + business.value.deliveryPrice;
	})


	// 定义方法  用于获取后端信息
	const init = () => {

		// 根据商家id从后端获取商家信息
		axios.get(`/elm-api/BusinessController/getBusinessById?businessId=${businessId}`).then((response) => {
			// console.log(response.data);
			business.value = response.data;
		}).catch((error) => {
			console.log(error);
		});

		// 根据商家id从后端获取商家食品信息
		axios.get(`/elm-api/FoodController/listFoodByBusinessId?businessId=${businessId}`).then((response) => {
			// console.log(response.data);
			let arr = response.data;

			for (let i = 0; i < arr.length; i++) {
				// 新增一个属性值为0，用于保存数量。
				// 存在问题，当刷新页面后界面就重新为0
				arr[i].quantity = 0;
			}
			foodArr.value = arr;
			// 判断用户是否登录，如果登录去购物车查询有啥
			if (user != null) {
				listCart();
			}
		}).catch((error) => {
			console.log(error);
		});


	}

	// 调用方法，初始化商家和商品信息
	init();

	// 定义方法用于点击事件 -- 数量改变 增加 
	const add = (index) => {
		// 首先进行登录验证
		if (user == null) {
			router.push({
				path: '/login'
			});
			return; //没登录结束
		}

		// 登录成功，接下来的业务流程。
		if (foodArr.value[index].quantity == 0) {
			// insert插入
			saveCart(index);
		} else {
			// update更新
			updateCart(index, 1);
		}
	}

	// 定义方法用于点击事件 -- 数量改变 减少
	const minus = (index) => {
		// 首先进行登录验证
		if (user == null) {
			router.push({
				path: '/login'
			});
			return; //没登录结束
		}

		// 登录成功，接下来的业务流程。
		if (foodArr.value[index].quantity > 1) {
			// update更新
			updateCart(index, -1);
		} else {
			// 删除
			removeCart(index);

		}
	}

	// 用于向购物车中插入值
	const saveCart = (index) => {
		//servlet在逗号之后加qs.stringfy，spring时去掉
		axios.post('/elm-api/CartController/saveCart', ({
			businessId: businessId,
			userId: user.userId,
			foodId: foodArr.value[index].foodId //食品数组对象-下标-食品Id
		})).then((response) => {
			// console.log(response.data);
			// 返回值为1，插入成功
			if (response.data == 1) {
				foodArr.value[index].quantity = 1;
			} else {
				alert("OUT!!!!!!!!!");
			}
		}).catch((error) => {
			console.log(error);
		});
	}

	// 用于更新购物车的数量
	const updateCart = (index, num) => {
		//servlet在逗号之后加qs.stringfy，spring时去掉
		axios.post('/elm-api/CartController/updateCart', ({
			businessId: businessId,
			userId: user.userId,
			foodId: foodArr.value[index].foodId, //食品数组对象-下标-食品Id
			quantity: foodArr.value[index].quantity + num
		})).then((response) => {
			// console.log(response.data);
			// 返回值为1，更新成功
			if (response.data == 1) {
				foodArr.value[index].quantity += num
			} else {
				alert("OUT!!!!!!!!!");
			}
		}).catch((error) => {
			console.log(error);
		});
	}

	// 用于向购物车中删除
	const removeCart = (index) => {
		//servlet在逗号之后加qs.stringfy，spring时去掉
		axios.post('/elm-api/CartController/removeCart', ({
			businessId: businessId,
			userId: user.userId,
			foodId: foodArr.value[index].foodId //食品数组对象-下标-食品Id
		})).then((response) => {
			//console.log(response.data);
			// 返回值为1，删除成功
			if (response.data == 1) {
				foodArr.value[index].quantity = 0;
			} else {
				alert("删除失败!!!!!!!!!");
			}
		}).catch((error) => {
			console.log(error);
		});
	}


	// 查询购物车信息
	const listCart = () => {
		axios.get(`/elm-api/CartController/listCart?businessId=${businessId}&userId=${user.userId}`).then((response) => {
			// console.log('查询购物车信息');
			// console.log(response.data);
			// 购物车数据
			let cartArr = response.data;
			// 食品列表的数据
			for (let foodItem of foodArr.value) {
				foodItem.quantity = 0;
				for (let cartItem of cartArr) {
					if (cartItem.foodId == foodItem.foodId) {
						foodItem.quantity = cartItem.quantity;
					}
				}
			}
		}).catch((error) => {
			console.log(error);
		});
	}

	// 路由到确认订单组件
	const toOrder = () => {
		router.push({
			path: "/order",
			query: {
				businessId: businessId
			}	
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

	/****************** 商家logo部分 ******************/
	.a .business-logo {
		width: 100%;
		height: 35vw;
		/*使用上外边距避开header部分*/
		margin-top: 12vw;

		display: flex;
		justify-content: center;
		align-items: center;
	}

	.a .business-logo img {
		width: 40vw;
		height: 30vw;
		border-radius: 5px;
	}

	/****************** 商家信息部分 ******************/
	.a .business-info {
		width: 100%;
		height: 20vw;

		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
	}

	.a .business-info h1 {
		font-size: 5vw;
	}

	.a .business-info p {
		font-size: 3vw;
		color: #666;
		margin-top: 1vw;
	}

	/****************** 食品列表部分 ******************/
	.a .food {
		width: 100%;
		/*使用下外边距避开footer部分*/
		margin-bottom: 14vw;
	}

	.a .food li {
		width: 100%;
		box-sizing: border-box;
		padding: 2.5vw;
		user-select: none;

		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.a .food li .food-left {
		display: flex;
		align-items: center;
	}

	.a .food li .food-left img {
		width: 20vw;
		height: 20vw;
	}

	.a .food li .food-left .food-left-info {
		margin-left: 3vw;
	}

	.a .food li .food-left .food-left-info h3 {
		font-size: 3.8vw;
		color: #555;
	}

	.a .food li .food-left .food-left-info p {
		font-size: 3vw;
		color: #888;
		margin-top: 2vw;
	}

	.a .food li .food-right {
		width: 16vw;
		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.a .food li .food-right .fa-minus-circle {
		font-size: 5.5vw;
		color: #999;
		cursor: pointer;
	}

	.a .food li .food-right p {
		font-size: 3.6vw;
		color: #333;
	}

	.a .food li .food-right .fa-plus-circle {
		font-size: 5.5vw;
		color: #0097EF;
		cursor: pointer;
	}

	/****************** 购物车部分 ******************/
	.a .cart {
		width: 100%;
		height: 14vw;

		position: fixed;
		left: 0;
		bottom: 0;

		display: flex;
	}

	.a .cart .cart-left {
		flex: 2;
		background-color: #505051;
		display: flex;
	}

	.a .cart .cart-left .cart-left-icon {
		width: 16vw;
		height: 16vw;
		box-sizing: border-box;
		border: solid 1.6vw #444;
		border-radius: 8vw;
		background-color: #3190E8;
		font-size: 7vw;
		color: #fff;

		display: flex;
		justify-content: center;
		align-items: center;

		margin-top: -4vw;
		margin-left: 3vw;

		position: relative;
	}

	.a .cart .cart-left .cart-left-icon-quantity {
		width: 5vw;
		height: 5vw;
		border-radius: 2.5vw;
		background-color: red;
		color: #fff;
		font-size: 3.6vw;

		display: flex;
		justify-content: center;
		align-items: center;

		position: absolute;
		right: -1.5vw;
		top: -1.5vw;
	}

	.a .cart .cart-left .cart-left-info p:first-child {
		font-size: 4.5vw;
		color: #fff;
		margin-top: 1vw;
	}

	.a .cart .cart-left .cart-left-info p:last-child {
		font-size: 2.8vw;
		color: #AAA;
	}

	.a .cart .cart-right {
		flex: 1;
	}

	/*达到起送费时的样式*/
	.a .cart .cart-right .cart-right-item {
		width: 100%;
		height: 100%;
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

	/*不够起送费时的样式（只有背景色和鼠标样式的区别）*/
	/*
	.a .cart .cart-right .cart-right-item{
		width: 100%;
		height: 100%;
		background-color: #535356;
		color: #fff;
		font-size: 4.5vw;
		font-weight: 700;
		user-select: none;
		
		display: flex;
		justify-content: center;
		align-items: center;
	}
	*/
</style>