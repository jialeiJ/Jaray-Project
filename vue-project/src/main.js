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

import '@/assets/iconfont/iconfont.css'

Vue.config.productionTip = false
Vue.use(ElementUI)
Vue.use(Fragment.Plugin)

Vue.prototype.axios = axios

/* eslint-disable no-new */
new Vue({
    el: '#app',
    router,
    // 这样就能全局使用vuex了
    store,
    components: { App },
    template: '<App/>'
})
