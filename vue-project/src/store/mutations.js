//提交mutations时更改Vuex状态的唯一合法方法
export const modifyCollapsed = (state, collapsed) => {
    //把方法传递过来的参数，赋值给state中的collapsed
    state.collapsed = collapsed
}

export const modifyLeftMenus = (state, leftMenus) => {
    //把方法传递过来的参数，赋值给state中的collapsed
    state.leftMenus = leftMenus
}

