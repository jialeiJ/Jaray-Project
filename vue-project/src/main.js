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

import '@/assets/iconfont/iconfont.css'

Vue.config.productionTip = false
Vue.use(ElementUI)
Vue.use(Fragment.Plugin)

Vue.prototype.axios = axios

/**
 * 全局挂载（适用于script标签中的js代码格式化时间）
 * 使用方式：this.$moment(时间).format('时间格式')
 */
Vue.prototype.$moment = moment;

/* eslint-disable no-new */
new Vue({
    el: '#app',
    router,
    // 这样就能全局使用vuex了
    store,
    components: { App },
    template: '<App/>'
})
