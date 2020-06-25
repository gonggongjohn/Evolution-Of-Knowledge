[English](README.md)  [简体中文](README-zh_cn.md)

# Evolution Of Knowledge 1.12

This is the development repository of the Minecraft mod The Evolution Of Knowledge on Minecraft 1.12.2.

# Table Of Contents

- [Evolution Of Knowledge 1.12](#evolution-of-knowledge-112)
- [Table Of Contents](#table-of-contents)
- [Introduction](#introduction)
- [About Development](#about-development)
- [Features](#features)
- [Development Guide](#development-guide)
  - [How to Build](#how-to-build)
  - [Working with CuckooLib](#working-with-cuckoolib)

# Introduction
The Evolution Of Knowledge is a Minecraft mod trying to redefine the general idea of existing technology mods. 
Rather than simply adding a huge number of components and recipes to recurrent the work flow of modern industrial productions, EOK try to recurrent the human civilization from a developmental perspective. By introducing the concept of REIM (Research-Experiment-Improvement-Manufacturing), EOK can give players a panoramic and intuitive understanding of developments of science and technology in human history.

# About Development
1. EOK is maintained by Cuckoo Dev Team and the mod is still under development. There is no open-release version of this mod for now. If you have any issues or suggestions on the mod, you can directly contact us or [start an issue](https://github.com/gonggongjohn/EOK-1.12/issues).
2. EOK has 4 dependencies so far. The following links can directly point to the repositories of these dependencies:
* GregTech Community Edition [Github Repo](https://github.com/GregTechCE/GregTech)
* Code Chicken Lib [Github Repo](https://github.com/TheCBProject/CodeChickenLib)
* Terra Metal Craft [Github Repo](https://github.com/Os-Ir/Terra-Metal-Craft)
* Manor Life [Github Repo](https://github.com/gonggongjohn/Manor-Life-1.12)

# Features
* Minecraft Version: 1.12.2
* Forge Version: 14.23.5.2847
* Current Mod Version: 1.12.2-1.0.1.9-alpha

# Development Guide

## How to Build

If you are using Windows, execute the command below in the project root directory:

```
gradlew build
```

or if you're using Linux/Unix:

```
./gradlew build
```

Then you can find the archives in the directory `build/libs`.

## Working with CuckooLib

[CuckooLib](https://github.com/zi-jing/CuckooLib) is a dependency library of the EOK project. It includes plenty of reusable code which is used by Cuckoo Dev Team.

If you want to debug EOK more easily, or you want to debug CuckooLib with EOK, you can clone the [CuckooLib repository](https://github.com/zi-jing/CuckooLib), then create the file `gradle.properties` in the EOK project's root directory, add the folllowing contents:

```properties
CuckooLibIncludeBuildPath=../CuckooLib
```

In the above code, key `CuckooLibIncludeBuildPath` refers to the CuckooLib project's root path.

Save the files, refresh the Gradle project in your IDE, then the CuckooLib project will be imported.