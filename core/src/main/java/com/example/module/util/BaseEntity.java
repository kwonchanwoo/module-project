package com.example.module.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import javax.persistence.*;
import java.time.LocalDateTime;

//@Getter
//@MappedSuperclass // BaseEntity를 상속한 엔터티들은 아래 필드들을 컬럼으로 인식하게 된다.
@EntityListeners(AuditingEntityListener.class) // Auditing(자동으로 값 매핑) 기능 추가
@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private boolean deleted;

    @CreatedBy
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedBy
    private LocalDateTime updatedAt;
}