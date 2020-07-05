package com.vienna.jaray.common;

/**
 * 分隔符
 */
public enum Separator {

    COMMA_SEPARATOR_EN(",", "英文逗号分隔符"),
    COMMA_SEPARATOR_CN("，", "中文逗号分隔符");

    // 成员变量
    /**
     * 状态码
     */
    private String separator;
    /**
     * 状态描述
     */
    private String separatorDesc;

    // 构造方法
    private Separator(String separator, String separatorDesc) {
        this.separator = separator;
        this.separatorDesc = separatorDesc;
    }

    // 普通方法
    public static String getSeparatorDesc(String separator) {
        for (Separator sep : Separator.values()) {
            if (sep.getSeparator() == separator) {
                return sep.separatorDesc;
            }
        }
        return null;
    }

    // get set 方法
    public String getSeparatorDesc() {
        return separatorDesc;
    }
    public void setSeparatorDesc(String separatorDesc) {
        this.separatorDesc = separatorDesc;
    }
    public String getSeparator() {
        return separator;
    }
    public void setSeparator(String separator) {
        this.separator = separator;
    }

}
