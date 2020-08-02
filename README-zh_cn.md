[English](README.md)  [简体中文](README-zh_cn.md)

# Evolution Of Knowledge 1.16

这是Minecraft 1.16.1版本的模组Evolution Of Knowledge（知识的进化）的代码仓库。

### 尚未完成

## 对于Mod开发者

### 构建release

下载你需要构建的release的源码，执行`gradlew build`即可。

### 构建快照/参与开发

将此项目仓库和 [CuckooLib仓库的1.16分支](https://github.com/zi-jing/CuckooLib/tree/1.16) clone到本地，在本项目的根目录下创建`gradle.properties`文件，输入以下内容（**注：这个文件不会也不应该被添加到Git中，如果它被意外地添加到了Git中，请不要将其push到远程仓库**）

```properties
# 提示：以'#'开头的行是注释

# 本模组依赖CuckooLib并与CuckooLib同步开发，开发过程中可能会使用CuckooLib的未发布版本的代码，因此我们需要将CuckooLib项目作为依赖。
# 我们提供了一种使用CuckooLib项目代替正式版CuckooLib的简便方法，只需设置'CuckooLibIncludeBuildPath'属性即可，属性的值为CuckooLib项目路径。
# 这是一个必选属性
CuckooLibIncludeBuildPath=../CuckooLib-1.16

# 我们提供了在生成启动方案时自动添加登录信息的简单方式，只需添加'username'和'password'属性即可（当'password'属性不存在时会使用离线登录，此时玩家名即为'username'属性的值；当两个属性同时存在时会使用正版登录，此时'username'属性必须是邮箱地址）
# 这两个属性均为可选属性
username=zijing_233
password=xxxxxxxx

# 我们提供了设置游戏内存限制的简便方法，'memory'属性的单位可以为'M'或'G'
# 这个属性为可选属性
memory=4G

# TODO
```

