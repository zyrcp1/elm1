<template>
	<div id="footer">
		<!-- 底部菜单部分 -->
		<ul class="footer">
			<li @click="toIndex()">
				<i class="fa fa-home"></i>
				<p>首页</p>
			</li>
			<li>
				<i class="fa fa-compass"></i>
				<p>发现</p>
			</li>
			<li @click="toOrderList()">
				<i class="fa fa-file-text-o"></i>
				<p>订单</p>
			</li>
			<li>
				<i class="fa fa-user-o"></i>
				<p>我的</p>
			</li>
		</ul>
	</div>
</template>

<script setup>
	// 路由跳转
	import {useRouter,useRoute} from 'vue-router';
  import {getSessionStorage} from "@/common";
	const router = useRouter();
	const toIndex = () => {
		console.log("???????")
		router.push({
			path: '/'
		});
	};
	const toOrderList = () => {
		// router.push({
		// 	path: '/orderList'
		// });
    const user = getSessionStorage('user');
    if (user && user.userId) {
      // 用户已登录，导航到 OrderList
      router.push({ path: '/orderList' });
    } else {
      // 用户未登录，导航到登录页面
      alert('请先登录');
      router.push({ path: '/login', query: { redirect: '/orderList' } });
    }
	};
	
	
</script>

<style scoped>
	/****************** 底部菜单部分 ******************/
	.a .footer {
		width: 100%;
		height: 14vw;
		border-top: solid 1px #DDD;
		background-color: #fff;

		position: fixed;
		left: 0;
		bottom: 0;

		display: flex;
		justify-content: space-around;
		align-items: center;
	}

	.a .footer li {
		/*li本身的尺寸完全由内容撑起*/
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;

		color: #999;
		user-select: none;
		cursor: pointer;
	}

	.a .footer li p {
		font-size: 2.8vw;
	}

	.a .footer li i {
		font-size: 5vw;
	}
</style>