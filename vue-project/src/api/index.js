import Env from './env';
import axios from 'axios'
import Qs from 'qs'
import routerIndex from '../router/index'

let token = '';

axios.defaults.withCredentials = false;
axios.defaults.headers.common['token'] = token;
axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';//配置请求头

//添加一个请求拦截器
axios.interceptors.request.use(function (config) {
//   let user = JSON.parse(window.sessionStorage.getItem('access-user'));
//   if (user) {
//     token = user.token;
//   }
//   config.headers.common['token'] = token;
  //console.dir(config);
    return config;
}, function (error) {
    console.error(error);
    return Promise.reject(error);
});

// 添加一个响应拦截器
axios.interceptors.response.use(function (response) {
    if (response.data && response.data.code) {
        if (parseInt(response.data.code) === 108 || parseInt(response.data.code) === 109 || response.data.msg === 'TOKEN失效，请重新登录' || response.data.msg === 'TOKEN不存在') {
            //未登录
            response.data.msg = "登录信息已失效，请重新登录";
            console.log(response.data.msg)
            routerIndex.push('/login');
        }
        if (parseInt(response.data.code) === -1) {
            console.error("请求失败")
        }
    }
    return response;
}, function (error) {
    // Do something with response error
    console.dir(error);
    console.error("服务器连接失败")
    return Promise.reject(error);
})

//基地址
let base = Env.baseURL;

//测试使用
export const ISDEV = Env.isDev;

//通用方法
export const POST = (url, params) => {
    const getTimestamp = new Date().getTime();
    let data = Qs.stringify(params)
    let config = {headers:{'Content-Type':'application/x-www-form-urlencoded'}}
    return axios.post(`${base}${url}?timer=${getTimestamp}`, data, config).then(result => result.data)
}

export const GET = (url, params) => {
    const getTimestamp = new Date().getTime();
    return axios.get(`${base}${url}?timer=${getTimestamp}`, {params: params}).then(result => result.data)
}

export const PUT = (url, params) => {
    return axios.put(`${base}${url}`, params).then(result => result.data)
}

export const DELETE = (url, params) => {
    return axios.delete(`${base}${url}`, {params: params}).then(result => result.data)
}

export const PATCH = (url, params) => {
    return axios.patch(`${base}${url}`, params).then(result => result.data)
}