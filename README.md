[English(Not available yet)](README.md)  [简体中文](README.md)

# Evolution Of Knowledge 1.16

这是Minecraft 1.16.1版本的模组Evolution Of Knowledge（知识的进化）的代码仓库。

## 目录

- [Evolution Of Knowledge 1.16](#evolution-of-knowledge-116)
  - [目录](#目录)
  - [对于Mod开发者 - 使用我们的API](#对于mod开发者---使用我们的api)
  - [对于Mod开发者 - 构建/参与开发](#对于mod开发者---构建参与开发)
    - [配置基本构建环境](#配置基本构建环境)
    - [编译并生成Mod文件](#编译并生成mod文件)
    - [配置开发环境](#配置开发环境)
      - [1. 使用Intellij IDEA进行开发](#1-使用intellij-idea进行开发)
      - [2. 使用Eclipse进行开发](#2-使用eclipse进行开发)
      - [3. 使用Visual Studio Code进行开发](#3-使用visual-studio-code进行开发)

## 对于Mod开发者 - 使用我们的API

尚未完成

~~我们好像还没有写API~~

## 对于Mod开发者 - 构建/参与开发

**警告：本项目对构建系统做出了一些改动，在开始开发前必须确保你已仔细阅读本文档！**

### 配置基本构建环境

本项目依赖[CuckooLib](https://github.com/zi-jing/CuckooLib/tree/1.16)，在配置开发环境前必须先Clone [CuckooLib](https://github.com/zi-jing/CuckooLib/tree/1.16)的仓库，切换(checkout)到1.16分支并pull。

操作完成后，你需要在EOK项目的根目录下创建文件`gradle.properties`，输入以下内容（**注意：这个文件不会也不应该被添加到Git中，如果它被意外地添加到了Git中，请不要将其push到远程仓库）**：

```properties
CuckooLibProjectPath=../CuckooLib-1.16
```

其中`CuckooLibProjectPath`属性的值即为CuckooLib项目存放的路径。

### 编译并生成Mod文件

**配置好基本构建环境后**，执行`gradlew build`，构建完成后你将能在build/libs目录下找到生成的Mod文件。

### 配置开发环境

**配置好基本构建环境后**，再次编辑`gradle.properties`，设置项目基本属性。

以下是一个示例文件以及各属性的说明：

```properties
# 提示：以'#'开头的行是注释
# 提示：字符'\'应该被替换成'/'，或者使用转义的方法'\\'

# 本模组依赖CuckooLib并与CuckooLib同步开发，
# 开发过程中可能会使用CuckooLib的未发布版本的代码，因此我们需要将CuckooLib
# 项目作为依赖。
# 我们提供了一种使用CuckooLib项目代替正式版CuckooLib的简便方法，只需设置
# "CuckooLibProjectPath"属性即可，属性的值为CuckooLib项目路径。
# 这是一个必选属性
CuckooLibProjectPath=../CuckooLib-1.16

# 以下两个属性用于在生成启动方案时自动添加(正版)登录参数，
# 只需添加"username"和"password"属性即可
# 当"password"属性不存在时会使用离线登录，此时玩家名即为"username"属性的值；
# 当两个属性同时存在时会使用正版登录，此时"username"属性必须是邮箱地址
# 这两个属性均为可选属性
username=zijing_233
password=xxxxxxxx

# 这个属性用于在生成启动方案时自动设置最大内存，
# 该属性值的单位可以为'M'或'G'(实际上这个属性值就是-Xmx后面的内存大小)
# 这个属性为可选属性
memory=4G

# 该属性用于指定是否使用位于项目根目录的本地Maven仓库
# 仅供内部调试使用
# 当它被设为true时，"publishing"任务组中将会出现一个叫做
# "publishMavenJavaPublicationToLocalBuildMaven"的Task，执行它则会在项目
# 根目录的‘LocalBuildMaven’目录生成构件归档
# 这个属性为可选属性
useLocalBuildMaven=true
```

设置完成后，你需要根据你的IDE来选择教程：

#### 1. 使用Intellij IDEA进行开发

直接将项目作为Gradle项目导入并运行`fg_runs`组中的`genIntellijRunsForEOK` Task**(注意：不要运行`gradlew idea`，使用它生成的项目可能会出现无法识别启动方案的奇怪问题)**，等它生成完成后，单击Gradle窗口工具栏右侧的扳手，打开**Gradle Settings**界面，将"Build and run using"和"Run tests using"两个选项都设置为"Intellij IDEA"，然后即可启动Minecraft并进行调试和开发。

**故障排除提示：若在运行`genIntellijRunsForEOK` Task时发生`NullPointerException`，这属于正常情况，可以忽略。**

#### 2. 使用Eclipse进行开发

很抱歉，我们并没有提供针对Eclipse的启动方案修复代码，你只能手动进行修复。

**警告：在进行下一步操作之前你必须确保你已经设置好了Gradle项目，并且Eclipse中能够正确显示两个项目。**

运行`genEclipseRuns` Task，并使用文本编辑器打开生成的runClient.launch、runServer.launch等文件，寻找内容为

```xml
<mapEntry key="MOD_CLASSES" value="cuckoolib%%...bin\main;cuckoolib%%..."/>
```

的一行，将每个`cuckoolib%%`后的路径更改为CuckooLib项目中的对应路径即可。

#### 3. 使用Visual Studio Code进行开发

vscode……可以的

自从ForgeGradle3以来，ForgeGradle也支持生成VSCode的启动方案了。修改方法和Eclipse差不多，也是改一下启动方案文件里的`MOD_CLASSES`环境变量，这里不再赘述。
