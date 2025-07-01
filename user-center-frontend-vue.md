# User-Center

## 实操

https://www.codefather.cn/course/1790943469757837313/section/1859494346744762370#heading-11



## 遇到的问题

**idea、webstorm下载插件报错：插件无法下载**

插件下载设置里使用无代理，不要使用自动检测代理



## 笔记

**idea常用插件：**

- Alibaba Java Coding：阿里巴巴代码规范
- Auto Filling Java Call Arguments：自动填充参数
- Background Image Plus：背景图
- Code Glance：代码小地图
- GenerateAllsetter：自动生成所有setter方法
- Key Promoter X：记录快捷键
- Mybatis X：自动生成数据库基本操作代码
- Rainbow：彩虹括号
- Translation：翻译
- Nyan progress Bar：彩虹进度条

**webstorm插件：**

- Background Image Plus：背景图

- Code Glance：代码小地图

- Key Promoter X：记录快捷键

- Rainbow：彩虹括号

- Translation：翻译

- Nyan progress Bar：彩虹进度条

    

**style="min-height: 100vh"**

设置元素的最低高度为视口高度的100%，即整个视口的高度。在CSS中，`vh`（Viewport Height）是一个相对长度单位，表示视口高度的1%。

使用场景：

- 确保元素高度至少为视口高度
- 响应式设计



**基本布局：通用布局、栅格布局**

粘贴框架，自己写样式

**具体组件：登录按钮、导航栏、搜索条**

全部粘贴，可通过编写高优先级样式进行样式修改

**路由跳转：**

路由跳转，并实现刷新页面自动更新当前current值，进而高亮当前选中菜单

**路由请求：**

1. 创建request.ts配置文件，用于自定义请求响应拦截器和请求实例

2. 创建api/user.ts，编写每个请求接口用于发送后端请求

3. 后端解决跨域问题

```java
 @CrossOrigin(origins = {"http://localhost:3000"}, allowCredentials = "true") 
```

**全局状态管理：**

1. 引入pinia全局状态管理库
2. 创建store/useLoginUser.ts配置全局状态和一些全局状态的set、get方法
3. App.vue中调用全局状态的set方法设置全局状态
4. 其他页面中使用全局状态

**前端页面开发：**

1. 按照规范创建页面，并修改路由
2. 依次开发每个页面，利用 Ant Design组件库，复制粘贴，修改其结构，样式

注：对于管理员用户访问的页面需要做 全局权限校验（access.ts），记得在main.ts中引入以生效

**部署：**

多环境：根据 process.env.NODE_ENV 改变项目请求后端域名

- 开发环境：npm run serve
- 生产环境：npm run build，生成dist打包文件，打包会自动进行代码加密、体积压缩等工作
    - npm i -g serve，进入dist目录，输入serve命令即可启动项目