# 数据库版本控制使用规范
---
### 1. 什么是数据库版本控制
用一种简单、干净的方案，帮助开发者完成数据库协同更变的工作。
本项目使用flyway框架，只要创建好数据库，无需手动建表，运行项目便能自动完成数据库的更变。

### 2. 使用场景
开发过程中，当数据库发生更变时，要求开发者提交新的`版本增量`
如：`V10.000000__test_table.sql`。
注意: 仅提交数据库的更变部分，无更变部分不需要提交，原则上应该尽量保护好每位开发者数据库的数据。


### 3. 快速使用
- 创建数据库，并指定数据库名。只要在配置文件设置:
```
  flyway:
    # 指定数据库名，一般指定为spring.datasource.url 配置的那个 schema
    schemas: xxx
```

- 启用flyway。需要在配置文件设置
```
  flyway:
    # 启用或禁用 flyway
    enabled: true
```
- 版本增量的创建。在`db\migration`目录下创建
```
    例：V2.000000__add_hello_table.sql
```
- 写入SQL语句, 运行项目即可
```sql
CREATE TABLE `hello`  ( `id` bigint(20) NOT NULL)ENGINE = InnoDB;
```
### 4. 使用规范
- 起名规范
```
前缀V+版本号+双下划线+行为+下划线+操作对象
例: V2.000000__add_hello_table.sql

版本号的写法
用`.`分割成2个数字
前面的数字: 以天为变化单位， 迭代增1
后面的数字: 现时分秒

```
- 内容规范
```
SQL文件首行 时间(年/月/日 时分秒)+作者名
例: /* 2020/02/02 202002 mission */
```

### 5.注意事项
- 尽量使用IF EXISTS等语句，减少报错率
- 仅提交数据库的更变部分作为，无更变部分不需要提交
- 按照规范提交
- 运行成功`验证`后才提交文件
- 一般情况是不能更改已提交的`版本增量`。如有需要，做好沟通工作
- sql语句中不要出现数据库库名，如:
``` SQL
INSERT INTO `aboot`.`hello`(`id`) VALUES (1);
要改成
INSERT INTO `hello`(`id`) VALUES (1);
```
### 6.小技巧
- 建表语句获取
> 安装打开HeiSQL，点击表，找到`CREATE代码`，记得前面加上`IF EXISTS`语句
- 数据变化语句获取
> 安装打开HeiSQL，点击数据 ->选定变化的行 ->鼠标右键 ->`导出表格的行` ->选SQL ... ->点确定

- 字段变化语句获取
> 安装打开HeiSQL， 修改数据库字段 ->在`日志框`中选择复制

- 解决外键约束的麻烦
``` SQL
//开头
SET FOREIGN_KEY_CHECKS = 0;

//主要SQL

SET FOREIGN_KEY_CHECKS = 1;
//结尾
```
- 临时版本增量
> 未写完或暂不使用的增量可先保存为txt格式
- 版本增量过多
> 前面所有增量打包并备份，并创建一个整合增量 
