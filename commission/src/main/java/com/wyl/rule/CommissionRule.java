package com.wyl.rule;


import com.wyl.entity.Order;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/*
@作者：wyl  
*/
@Service
public interface CommissionRule {
 BigDecimal calculate(Order order);
 String getRuleName();
}
