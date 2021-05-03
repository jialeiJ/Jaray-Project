import * as API from '.'

export default {
    //查询用户信息列表
    findAllVideo: params => {
        return API.POST('/education/video/find/all', params);
    },
    //删除用户信息
    deleteVideoByIds: params => {
        return API.POST('/education/video/deleteByIds', params);
    },
    //添加用户信息
    addVideo: params => {
        return API.POST('/education/video/add', params);
    },
    //更新用户信息
    updateVideoById: params => {
        return API.POST('/education/video/updateById', params);
    },
    //查询用户信息
    viewVideoById: params => {
        return API.POST('/education/video/findById', params);
    },
}