// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import Fragment from 'vue-fragment'
import axios from 'axios'
import store from './store'
// 引入JavaScript 日期处理类库（格式化）
import moment from "moment";
// moment.locale('zh-cn') // 汉化
import 'default-passive-events'
import $ from 'jquery'

import '@/assets/iconfont/iconfont.css'

Vue.config.productionTip = false

Vue.use(ElementUI, { size: 'medium', zIndex: 3000 });
Vue.use(Fragment.Plugin)

Vue.prototype.axios = axios

/**
 * 全局挂载（适用于script标签中的js代码格式化时间）
 * 使用方式：this.$moment(时间).format('时间格式')
 */
Vue.prototype.$moment = moment;

// 全局前置守卫
// 在Router实例上进行守卫
router.beforeEach((to, from, next) => {
    // to和from都是路由实例
    // to：即将跳转到的路由
    // from：现在的要离开的路由
    // next：函数
    // 如果用户信息不存在，跳到登录页，如果存在，选择哪个页面跳到哪个页面；如果存在还去login页面，就跳到首页。
    let accessUser = sessionStorage.getItem('access-user')

    if (to.path == '/') {
        if (accessUser) {
            next({ path: '/main' })
        } else {
            return next()
        }   
    } else {
        if (accessUser) {
            next()
        } else {
            next({ path: '/' })
        }     
    }
})
  
// 全局解析守卫 定义解析方法为空时路由不跳转
// router.beforeResolve((to, from, next) => {

// })
  
// 全局后置钩子
router.afterEach((to, form) => {
    
})

/* eslint-disable no-new */
new Vue({
    el: '#app',
    router,
    // 这样就能全局使用vuex了
    store,
    components: { App },
    template: '<App/>'
})
