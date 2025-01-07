<template>
	<div class="a">

		<!-- header部分 -->
		<header>
			<p>地址管理</p>
		</header>
 
		<!-- 地址列表部分 -->
		<ul class="addresslist">
			<li v-for="item in deliveryAddressArr" :key="item.daId">
				<div class="addresslist-left" @click="setDeliveryAddress(item)">
					<h3>{{item.contactName}}
						{{item.contactSex==1?"先生":"女士"}}
						{{item.contactTel}}
					</h3>
					<p>{{item.address}}</p>
				</div>
				<div class="addresslist-right">
					<!-- 进入修改地址页面 -->
					<i class="fa fa-edit" @click="editUserAddress(item.daId)"></i>
					<!-- 删除地址 -->
					<i class="fa fa-remove" @click="removeUserAddress(item.daId)"></i>
				</div>
			</li>
		</ul>

		<!-- 新增地址部分 -->
		<div class="addbtn" @click="toAddUserAddress()">
			<i class="fa fa-plus-circle"></i>
			<p>新增收货地址</p>
		</div>

		<!-- 底部菜单部分 -->
		<Footer></Footer>
	</div>
</template>

<script setup>
	import Footer from '../components/Footer.vue';
	import qs from 'qs';
	//导入路由相关的模块
	import {
		useRouter,
		useRoute
	} from 'vue-router'
	import {
		ref,
		inject,
		computed
	} from 'vue';
	import {
		getSessionStorage,
		getLocalStorage,
		setLocalStorage
	} from '../common.js';

	const axios = inject("axios");
	const router = useRouter();
	const route = useRoute();
	const businessId = route.query.businessId;
	const user = getSessionStorage('user');
	const deliveryAddressArr = ref([]);

	const listDeliveryAddressByUserId = () => {
		//使用servlet时改为DeliveryaddressController
		axios.get(`/elm-api/DeliveryAddressController/listDeliveryAddressByUserId?userId=${user.userId}`).then((response) => {
			deliveryAddressArr.value = response.data;
			console.log(deliveryAddressArr.value);
		}).catch((error) => {
			console.log(error);
		});
	};
	listDeliveryAddressByUserId();


	// 选择地址信息返回确认订单页面
	const setDeliveryAddress = (deliveryAddress) => {
		setLocalStorage(user.userId, deliveryAddress);
		router.push({
			path: "/order",
			query: {
				businessId: businessId
			}
		});
	}

	// 去添加地址页面
	const toAddUserAddress = (daId) => {
		router.push({
			path: "/addUserAddress",
			query: {
				businessId: businessId
			}
		});
	}

	// 更新地址页面
	const editUserAddress = (daId) => {
		router.push({
			path: "/editUserAddress",
			query: {
				daId: daId
			}
		});
	}
	// 删除地址信息
	// DeliveryAddressController/removeDeliveryAddress
	const removeUserAddress = (daId) => {
		axios.post('/elm-api/DeliveryAddressController/removeDeliveryAddress',
			({
				daId: daId
			})).then((response) => {
			response.data;
			console.log(response.data>0?"删除成功":"删除失败");
			listDeliveryAddressByUserId();
		}).catch((error) => {
			console.log(error);
		});
	}

</script>

<style scoped>
	/*************** 总容器 ***************/
	.a {
		width: 100%;
		height: 100%;
		background-color: #F5F5F5;
	}

	/*************** header ***************/
	.a header {
		width: 100%;
		height: 12vw;
		background-color: #0097FF;
		display: flex;
		justify-content: space-around;
		align-items: center;
		color: #fff;
		font-size: 4.8vw;
		position: fixed;
		left: 0;
		top: 0;
		/*保证在最上层*/
		z-index: 1000;
	}

	/*************** addresslist ***************/
	.a .addresslist {
		width: 100%;
		margin-top: 12vw;
		background-color: #fff;
	}

	.a .addresslist li {
		width: 100%;
		box-sizing: border-box;
		border-bottom: solid 1px #DDD;
		padding: 3vw;

		display: flex;
	}

	.a .addresslist li .addresslist-left {
		flex: 5;
		/*左边这块区域是可以点击的*/
		user-select: none;
		cursor: pointer;
	}

	.a .addresslist li .addresslist-left h3 {
		font-size: 4.6vw;
		font-weight: 300;
		color: #666;
	}

	.a .addresslist li .addresslist-left p {
		font-size: 4vw;
		color: #666;
	}

	.a .addresslist li .addresslist-right {
		flex: 1;
		font-size: 5.6vw;
		color: #999;
		cursor: pointer;

		display: flex;
		justify-content: space-around;
		align-items: center;
	}

	/*************** 新增地址部分 ***************/
	.a .addbtn {
		width: 100%;
		height: 14vw;
		border-top: solid 1px #DDD;
		border-bottom: solid 1px #DDD;
		background-color: #fff;
		margin-top: 4vw;

		display: flex;
		justify-content: center;
		align-items: center;

		font-size: 4.5vw;
		color: #0097FF;
		user-select: none;
		cursor: pointer;
	}

	.a .addbtn p {
		margin-left: 2vw;
	}
</style>