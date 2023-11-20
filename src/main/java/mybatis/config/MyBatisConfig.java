/**
 * mybatis 연결을 위한 클래스 생성
 * 직전 커밋 : d976642
 * */
package mybatis.config;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisConfig {
    private SqlSessionFactory sqlSessionFactory = null;

    public MyBatisConfig() {
        String resource = "mybatis/config/mybatis-config.xml";
        InputStream inputStream = null;

        try {
            inputStream = Resources.getResourceAsStream(resource);

        } catch (IOException e) {
            e.printStackTrace();
        }

        this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

    }  // MyBatisConfig()

    public SqlSessionFactory getSqlSessionFactory() {
        return this.sqlSessionFactory;
    }
}  // class
