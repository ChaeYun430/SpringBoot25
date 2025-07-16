package org.mbc.board.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Memo {

    @Id //pk임을 선언
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long mno;
    //GenerationType.IDENTITY -> pk를 자동으로 생성하고자함
    //만일 연결되는 데이터베이스가 오라클이면 번호를 위한 별도의 테이블을 생성 (시퀀스 객체)
    //Mysql이나 MariaDB이면 auto increment를 기본으로 사용해서 새로운 레코드가 기록될 때 다른 번호를 줌
    //GenerationType.AUTO -> jpa가 알아서 생성방식을 결정
    //GenerationType.SEQUENCE -> 데이터베이스의 sequence를 이용해서 키를 생성
    //GenerationType.TABLE -> 키 생성 전용 테이블을 생성해서 키를 생성

    @Column(length = 200, nullable = false) //200글자에 notnull 호출
    private String memoText;
    
}
