package com.cloud.photo.common.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author weifucheng
 * @since 2023-06-13
 */
@Data
@ToString
public class UserFileBo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String userFileId;

    private String userId;

    private String fileName;

    private String parentId;

    private Long fileSize;

    private String fileStatus;

    private String fileType;

    private String isFolder;

    private LocalDateTime createTime;

    private LocalDateTime modifyTime;

    private String storageObjectId;

    private Integer category;

    private Integer auditStatus;

}
