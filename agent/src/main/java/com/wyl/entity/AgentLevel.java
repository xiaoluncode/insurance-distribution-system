package com.wyl.entity;


import lombok.Data;

/*
@作者：wyl  
*/
public enum AgentLevel {
    NORMAL("普通", 1),
    ADVANCED("高级", 2),
    EXPERT("专家", 3);

    private final String name;
    private final int rank;

    AgentLevel(String name, int rank) {
        this.name = name;
        this.rank = rank;
    }
}
