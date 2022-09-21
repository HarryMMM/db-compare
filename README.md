# db-compare

## 用法
``
usage: db-compare 用来比较数据库表的差异的工具
 -c,--custom <arg>          自定义比较数据库,与mode参数互斥，优先使用mode.参数格式如下：
                            dialect:ip:port:username:password:alias|dialec
                            t:ip:port:username:password:alias
                            dialect: 必填，数据库方言：目前支持mysql|mysql8
                            host: 必填，数据库主机地址
                            port: 可选，默认使用数据库的缺省端口
                            username: 必填，数据库账户
                            password: 必填，数据库密码
                            alias: 可选，数据库别名，输出报告使用。没有的话使用IP当做别名
                            示例 ：-c
                            mysql8:192.168.13.70:3306:user:pwd
***REMOVED***:test|mysql8:192.16
                            8.13.175:3306:root:pwd:dev
 -e,--exclude-table <arg>
                            不参与对比的表信息,与-t互斥，同时设置优先使用-t参数，忽略此参数,默认排除undo_lo
                            g表。eg. -e table1,table2
 -f,--full-mode             比对报告输出所有表的比对信息。不加此参数默认只输出有差异的表信息
 -h,--help <arg>            帮助信息
 -m,--mode <arg>            指定要使用的模式，custom，优先使用此参数。内置以下几个:
                            1. dev vs test
                            2. test vs pre
                            3. pre vs prod
 -s,--schema <arg>
                            比对的schema，可结合-e或-t使用。指定某些schema.默认比较scp全部的sche
                            ma. eg. -s schema1,schema2
 -t,--table <arg>           参与比对的表信息。与-e互斥,同时设置优先使用此参数，忽略-e。 eg. -t
                            table1,table2
``