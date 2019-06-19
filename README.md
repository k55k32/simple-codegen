# simple-codegen 代码生成器
> 基于 freemarker的代码生成器, 主要针对后端管理平台一些通用的类生成。原理是将已编写好的模板，通过传入参数，
利用freemarker渲染成文件，然后生成到指定目录。


### 插件配置
1. `pom.xml` 添加插件, 配置好生成参数, 然后执行 `mvn simple-codegen:codegen`
```xml
<plugin>
    <groupId>com.diamondfsd</groupId>
    <artifactId>simple-codegen</artifactId>
    <version>1.0.3</version>
    <configuration>
        <!--模板路径-->
        <templatePath>src/main/resources/code-template</templatePath>
        <!--目标路径-->
        <sourcePath>src/main/java/</sourcePath>
        <!--pojo参数，传入 表名/Model名称,主键类型-->
        <pojos>
            <pojo>table_name,Integer</pojo>
            <pojo>ModelName,Long</pojo>
        </pojos>
        <!--自定义参数-->
        <params>
            <youSelfKey>youSelfValue</youSelfKey>
            <youSelfKey2>youSelfValue2</youSelfKey2>
        </params>
    </configuration>
</plugin>
```

### 插件配置说明
- templatePath: 模板文件夹路径，默认`src/main/resources/code-template`
- sourcePath: 生成源码输出基础目录, 默认 `src/main/java/`
- pojos: 预置的参数，接受一个Model名称和Java类型作为参数，每一个pojo会生成一遍所有模板内的内容
- params: 自定义参数，可以加一些自定义参数在模板内使用

### 模板范例
模板以 `!#` 开头，后面是生成好文件的目标路径和名称，会自动加上 `sourcePath` 配置，
因为是`ftl`模板，可以用freemark语法，大部分情况情况下，我们只需要用到`${xxx}`来替换字符串
> 模板内参数在插件的 pojos内配置，可以配置多条，多条会生成多次      

默认可用参数如下:
1. name: 模型类名称, `table_name -> TableName`, `ModelName -> ModelName`
2. nameCamelCase: 模型名称驼峰, `table_name -> tableName`, `ModelName -> modelName`
3. nameSplit: 模型名称使用`-`分割, `table_name -> table-name`, `ModelName -> model-name`
4. primaryKeyType: 主键类型, `Any -> Any`, `any -> any`

```ftl
!# com/diamondfsd/report/model/${name}.java
package com.diamondfsd.report.model;

import com.diamondfsd.report.jooq.tables.pojos.${name}Pojo;

public class ${name} extends ${name}Pojo {
}
```