# EasyApi
项目介绍：
基于 React + Spring Boot + Dubbo+ Gateway 的 API 接口开放调用平台。管理员可以接入并发布接口，可视化各接口调用情况；用户可以开通接口调用权限、浏览接口及在线调试，并通过客户端 SDK 轻松调用接口。

1. 整个项目后端划分为 web 系统、模拟接口、公共模块、客户端 SDK、API 网关这 5 个子项目，并使用 Maven 进行多模块依赖管理和打包。
2. 使用 Ant Design Pro 脚手架 ＋ 自建 Spring Boot 项目模板快速构建初始 web 项目，并实现了前后端统一权限管理、多环境切换等基础能力。
3. 基于 MyBatis Plus 框架的 QueryWrapper 实现对 MySQL 数据库的灵活查询，井配合MyBatis × 插件自动生成后端 CRUD 基础代码，减少重复工作。
4. 前端：后端使用 Swagger + Knife4j 自动生成 OpenAP 规范的接口文档，前端在此基础上使用插件自动生成接口请求代码，降低前后协作成本。
5. 为防止接口被恶意调用，设计 API 签名认证算法，为用户分配唯一ak / sk 以鉴权，保障调用的安全性、可溯源性（指便于统计接口调用次数）.
6. 为解决开发者调用成本过高的问题（须自己使用 HTTP＋封装签名去调用接口，平均 20 行左右代码），基于 Spring Boot Starter 开发了客户端 SDK：一行代码 即可调用接口，提高开发体验。
7. 前端：使用 ECharts 可视化库实现了接口调用的分析图表（如饼图），并通过loading 配置提高加载体验。
8. 选用 Spring Cloud Gateway 作为 API 网关，实现了路由转发、访问控制、 流量染色，并集中处理签名校验、请求参数校验、接口调用统计等业务逻辑，提高安全性的同时、便于系统开发维护。
9. 为解决多个子系统内代码大量重复的问题，抽象模型层和业务层代码为公共模块，并使用Dubbo RPC 框架实现子系统间的高性能接口调用，大幅减少重复代码。
