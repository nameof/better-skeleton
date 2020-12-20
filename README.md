# better-skeleton
a better and clean java/web project skeleton


# 原则
1、分层按照先业务再技术，例如
com.xx.user.controller
com.xx.user.service
com.xx.blog.controller
com.xx.blog.service

也就是整体基于业务拆分，也符合微服务模块化思想
但如果按照这样，对maven分工程拆分后，如何解决业务模块之间相互依赖？

2、技术层的包不能再细分了，2层足够，例如
controller
service
external
client
dao
model