package com.wyl.entity;


/*
@作者：wyl
*/
public enum OrderStatus {
    WAIT_UW(1, "待核保"),       // 待核保
    UW_SUCCESS(2, "核保成功"),  // 核保成功
    WAIT_PAY(3, "待支付"),      // 待支付
    PAID(4, "支付成功"),        // 支付成功
    WAIT_ISSUE(5, "待出单"),    // 待出单
    ISSUED(6, "已出单");       // 已出单

    private final int code;
    private final String description;

    OrderStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return code + ":" + description;
    }
}

