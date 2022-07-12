package com.pado.calculator.common;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@EntityListeners(AuditingEntityListener.class) // PreUpdate, PrePersit 등 엔티티에 라이프사이클과 관련된 이벤트가 존재하는데 해당 이벤트를 listen 해줌
@MappedSuperclass // entity는 entity 끼리만 상속받을 수 있음. 엔티티 클래스에서 일반 클래스를 상속받기 위한 어노테이션
public class BaseEntity {

    @CreatedDate
    @Column(updatable = false) //  생성 날짜는 수정되지 못하도록
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;


}