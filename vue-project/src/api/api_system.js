import * as API from './'

export default {
    //查询投诉信息列表
    findLeftNav: params => {
        return API.POST('/system/findLeftNav', params);
    }
}