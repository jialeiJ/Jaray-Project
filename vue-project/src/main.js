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
import API from './api/api_system'

import '@/assets/iconfont/iconfont.css'

Vue.config.productionTip = false
Vue.use(ElementUI)
Vue.use(Fragment.Plugin)

Vue.prototype.axios = axios

let refreshTokenFalg = true

router.beforeEach((to, from, next) => {
    // 如果有token 说明该用户已登陆
    let token = ""
    if (token = localStorage.getItem('access-token')) {
        if(refreshTokenFalg){
            // 定义请求参数
            let params = {token: token}
            // 调用接口
            API.refreshToken(params).then(function (result) {
                if (result.code === 200) {
                    if(result.map.refreshToken){
                        localStorage.setItem('access-token', result.map.refreshToken); // 将token信息存到localStorage中
                    }
                } else {
                    router.push({path : '/'});
                    localStorage.removeItem('access-token'); // 将token信息存到localStorage中
                }
            });
            refreshTokenFalg = false
        }
        next();
    } 
    else {
        // 没有登陆则访问任何页面都重定向到登陆页
        if (to.path !== '/') {//跳转到登录页
            return next({path: '/'});
        }else {
            next();
        }
    }
})

function getrefreshToken(expiredToken) {
    let params = {
        token: expiredToken
    }
    // 调用接口
    API.refreshToken(params).then(function (result) {
        if (result.code === 200 && result.map.refreshToken) {
            localStorage.setItem('access-token', result.map.refreshToken)
        }
    });
}

/* eslint-disable no-new */
new Vue({
    el: '#app',
    router,
    // 这样就能全局使用vuex了
    store,
    components: { App },
    template: '<App/>'
})
