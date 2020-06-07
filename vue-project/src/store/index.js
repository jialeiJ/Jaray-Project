import Vue from 'vue'
import Vuex from 'vuex'
//导入响应的模块，*相当于引入了这个组件下所有导出的事例
//* as表示导入这个文件的所有内容，就不用一个个实例来导入
import * as getters from './getters'
import * as actions from './actions'
import * as mutations from './mutations'

Vue.use(Vuex)
//首先声明一个需要全局维护的状态state，比如这里举例的collapsed
const state = {
    // 左侧菜单是否折叠标志
    collapsed: false,
    // 左侧菜单数据集合
    leftMenus: []
}

//注册上面引入的各大模块
const store = new Vuex.Store({
    //共同维护的一个状态，state里面可以是很多个全局状态
    state,
    //获取数据并渲染
    getters,
    //数据的异步操作
    actions,
    //处理数据的唯一途径，state的改变或赋值只能在这里
    mutations
})

//导出store并在main.js中引用注册
export default store