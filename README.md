# better-skeleton
a better and clean java/web project skeleton, better layer practice. 更好的分层实践 <br/>
在实现适当的关注点分离与避免过度设计、无意义的重复之间找到平衡
# 原则
1、分层按照先业务再技术，例如  <br/>
com.xx.user.controller <br/>
com.xx.user.service <br/>
com.xx.blog.controller <br/>
com.xx.blog.service

2、数据对象转换关系 <br/>
① request → domain <br/>
② dto ← domain  <br/>

3、更好的设计实践 <br/>
学习DDD吧
