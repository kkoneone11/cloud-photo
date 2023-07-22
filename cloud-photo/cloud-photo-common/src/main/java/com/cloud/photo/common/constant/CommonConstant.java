package com.cloud.photo.common.constant;


/**
 * 公共常量类
 * @author linzsh
 */
public class CommonConstant {

    /**
     * 手机号正则表达式
     */
    public static final String REGEX_MOBILE = "((\\+86|0086)?\\s*)((134[0-8]\\d{7})|(((13([0-3]|[5-9]))|(14[5-9])|15([0-3]|[5-9])|(16(2|[5-7]))|17([0-3]|[5-8])|18[0-9]|19(3|1|[8-9]))\\d{8})|(14(0|1|4)0\\d{7})|(1740([0-5]|[6-9]|[10-12])\\d{7}))";

    /**
     * 存在session里面的用户信息Key
     */
    public static final String USER_INFO = "userInfo";

    /**
     * 文件状态正常 0
     */
    public static final String FILE_STATUS_NORMA = "0";

    /**
     * 用户角色
     */
    public static final String ADMIN = "admin";
    public static final String USER = "user";

    /**
     * 是否是目录  -否
     */
    public static final String FILE_IS_FOLDER_NO = "0";

    /**
     * 审核通过
     */
    public static final Integer FILE_AUDIT = 0;

    /**
     * 审核通过
     */
    public static final Integer FILE_AUDIT_ACCESS = 1;
    /**
     * 审核不通过
     */
    public static final Integer FILE_AUDIT_FAIL = 2;

    /**
     * 图片分类 Category - 1人脸 2事物 3地点
     */
    public static final Integer IMAGE_CATEGORY_1 = 1;
    public static final Integer IMAGE_CATEGORY_2 = 2;
    public static final Integer IMAGE_CATEGORY_3 = 3;

    /**
     * 文件上传状态：上传成功、上传中、上传失败
     */
    public static final String FILE_UPLOAD_SUCCESS = "1";
    public static final String FILE_UPLOAD_ING = "0";
    public static final String FILE_UPLOAD_FAIL = "-1";

    public static final long FILE_UPLOAD_KEY_EXPIRED = 24 * 3600;
    public static final String ICON_STORAGE_OBJECT_ID = "iconStorageObjectId";
    public static final String FILE_AUDIT_TOPIC = "file_audit_topic";

    public static final String FILE_IMAGE_TOPIC = "file_image_topic";}
