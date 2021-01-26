import Env from './env';
import axios from 'axios'
import Qs from 'qs'
import router from '../router/index'
import API from '../api/api_system'
// 注意：在js文件中使用element组件 按照下面更优雅哦~~
import {
    Message,
    MessageBox
} from 'element-ui'

let token = '';
let refreshTokenFalg = true
let noPermissionFalg = false
axios.defaults.withCredentials = true;
axios.defaults.headers.common['token'] = token;

//添加一个请求拦截器
axios.interceptors.request.use(function (request) {
    noPermissionFalg = false
    // 如果有token 说明该用户已登陆
    // let accessUser = localStorage.getItem('access-user')
    let accessUser = sessionStorage.getItem('access-user')
    if (accessUser) {
        let sysUserToken = JSON.parse(accessUser)
        // 若token在15分钟内过期并且用户期间操作则刷新token
        if(refreshTokenFalg && (Date.parse(sysUserToken.expire_time) < (new Date(new Date().getTime() + 15 * 60 * 1000)) ) && (Date.parse(sysUserToken.expire_time) > new Date())){
            //localStorage.clear() // 清除用户信息
            sessionStorage.clear()
            // 定义请求参数
            let params = sysUserToken
            // 调用接口
            API.refreshToken(params).then(function (result) {
                if (result.code === 200) {
                    sessionStorage.setItem('access-user', JSON.stringify(result.map.sysUserToken)); // 将用户信息存到sessionStorage中
                    sessionStorage.setItem('access-token', result.map.sysUserToken.token); // 将token信息存到sessionStorage中  
                    // localStorage.setItem('access-user', JSON.stringify(result.map.sysUserToken)); // 将用户信息存到localStorage中
                    // localStorage.setItem('access-token', result.map.sysUserToken.token); // 将token信息存到localStorage中  
                } else {
                    router.push({path : '/'});
                }
            });
            refreshTokenFalg = false
        }
    }else {
        // 没有登陆则访问任何页面都重定向到登陆页
        router.push({path : '/'});
    }

    // token = localStorage.getItem('access-token')
    token = sessionStorage.getItem('access-token')
    if(token){
        request.headers.common['token'] = token;
    }
    return request;
}, function (error) {
    return Promise.reject(error);
});

// 添加一个响应拦截器
axios.interceptors.response.use(function (response) {
    if (response.data && response.data.code) {
        if (!noPermissionFalg && parseInt(response.data.code) === 403) {
            noPermissionFalg = true
            // localStorage.clear()
            sessionStorage.clear()
            //未登录
            Message({
                message: 'TOKEN不存在或TOKEN失效，请重新登录',
                type: 'error',
                duration: 5 * 1000
            })
            router.push({path : '/'});
        } else if (!noPermissionFalg && parseInt(response.data.code) === 100) {
            Message({
                message: response.data.msg,
                type: 'error'
            });
            console.error("请求失败", response.data.msg);
        } else if (parseInt(response.data.code) !== 200) {
            Message({
                message: response.data.msg,
                type: 'error'
            });
            console.error("请求失败", response.data.msg);
        }
    }
    return response;
}, function (error) {
    console.dir(error);
    console.error("服务器连接失败")
    return Promise.reject(error);
})

//基地址
// let base = 'Env.baseURL';
let base = process.env.NODE_ENV === 'production'?'':'api';

//测试使用
export const ISDEV = Env.isDev;

//通用方法
export const POST = (url, params) => {
    const getTimestamp = new Date().getTime();
    let data = Qs.stringify(params)
    let config = {headers:{'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'}}
    return axios.post(`${base}${url}?timer=${getTimestamp}`, data, config).then(result => result.data)
}

export const GET = (url, params) => {
    const getTimestamp = new Date().getTime();
    return axios.get(`${base}${url}?timer=${getTimestamp}`, {params: params}).then(result => result.data)
}

export const PUT = (url, params) => {
    let config = {headers:{'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'}}
    return axios.put(`${base}${url}`, params, config).then(result => result.data)
}

export const DELETE = (url, params) => {
    let config = {headers:{'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'}}
    return axios.delete(`${base}${url}`, {params: params}, config).then(result => result.data)
}

export const PATCH = (url, params) => {
    let config = {headers:{'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'}}
    return axios.patch(`${base}${url}`, params, config).then(result => result.data)
}