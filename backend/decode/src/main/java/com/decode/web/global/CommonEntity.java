package com.decode.web.global;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@ToString
// 엔티티에 공통적으로 들어가는 필드를 정의
// 각 엔티티에서 상속해서 사용하면 DB에 자동으로 필드가 정의됨

public abstract class CommonEntity {

    @CreatedDate
    // 한국 시간으로 맞춰야함

    @Column(name = "created_time", updatable = false, nullable = false)
    private LocalDateTime createdTime;

    @LastModifiedDate
    @Column(name = "updated_time", nullable = false)
    private LocalDateTime updatedTime;

}
