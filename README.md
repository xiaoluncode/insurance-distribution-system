# 保险分销系统 - 系统说明文档

## 1. 系统概述

本系统是一个基于 Spring Cloud 微服务架构的保险产品分销与佣金结算平台。系统核心目标是管理代理人层级关系、处理保险订单、并根据一套灵活、可扩展的规则引擎自动计算和结算各级代理人的佣金。

### 核心架构
- **技术框架**: Spring Boot + Spring Cloud Alibaba
- **服务注册与发现**: Nacos
- **分布式事务**: Seata
- **服务调用**: OpenFeign
- **数据持久化**: MySQL + MyBatis Plus
- **身份认证**: JWT
- **服务网关**: Spring Cloud Gateway

---

## 2. 核心模块分析

系统主要由以下几个微服务模块构成：

### 2.1 代理人模块 (`agent-service`)

- **功能描述**: 负责管理代理人的基本信息、层级关系、团队结构和业绩。
- **实现方式**:
    - **层级关系**: `agent` 表中的 `parentId` 字段构成了代理人之间的树状推荐关系。
    - **团队查询**: 使用 SQL 的递归查询（`WITH RECURSIVE`）来高效地查询某个代理人的整个团队（包括所有下级）。
- **核心业务逻辑**:
    - **代理人注册**: 创建新的代理人并关联到上级。
    - **团队业绩查询**: 聚合指定团队（通过 `findAllMembers` 获取所有成员ID）在某月的 `commission_summary_monthly` 表中的业绩总和。
- **API 接口**:
| 方法 | 路径 | 描述 |
| :--- | :--- | :--- |
| `POST` | `/agent/register` | 注册新代理人 |
| `GET` | `/agent/team` | 查询某代理人的团队成员 |
| `GET` | `/agent/getLevelById/{agentId}` | 获取代理人等级 |
| `GET` | `/agent/getMonthlyPerformance` | 查询团队月度业绩 |


### 2.2 订单模块 (`order-service`)

- **功能描述**: 负责处理保险订单的创建、查询和状态流转。
- **实现方式**:
    - **订单状态机**: 采用了经典的**状态模式**（State Pattern）来管理订单从"待核保"到"已出单"的整个生命周期。这是本模块最核心的设计。
    - **`OrderStateMachine`**: 状态机调度器，负责根据订单当前状态，找到对应的状态处理器 (`OrderStateHandler`) 并执行。
    - **`OrderStateHandler`**: 状态处理器接口，每个实现类（如 `WaitUnderwriteStateHandler`）负责一个具体状态的业务逻辑和状态扭转。所有处理器都作为 Spring Bean 被自动注入到状态机中。
- **核心业务逻辑**:
    - **订单创建**: 生成订单，并设置初始状态。
    - **状态更新**: 通过调用 `/order/updatestatus/{orderNo}` 触发状态机，自动执行当前状态的业务逻辑并流转到下一个状态。
- **API 接口**:
| 方法 | 路径 | 描述 |
| :--- | :--- | :--- |
| `POST` | `/order/add` | 创建新订单 |
| `GET` | `/order` | 分页查询订单 |
| `PUT` | `/order/updatestatus/{orderNo}` | 驱动订单状态流转 |

### 2.3 佣金模块 (`commission-service`)

- **功能描述**: 系统核心模块，负责根据复杂的业务规则计算订单佣金，并进行日、月、年度的汇总统计。
- **实现方式**:
    - **佣金计算引擎**: 采用**责任链模式**（Chain of Responsibility Pattern）和**策略模式**（Strategy Pattern）相结合的设计。
    - **`CommissionRuleChain`**: 规则链，按顺序调用所有佣金计算规则。
    - **`CommissionRule`**: 规则接口，每个实现类（如 `AgentLevelBonusRule`）代表一种独立的佣金计算策略。所有规则都作为 Spring Bean 被自动注入，非常易于扩展新规则。
    - **定时任务**: 使用 `@Scheduled` 注解 (`CommissionSummaryScheduler`) 定时触发每日、每月、每年的佣金聚合结算。
- **核心业务逻辑**:
    - **实时计算**: 当订单支付成功后，调用 `/commission/calculate` 接口，佣金计算引擎会遍历所有规则，累加计算出总佣金。
    - **分层聚合**:
        1.  **每日汇总**: 定时任务聚合当天的佣金明细 (`commission` 表)，写入 `commission_summary_daily` 表。
        2.  **每月汇总**: 定时任务聚合当月的每日汇总，写入 `commission_summary_monthly` 表。
        3.  **年度汇总**: 定时任务聚合当年的每月汇总，写入 `commission_summary_yearly` 表。
- **API 接口**:
| 方法 | 路径 | 描述 |
| :--- | :--- | :--- |
| `POST` | `/commission/calculate` | （核心）根据订单计算佣金 |
| `GET` | `/summary/daily/selectByMonth`| 查询某月每日汇总 |
| `GET` | `/summary/monthly/selectByYear`| 查询某年每月汇总 |
| `GET` | `/summary/yearly/select` | 查询年度汇总 |


### 2.4 其他模块

- **认证模块 (`auth-service`)**: 基于 JWT 为用户提供登录和注册功能，颁发 Token。
- **产品模块 (`product-service`)**: 提供保险产品的基本 CRUD 管理功能。
- **网关模块 (`gateway-service`)**: 作为所有服务的统一入口，并集成 JWT 过滤器进行统一的身份校验。
- **通用模块 (`common-module`)**: 存放通用工具类、实体、全局异常处理器 (`GlobalExceptionHandler`) 和标准响应体 (`ResultVo`)。

---

## 3. 技术栈列表

- **后端**: Spring Boot, Spring Cloud Alibaba
- **数据库**: MySQL
- **持久层**: MyBatis-Plus
- **服务治理**: Nacos
- **分布式事务**: Seata
- **服务调用**: OpenFeign
- **服务网关**: Spring Cloud Gateway
- **身份认证**: JWT (jjwt)
- **其他**: Lombok, Maven

---

## 4. 如何运行

1.  确保本地已安装并启动 MySQL, Nacos, Seata-Server。
2.  在 Nacos 中为每个服务创建对应的配置文件，并添加 Seata 所需的配置项 (`service.vgroupMapping...`)。
3.  依次启动 `auth-service`, `agent-service`, `product-service`, `order-service`, `commission-service`, `gateway-service`。
4.  通过 API 测试工具（如 Postman, Apifox）调用 Gateway (例如 `http://localhost:8080`) 暴露的 API 接口进行测试。
    -   先调用 `/auth/login` 获取 Token。
    -   后续请求在 Header 中携带 `Authorization: Bearer <token>`。 
