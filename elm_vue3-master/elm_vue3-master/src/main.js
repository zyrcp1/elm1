import {
	createApp
} from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';
// 导入字体库
import 'font-awesome/css/font-awesome.min.css'


createApp(App).use(store).use(router).mount('#app')

// 添加路由守卫
router.beforeEach(function(to, from, next) {
	// 从session中找user
	let user = sessionStorage.getItem('user');

	// 判断路由到哪里去
	// 除了登录，注册，首页，商家列表，商家信息以外的组件都需要进行判断是否登录。
	if (!(to.path == '/' || to.path == '/index' || to.path == '/businessList' || to.path == 'businessInfo' ||
			to.path == '/login' || to.path == '/register')) {
		if (user = null) {
			router.push('/login');
		}
	}
	// 判断完成继续进行
	next();
})