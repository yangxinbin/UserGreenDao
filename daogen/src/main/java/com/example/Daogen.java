package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class Daogen {

        public static void main(String[] args) throws Exception{

            //参数1：数据库版本
            //参数2：自动生成代码默认包
            Schema schema = new Schema(3,"com.example.db");

            //一个实体类关联到一张表，表名：UserInfo
            Entity userInfo = schema.addEntity("UserInfo");

            //添加字段
            userInfo.addIdProperty();//唯一标识
            userInfo.addStringProperty("usernumber");
            userInfo.addStringProperty("userpassword");

            String outDir ="app/src/main/java-gen";
            System.out.println(outDir);
            new DaoGenerator().generateAll(schema,outDir);
        }

}
