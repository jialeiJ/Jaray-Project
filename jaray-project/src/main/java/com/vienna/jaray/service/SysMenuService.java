package com.vienna.jaray.service;

import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.SysMenu;
import com.vienna.jaray.model.CommonParamsModel;
import org.apache.ibatis.annotations.Param;

public interface SysMenuService {
    /**
     * 查询左侧菜单信息
     * @param user_id 用户id
     * @return
     */
    public ResponseResult findLeftNav(String user_id);

    /**
     * 查询菜单列表
     * @return
     */
    public ResponseResult findAll(CommonParamsModel commonParamsModel);

    /**
     * 添加菜单
     * @param sysMenuEntity 菜单对象
     * @return
     */
    public ResponseResult add(@Param("entity") SysMenu sysMenuEntity);

    /**
     * 删除菜单
     * @param ids 菜单ids
     * @return
     */
    public ResponseResult deleteByIds(@Param("ids")String[] ids);

    /**
     * 更新菜单
     * @param sysMenuEntity 菜单对象
     * @return
     */
    public ResponseResult updateById(@Param("entity") SysMenu sysMenuEntity);

    /**
     * 查询菜单
     * @param id 菜单id
     * @return
     */
    public ResponseResult findById(@Param("id")String id);

    /**
     * 查询所有目录菜单
     * @return
     */
    public ResponseResult findAllDir();
}
