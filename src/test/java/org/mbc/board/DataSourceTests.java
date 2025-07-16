package org.mbc.board;

import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
@Log4j2
public class DataSourceTests {
    //데이터베이스 연결 테스트용
    @Autowired
    private DataSource dataSource;
    //DataSource 객체는 application.properties에 있는 데이터베이스 정보를 활용한다.

    @Test //import import org.junit.jupiter.api.Test;
    public void testConnection() throws SQLException {

        @Cleanup //시작시 청소
        Connection conn = dataSource.getConnection();

        log.info("데니터베이스 연결 테스트용 객체 :" + conn);
        Assertions.assertNotNull(conn);
    }
}
