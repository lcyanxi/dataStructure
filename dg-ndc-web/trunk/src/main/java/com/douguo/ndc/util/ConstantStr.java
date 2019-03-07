package com.douguo.ndc.util;

/**
 * Created by lcyanxi on 2018/8/12.
 */
public interface ConstantStr {

    String LICHANG = "lichang";
    /**
     * 用户画像标签长度大小设置
     */
    int SIZE = 100;

    /**
     * 消息
     */
    String MESSAGE = "message";

    /**
     * 状态码
     */
    String SATATUSCODE = "statusCode";

    /**
     * 一级标签名称
     */
    String TITLE = "title";

    /**
     * 二级标签名称
     */
    String NEXTTITLE = "nextTitle";

    /**
     * json的name
     */
    String NAME = "name";

    /**
     * json 的value
     */
    String VALUE = "value";

    /**
     * 一级标签数据标识
     */
    String LIST_TAG = "list_tag";

    /**
     * 二级标签数据标识
     */
    String LIST_TAG_SUB = "list_tag_sub";

    /**
     * 返回的数据集合
     */
    String DATA = "data";

    /**
     * 封装柱形图数据
     */
    String BARDATA = "barData";

    /**
     * table表格获取总数求百分比
     */
    String TOTAL = "total";

    /**
     * session 里存放的user对象
     */
    String SESSION_USER = "user";

    /**
     * session 里存放的uid
     */
    String UID = "uid";

    /**
     * session 里存放的菜单结构
     */
    String STRMENU = "strMenu";

    /**
     * query table 请求发送记录操作的次数
     */
    String SECHO = "sEcho";

    /**
     * query table 请求发送每页显示的行数
     */
    String IDISPLAYSTART = "iDisplayStart";

    /**
     * query table 请求发送每页显示的行数
     */
    String IDISPLAYLENGTH = "iDisplayLength";

    /**
     * query table 请求发送搜索词
     */
    String SSEARCH = "sSearch";

    /**
     * query table 返回实际的行数
     */
    String ITOTALRECORDS = "iTotalRecords";

    /**
     * query table 返回显示的行数
     */
    String ITOTALDISPLAYRECORDS = "iTotalDisplayRecords";

    /**
     * query table 返回封装的数据
     */
    String AADATA = "aaData";

    /**
     * 用于区分客户端点击事件区分dimension_type 字段
     * 该字段为枚举类型 ALL  KEY  VALUE
     */
    String PARAM_TYPE_ALL = "ALL";
    String PARAM_TYPE_KEY = "KEY";
    String PARAM_TYPE_VALUE = "VALUE";

    /**
     * 用于用户留存分析区分日  周  月
     */
    String DATE = "date";
    String WORK = "work";
    String MONTH = "month";

}
