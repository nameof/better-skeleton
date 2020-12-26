# better-skeleton
a better and clean java/web project skeleton


# 原则
1、分层按照先业务再技术，例如  <br/>
com.xx.user.controller <br/>
com.xx.user.service <br/>
com.xx.blog.controller <br/>
com.xx.blog.service

2、数据对象转换关系 <br/>
① request/query → dto/bo → domain <br/>
② (vo ← ) dto ← domain 
