import * as API from '.'

export default {
    
    //删除定时任务信息
    deleteSysUserByIds: params => {
        return API.POST('/sys/user/deleteByIds', params);
    },
    //添加定时任务信息
    addSysUser: params => {
        return API.POST('/sys/user/add', params);
    },
    //更新定时任务信息
    updateSysUserById: params => {
        return API.POST('/sys/user/updateById', params);
    },
    // 查询定时任务信息
    viewSysUserById: params => {
        return API.POST('/sys/user/findById', params);
    },
    // 查询定时任务列表
    findAllJob: params => {
        return API.POST('/quartz/job/find/all', params);
    },
    // 添加定时任务
    addJob: params => {
        return API.POST('/quartz/job/addJob', params);
    },
    // 暂停定时任务
    pauseJob: params => {
        return API.POST('/quartz/job/pauseJob', params);
    },
    // 恢复定时任务
    resumeJob: params => {
        return API.POST('/quartz/job/resumeJob', params);
    },
    updateJob: params => {
        return API.POST('/quartz/job/updateJob', params);
    },
    // 删除定时任务
    deleteJob: params => {
        return API.POST('/quartz/job/deleteJob', params);
    },
}