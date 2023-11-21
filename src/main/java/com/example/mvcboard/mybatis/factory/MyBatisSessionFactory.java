/**
 * SQL 구문을 직접 실행하기 위한 SqlSession 인스턴스 생성
 * */

package com.example.mvcboard.mybatis.factory;

import com.example.mvcboard.mybatis.config.MyBatisConfig;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class MyBatisSessionFactory {

    private static SqlSessionFactory sqlSessionFactory = null;

    static {
        MyBatisConfig myBatisConfig = new MyBatisConfig();
        sqlSessionFactory = myBatisConfig.getSqlSessionFactory();
    }

    public static SqlSession getSqlSession() {
        return sqlSessionFactory.openSession();
    }
}
