import * as API from '.'

export default {
    //查询用户信息列表
    findAllSysUser: params => {
        return API.POST('/sys/user/find/all', params);
    },
    //删除用户信息
    deleteSysUserByIds: params => {
        return API.POST('/sys/user/deleteByIds', params);
    },
    //添加用户信息
    addSysUser: params => {
        return API.POST('/sys/user/add', params);
    },
    //更新用户信息
    updateSysUserById: params => {
        return API.POST('/sys/user/updateById', params);
    },
    //查询用户信息
    viewSysUserById: params => {
        return API.POST('/sys/user/findById', params);
    },
    //添加任务
    addTask: params => {
        return API.POST('/quartz/task/addjob', params);
    },
    //添加任务
    pauseJob: params => {
        return API.POST('/quartz/task/pauseJob', params);
    },
    deleteJob: params => {
        return API.POST('/quartz/task/deletejob', params);
    },
}