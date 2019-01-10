package com.asiainfo.mainFrame.entity;


/**
 * 浙江省所属地址代码
 */
public class Region {
    
    private String code; //地市代码
    private String sort; //组成规则： 地市名称的第一个汉字的拼音大写首字母＋ 第二个汉字的拼音大写首字母＋…＋ 地市名称的第N个汉字的拼音大写首字母。 比如，杭州：HZ；宁波：NB
    private String name; //浙江省所属地市名称
    
    public Region() {
        super();
        // TODO Auto-generated constructor stub
    }
    public Region(String code, String name) {
        super();
        this.code = code;
        this.name = name;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getSort() {
        return sort;
    }
    public void setSort(String sort) {
        this.sort = sort;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    
    
}
