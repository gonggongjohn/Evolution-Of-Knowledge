## developer diary for MolyOre

#2020/7/27
完成了矿脉世界生成的框架构建。\
矿脉定义包括：\
矿脉名（请勿随便取矿脉名称，因为它将与矿脉核心（矿源）的name有关,参见worldGenVein的说明)。\
矿脉生成权重\
矿脉的y坐标上界与下界\


矿脉核心的生成（目前指定三区块法中的坐标中心和矿脉中心的y坐标指定为矿脉中心，矿脉中心如果是石头方块，则会在其周围六个方块及其本体上生成矿脉核心)\
矿脉核心可使用蒸汽时代以后的各阶采矿机自动获取这个矿脉中的矿石。

尚未完成的工作：\
矿脉定义将在json文件中完成，所有矿脉的列表将在WorldGenVein构造函数中生成\
矿石生成的噪声分布

#2020/7/30
完成了从json文件中读取矿脉结构的代码（读个resource文件夹里的json文件都这么费力我是没想到的\
文件路径:resources/assets/eok/database/vein.json\
notice:\
矿脉中心不在json中初始化，在生成过程中调用随机种子在y的上界和下界中随机取值。\
矿脉中的矿石生成权重之和请勿超过100，否则可能出现问题\
可以小于100，这会使得矿脉生成的矿石密度变小\
orecontained写法：\
ore_name使用的是矿石方块的registername，请记得加模组前标识符（如"minecraft:","eok:"等）\
请严格按照给出的格式写矿脉\
请勿随意修改WorldGenVein.java内的任何内容以及vein.json中已经存在的部分，否则造成核弹爆炸后果自负\
如果确认由于WorldGenVein.java内的代码导致游戏无法正常运行，请注释掉WorldGenHandler.java中的\
“”
GameRegistry.registerWorldGenerator(new WorldGenVein(), 10001);
”“\
一行,并及时与molyOre联系\

#2020/8/2
完成了矿脉生成的代码\
目前只可以使用runclient运行矿脉生成的代码，若使用minecraft client启动会崩端，如要使用minecraft client请按上述所说注释掉那一行\
若要看到测试矿脉，请将WorldGenVein.java中的generateOre函数中
"if(world.getBlockState(pos).getBlock() ==Blocks.STONE) "中的括号内的后面加上||true（即永远为真的条件判断）

#2020/8/5
完成了两个查看矿物生成的工具item\
大锤八十！小锤四十！\
大锤八十：清除人所在区块的全部方块，只留下y=1的基岩。\
小锤四十：清除人所在区块的所有石头、泥土、草方块、沙子、沙砾等地底常见方块，保留所有矿石（在岩层系统加入后要进一步修改）