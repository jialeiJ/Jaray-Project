import * as API from './'

export default {
    //登录
    login: params => {
        return API.POST('/system/login', params);
    },
    //登录
    refreshToken: params => {
        return API.POST('/system/refreshToken', params);
    },
    //查询投诉信息列表
    findLeftNav: params => {
        return API.POST('/system/findLeftNav', params);
    }
}