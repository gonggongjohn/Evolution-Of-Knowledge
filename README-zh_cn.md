[English](README.md)  [简体中文](README-zh_cn.md)

# Evolution Of Knowledge 1.12

这是Minecraft 1.12.2版本的模组Evolution Of Knowledge（知识的进化）的代码仓库。

# 目录

- [Evolution Of Knowledge 1.12](#evolution-of-knowledge-112)
- [目录](#目录)
- [模组介绍](#模组介绍)
- [开发状态](#开发状态)
- [模组基本信息](#模组基本信息)
- [开发者指南](#开发者指南)
  - [如何构建](#如何构建)
  - [与CuckooLib协同开发](#与CuckooLib协同开发)

# 模组介绍
Evolution Of Knowledge是一个尝试重新定义已有的科技模组思路的模组。

与简单地添加巨量零件和配方来重现现代工业生产流程的模组不同，EOK正尝试以发展的角度重现人类文明历程。通过引入REIM（研究-实验-提升-制造）的概念，EOK可以给玩家们一个对人类历史上科学技术发展过程的一个全局理解。

# 开发状态
1. EOK目前由Cuckoo开发团队维护，目前处于非常早期的版本；EOK目前还没有发布公开的正式版本。如果你对这个模组有意见或建议，你可以直接联系我们或者[提出issue](https://github.com/gonggongjohn/EOK-1.12/issues)。
2. EOK目前有4个依赖项。下面的链接指向每个依赖项所在的仓库：
* GregTech Community Edition [Github仓库](https://github.com/GregTechCE/GregTech)
* Code Chicken Lib [Github仓库](https://github.com/TheCBProject/CodeChickenLib)
* Terra Metal Craft [Github仓库](https://github.com/Os-Ir/Terra-Metal-Craft)
* Manor Life [Github仓库](https://github.com/gonggongjohn/Manor-Life-1.12)

# 模组基本信息
* Minecraft版本: 1.12.2
* Forge版本: 14.23.5.2847
* 目前模组版本: 1.12.2-1.0.1.9-alpha

# 开发者指南

## 如何构建

如果你在使用Windows，请在项目根目录执行以下命令：

```
gradlew build
```

如果你使用的是Linux/Unix：

```
./gradlew build
```

构建完成后，你可以在`build/libs`目录找到生成的归档文件。

## 与CuckooLib协同开发

[CuckooLib](https://github.com/zi-jing/CuckooLib)是EOK项目的一个依赖库，包含了Cuckoo Dev Team需要用到的大量可重用代码。

如果你想更好地调试EOK项目或利用EOK项目来调试CuckooLib，你可以clone [CuckooLib仓库](https://github.com/zi-jing/CuckooLib)，然后在项目根目录创建文件`gradle.properties`，添加以下内容：

```properties
CuckooLibIncludeBuildPath=../CuckooLib
```

其中，键`CuckooLibIncludeBuildPath`的值为CuckooLib项目根目录路径。

保存文件，在IDE中刷新Gradle项目，即可引入CuckooLib项目。