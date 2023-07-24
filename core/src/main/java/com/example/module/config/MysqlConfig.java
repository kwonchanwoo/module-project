package com.example.module.config;

import org.hibernate.dialect.MySQL57Dialect;
import org.springframework.context.annotation.Configuration;

/**
 * application.yml설정에서 hibernate ddl-auto:create로 서버를 재시작하면
 * 기본 character set이 latin으로 잡혀서 해당메소드로 문제 해결
 */
@Configuration
public class MysqlConfig extends MySQL57Dialect {
    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}
