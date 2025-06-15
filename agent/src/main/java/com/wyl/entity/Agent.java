package com.wyl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("agent")
public class Agent {
 @TableId(type = IdType.AUTO)
 private Long id;

 private Long userId;      // 关联 user.id
 private String name;      // 代理人姓名
 private Long parentId;    // 上级代理
 private String level;     // 普通、高级、专家
 private String status;    // 1=正常，0=禁用
 private LocalDateTime createdAt;
}


