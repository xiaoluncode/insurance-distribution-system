package com.wyl.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AgentRegisterDTO {
    @NotNull(message = "userId 不能为空")
    private Long userId;

    @NotNull(message = "姓名不能为空")
    private String name;

    private Long parentId; // 可选，顶级传 null

    private String inviteCode; // 如果要用邀请码，可在后续扩展
}
