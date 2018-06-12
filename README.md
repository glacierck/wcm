##项目结构


| 说明                 | 文件          |
| -------------------------------------------------------------- | ----------------- |
| gradle项目产生文件（自动编译工具产生的文件）                   | .gradle           |
| IDEA项目文件（开发工具产生的文件）                             | .idea             |
| 其中一个module，复用父项目的设置，可与父项目拥有相同的配置文件 | app               |
| 自动构建时生成文件的地方                                       | build             |
| 自动完成gradle环境支持文件夹                                   | gradle            |
| git源码管理文件                                                | .gitignore        |
| gradle 项目自动编译的配置文件                                  | build.gradle      |
| gradle 运行环境配置文件                                        | gradle.properties |
| 自动完成 gradle 环境的linux mac 脚本，配合gradle 文件夹使用    | gradlew           |
| 自动完成 gradle 环境的windows 脚本，配合gradle 文件夹使用      | gradlew.bat       |
| Android SDK NDK 环境路径配置                                   | local.properties  |
| IDEA 项目文件                                                  | *.iml             |
| gradle 项目的子项目包含文件                                    | setting.gradle    |

1.  .gradle .idea 是在分别在 gradle ，IDEA 运行时候会生成的文件，一般这样的文件也不会纳入源代码管理之中。
2.  app文件夹，是其中一个module，里面的文件内容与父类差不多，若没有定义，则在项目中使用父类的设置（意思就是，里面也能包含build.gradle、gradle.properties、setting.gradle 等相关gradle文件，怎么理解？其实每一层都是一个module，整个项目是一个大的 module 而已）
3.  gradle 文件夹，用于保存gradle 下载路径的配置文件位置，用于没有gradle环境的环境初始化使用
4.  build.gradle 项目的编译环境配置，比如制定项目依赖的lib包。
5.  gradle.properties 配置gradle运行环境的文件，比如配置gradle运行模式，运行时jvm虚拟机的大小
6.  gradlew && gradlew.bat 代替gradle 命令实现自动完成gradle环境搭建。配合gradle文件夹的内容，会降到IDEA如何搭配gradlew使用。
7.  local.properties 配置android NDK，SDK的地方，恩，非android项目可能没有这个文件，这个路径根据不同想电脑不同，一般也不会纳入源代码管理之中，一般可以写一个local.properties.simple 文件，告知需要修改该文件名并写上本地SDK NDK 路径。simple文件纳入源码管理之中。
8.  setting.gradle 子项目包含文件，声明当前目录下含有什么module，当然你的app底下加上这样的文件，也能继续在app底下加module。和我第点说的，整个project就是一个大的module，每个module下面还能包含相应的module。如果你理解这个了，其实app目录单独作为一个项目管理也是可以的，，把相应的配置文件配上而已，相当于主目录应用 android 的gradle plugin （下一点会说到这个）
9.  gitignore 该文件是源码管理的配置文件，不在该文讲解。
    既然gradle 是多 module形式，那么我们来看看 setting.gradle 配置的内容
    从上面目录的配置文件内容来看，整个project也算是一个module，如果改module底下还有module，就可以通过setting.gradle配置进来，使得该module底下的gradle，从app module底下可以看出，module最少要含有 build.gradle文件，这个module的项目编译文件，该module依赖什么插件对该目录进行编译都在此配置，比如android与android-library，其他内容可继承父类的

##有哪些功能？

##有问题反馈

##捐助开发者

##感激
 
##关于作者
 