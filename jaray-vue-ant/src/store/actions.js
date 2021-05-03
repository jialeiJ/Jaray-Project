//给action注册事件处理函数。当这个函数触发时，将状态提交到mutations中处理
//commit 提交；collapsed即为点击后传递过来的参数，此时是true or false
export function modifyCollapsed({commit}, collapsed){
    return commit('modifyCollapsed', collapsed)
}

//commit 提交；collapsed即为点击后传递过来的参数，此时是true or false
export function modifyLeftMenus({commit}, leftMenus){
    return commit('modifyLeftMenus', leftMenus)
}

