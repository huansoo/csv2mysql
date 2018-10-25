本工具将csv中内容，导入到mysql中，只需做配置的修改，不用修改代码。
工具采用java的main方法执行
使用maven打包jar的命令可使用如下：
clean package install -DskipTests


1、首先在application.properties中配置需要导入的模块名称,多个模块名称之间用“,”分割。如：attendance
2、在resources目录下新建模块名称对应的properties文件，文件内存储需要导入的表和字段信息，具体如下：
    delimiter: csv文件中内容的分割符
    table: 标识 需要将csv中数据导入到哪张表
    csv: 存放数据csv文件名称，文件必须在resources/csv路径下
    fields: 标识 表中的字段对应的名称
            *************************************warning*****************************************************

            ******************  fields字段顺序需要和csv中列的顺序保持一致，否则会导致插入的值和列不匹配问题****************

            *************************************warning*****************************************************x



示例：将ldap的csv文件（ldap_tables.csv）导入到csv对应的mysql库表中(ueba_ldap)
1、首先在application.properties中添加模块名称ldap，名称可以任意，本示例假设叫做ldap模块
2、在resource目录下新建ldap.properties文件，文件内容如下：
	table:ueba_ldap
	delimiter:,
	fields: setid,dept_id,org_name,org_short_name,position_eff_date,position_eff_status,cmbc_dept_type,parent_node_num,parent_set_num,company,tree_level_num,usr_managetype,org_order
	csv: ldap_table.csv
3、新建表ueba_ldap，并创建相应的字段，字段需要与步骤2中的fields属性值相同
4、执行java -jar labdata-1.0-SNAPSHOT.jar即可，数据已经从csv导入到mysql中