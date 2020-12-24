# tuya_iot

#### 一.介绍

1.个人介绍

网名：小Young,现就职于重庆一家以点火器，调压器，充电模块等研发及生产的公司，我在公司主要负责仪表相关产品的软件开发工作，也就是主要让代码和硬件交流的工作。
虽说已经毕业一年了，但是始终觉得自己在技术上没有多大的进步，我对自己非常不满意，于是着手规划一些自己的技术能力提升计划，想起自己毕设是做智能家居相关的
作品，于是就想打造属于自己的智能家居系统，最终部署到自己的家中。
但是想法固然美好，实现起来却异常艰难，从自己搭建MQtt服务器，自己写APP，自己搞硬件，自己写代码，这些都去做了一遍，但是结果却不敬人意，虽说是实现了远程控制功能，
但是我是一个不太满足的人（可以说比较贪心吧。。。），功能太单一了，有同学说调侃说这是一种简约风。那就继续构造吧，但是系统构建的复杂性，对于个人来说，工作量异常的大，利用业余时间去完成，估计得花不少时间，起初写APP 用E4A写，从零开始写，都写了3000多行，但是自己还是不太满意。
基于以上原因自己不得不重新寻找一个比较规范可靠的物联网平台，基于网联平台来打造自己的智能家居系统。恰巧去年有学校老师找到我，要我完成一套智能家居系统，要有APP。起初选择的是中移物联的onenet 平台，但是也有它的一些缺点，很多都要自己去实现，包括硬件设备去联网，自己还得写物联网模组的代码，也没有较标准化的设备管理协议，虽说是减轻了我的的工作量，但是并没有让我感到满意。直到有一天晚上我躺在床上刷微信时，看到涂鸦公众号（之前参加立创训练营关注的）推送基于APP SDK 开发活动时，我想都没想都报了名，经过这一段时间的对涂鸦的摸索，发现这就是一片新天地呐，哈哈，爱了爱了。标准化的设备管理，丰富的API,零代码物联网模组......哇塞！太完美了，涂鸦牛X。

2.项目介绍

中文：这个项目是基于Tuya App SDK进行开发，它使您能够快速开发连接和控制许多设备智能场景的品牌应用程序。

想了解更多信息，请访问[涂鸦开发者网站](https://developer.tuya.com/en/docs/iot/app-development/sdk-development/app-sdk-instruction？id=K9kjstc7t376p）

English:
 This project is developed using Tuya App SDK, which enables you to quickly develop branded apps connecting and controlling smart scenarios of many devices.
For more information, please check [Tuya Developer Website](https://developer.tuya.com/en/docs/iot/app-development/sdk-development/app-sdk-instruction?id=K9kjstc7t376p)

其他:
主要美化UI布局，以及增加了删除场景、定时触发等功能。
此外，还引入了小爱同学语音控制功能。

#### 二.基本功能（此处跳过android studio 安装及demo配置,官方文档已经非常详细了）
##### 1. UI 美化

![登录页面](https://images.gitee.com/uploads/images/2020/1222/223729_eb6b68d7_5317700.jpeg "505f0ad1e9cfb67d4c2674f3f63b175.jpg")
![输入图片说明](https://images.gitee.com/uploads/images/2020/1222/224119_8d4cda17_5317700.jpeg "797beb03913db606f446262e3d92135.jpg")
![输入图片说明](https://images.gitee.com/uploads/images/2020/1222/223918_e0014496_5317700.jpeg "9edc80756f802b12d3ba596e10ebf34.jpg")
![输入图片说明](https://images.gitee.com/uploads/images/2020/1222/223938_c77d2ae1_5317700.jpeg "3fb811d2dcae13b9f02f0388143a847.jpg")
![输入图片说明](https://images.gitee.com/uploads/images/2020/1222/224032_6c7e9be9_5317700.jpeg "6194ee16a66b7c6a7fc9969804853e5.jpg")
![输入图片说明](https://images.gitee.com/uploads/images/2020/1222/223857_ecb65722_5317700.jpeg "fa86210b16bf103314c93ad30048c27.jpg")
![输入图片说明](https://images.gitee.com/uploads/images/2020/1222/224234_bf24bc9d_5317700.jpeg "fc9d24bb1146e48541e6f75f06f7b0f.jpg")
![输入图片说明](https://images.gitee.com/uploads/images/2020/1222/224243_1016957c_5317700.jpeg "4e2de61e7347637d053aa72c3784fef.jpg")

##### 2. 配网功能
在设备页面（确保设备处于配网状态），点击右上角的加号进入选择设备类型界面，在设备类型界面选择wifi设备进入到添加设备界面，然后选择“指示灯快速闪烁中”按钮进入到配网界面,在配网界面设置你手机连接的wifi密码，点击下一步即可配网，配网成功后，回到设备界面下拉刷新即可看到刚刚配网的设备。

![输入图片说明](https://images.gitee.com/uploads/images/2020/1222/234445_8af0579a_5317700.jpeg "e93dd594097c849f5997b8219617f15.jpg")
![输入图片说明](https://images.gitee.com/uploads/images/2020/1222/234454_89b6f791_5317700.jpeg "57bb4cf2ad391ef5049e3bc38002baa.jpg")
![输入图片说明](https://images.gitee.com/uploads/images/2020/1222/234507_7fe4ff25_5317700.jpeg "8c0b9baa1560b946622a48cee32ba3c.jpg")
![输入图片说明](https://images.gitee.com/uploads/images/2020/1222/234519_38df0fde_5317700.jpeg "c1a539587b6088294cb37e0a3f458f2.jpg")
![输入图片说明](https://images.gitee.com/uploads/images/2020/1222/234631_c0c8649d_5317700.jpeg "797beb03913db606f446262e3d92135.jpg")

##### 3. 删除场景功能
在智能页面，点击背景图片右上角的删除图标（垃圾箱），会弹出一个对话框，用于用户确认是否删除场景，
点击确认后即可删除场景，将页面往下拉刷新一下即可发现之前的场景不见了。

![输入图片说明](https://images.gitee.com/uploads/images/2020/1222/230655_00b673cd_5317700.jpeg "43ffd664b09c373383b4ed88750cde3.jpg")
![输入图片说明](https://images.gitee.com/uploads/images/2020/1222/230859_49e5b1fc_5317700.jpeg "04b9693b3be953190d93c4631b633b6.jpg")

##### 4. 定时触发任务
在智能页面点击最右上角的加号会弹出菜单，点击添加场景进入到添加场景页面，
点击添加任务，其次选择设备，然后选择功能，最后选择操作值，点击下一步即可回到添加场景页面，这个目的是为了获取设备ID,及dp点，
回到添加场景界面后，选择时间会弹出时间选择对话框，选择好时间后点击设置，最后点击设定时间按钮即可设置完成。
APP 通过定时接口设置好定时器信息后，硬件模块会自动根据定时要求进行预订的操作。

![输入图片说明](https://images.gitee.com/uploads/images/2020/1222/232358_ac93e024_5317700.jpeg "9edc80756f802b12d3ba596e10ebf34.jpg")
![输入图片说明](https://images.gitee.com/uploads/images/2020/1222/231528_0d7aed60_5317700.jpeg "添加场景.jpg")
![输入图片说明](https://images.gitee.com/uploads/images/2020/1222/232614_8bdc0ceb_5317700.jpeg "9036c6da30fefb2558582e01e3fb24e.jpg")
![输入图片说明](https://images.gitee.com/uploads/images/2020/1222/232826_24581f88_5317700.jpeg "选择功能.jpg")
![输入图片说明](https://images.gitee.com/uploads/images/2020/1222/232839_dc3e281e_5317700.jpeg "操作者.jpg")
![输入图片说明](https://images.gitee.com/uploads/images/2020/1222/232906_53d5920e_5317700.jpeg "4e2de61e7347637d053aa72c3784fef.jpg")
![输入图片说明](https://images.gitee.com/uploads/images/2020/1222/231658_1e972810_5317700.jpeg "4e2de61e7347637d053aa72c3784fef.jpg")
![输入图片说明](https://images.gitee.com/uploads/images/2020/1222/231719_ef98e381_5317700.jpeg "505f0008c3f6cd22bc242f82fdda5ce.jpg")

##### 4. 小爱同学语音控制
此时我们收到的灯支持语音控制，所以我寻思着，既然支持语音控制，涂鸦这么大的公司应该接入了小米小爱同学吧，一试不知道试了吓一跳，哇！真的可以哎~敲级开森😊。
下面是整个实现的步骤：

首先打开米家app(小米手机自带)，点击底部导航栏”我的“，然后将页面往下滑，找到其他平台设备，点击进去，然后点击底部的”点击添加“，进入到绑定账号页面，
进入到绑定账号页面后往下滑动，找到涂鸦智能，点击后进入到平台详情页面，点击底部的“账号绑定”进入到账号绑定页面，在账号绑定页面输入你登陆涂鸦云平台的账号及密码，
然后点击“立即登录”，登录成功后勾选你的APP，然后点击“立即登录”，登录成功后会进入到授权界面，点击“授权”即可完成设备绑定。注意：授权完成后会默认进入到显示设备列表的页面，如果是空的，点击页面底部的同步设备即可，如果你想删除，只需要点击底部的接触绑定即可。





##### 6. 涂鸦wifi 模块的使用
目的：SDK 是否支持自家所有的模组。
这里用了一块在涂鸦平台上买的wifi 模组。
首先还是给模块上电后配网，步骤同@2.配网功能，配网成功后回到“设备”页面，发现在设备列表里已经添加设备了。
结论：说明SDK 是支持自己其他模组的。

操作和灯的操作都是一样的没什么特别的。

#### 三.源码实现

1.  xxxx
2.  xxxx
3.  xxxx

#### 四.参与贡献

作者：小Young


