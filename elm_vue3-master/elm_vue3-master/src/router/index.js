import {
	createRouter,
	createWebHashHistory
} from 'vue-router'
import Index from '../views/Index.vue'
import BusinessList from '../views/BusinessList'
import BusinessInfo from '../views/BusinessInfo'
import Login from '../views/Login'
import Order from '../views/Order'
import UserAddress from '../views/UserAddress'
import AddUserAddress from '../views/AddUserAddress'
import Payment from '../views/Payment'
import OrderList from '../views/OrderList'
import EditUserAddress from '../views/EditUserAddress'
import Register from '../views/Register'

const routes = [{
		path: '/',
		name: 'Home',
		component: Index
	}, {
		path: '/index',
		name: 'Index',
		component: Index
	}, {
		path: '/businessList',
		name: 'BusinessList',
		component: BusinessList
	},
	{
		path: '/businessInfo',
		name: 'BusinessInfo',
		component: BusinessInfo
	},
	{
		path: '/login',
		name: 'Login',
		component: Login
	},
	{
		path: '/order',
		name: 'Order',
		component: Order
	},
	{
		path: '/userAddress',
		name: 'UserAddress',
		component: UserAddress
	},
	{
		path: '/addUserAddress',
		name: 'AddUserAddress',
		component: AddUserAddress
	},
	{
		path: '/payment',
		name: 'Payment',
		component: Payment
	},
	{
		path: '/orderList',
		name: 'OrderList',
		component: OrderList,
		meta: { requiresAuth: true }

	},
	{
		path: '/editUserAddress',
		name: 'EditUserAddress',
		component: EditUserAddress
	},
	{
		path: '/register',
		name: 'Register',
		component: Register
	}
]



const router = createRouter({
	history: createWebHashHistory(),
	routes
})



export default router