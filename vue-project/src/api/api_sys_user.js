import * as API from '.'

export default {
    //查询用户信息列表
    findSysUserList: params => {
        return API.GET('/sys/user/find/all', params);
    },
    //删除用户信息
    deleteSysUser: params => {
        return API.POST('/sys/user/delete', params);
    },
    //添加用户信息
    addSysUser: params => {
        return API.POST('/sys/user/add', params);
    },
    //更新用户信息
    updateSysUser: params => {
        return API.POST('/sys/user/update', params);
    },
    //查询用户信息
    viewSysUser: params => {
        return API.POST('/sys/user/find', params);
    },
}