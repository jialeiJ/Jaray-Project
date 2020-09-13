package com.vienna.jaray.common;

import lombok.Data;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 分隔符常量
 */
public enum Separator {
    /**
     * 英文逗号分隔符
     */
    COMMA_SEPARATOR_EN(",", "英文逗号分隔符"),
    /**
     * 中文逗号分隔符
     */
    COMMA_SEPARATOR_CN("，", "中文逗号分隔符");

    /**
     * 成员变量：状态码
     */
    private String separator;
    /**
     * 成员变量：状态描述
     */
    private String separatorDesc;

    /**
     * 私有构造方法
     *
     * @param separator 分隔符
     * @param separatorDesc 分隔符描述
     */
    private Separator(String separator, String separatorDesc) {
        this.separator = separator;
        this.separatorDesc = separatorDesc;
    }

    /**
     * 获取分隔符描述
     *
     * @param separator 分隔符
     * @return 分隔符描述
     */
    public static String getSeparatorDesc(String separator) {
        for (Separator sep : Separator.values()) {
            if (sep.getSeparator().equals(separator)) {
                return sep.separatorDesc;
            }
        }
        return null;
    }

    /**
     * 获取分隔符描述
     *
     * @return 分隔符描述
     */
    public String getSeparatorDesc() {
        return separatorDesc;
    }

    /**
     * 设置分隔符描述
     *
     * @param separatorDesc 分隔符描述
     */
    public void setSeparatorDesc(String separatorDesc) {
        this.separatorDesc = separatorDesc;
    }

    /**
     * 获取分隔符
     *
     * @return 分隔符
     */
    public String getSeparator() {
        return separator;
    }

    /**
     * 设置分隔符
     *
     * @param separator 分隔符
     */
    public void setSeparator(String separator) {
        this.separator = separator;
    }
}
