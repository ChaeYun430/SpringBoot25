package org.mbc.board.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.mbc.board.domain.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {//영속성 계층의 테스트용

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void testInsert(){
        //데이터베이스에 데이터 주입(c) 테스트 코드
        IntStream.rangeClosed(1,100).forEach(i -> {
            //i 변수에 1~100의 100개 정수를 반복해서 생성
            Board board = Board.builder()
                    .title("제목..."+i)
                    .content("내용..."+i)
                    .writer("사용자..."+i%10)
                    .build(); //@Builder용(setter 대신 더 간단하고 가독성이 좋게)
               // log.info(board);
            Board result = boardRepository.save(board);
            //save메서드는 jpa에서 상속한 메서드로 저장하는 용도
            //이미 존재하면 update를 진행한다.
            log.info("게시물 번호 출력 : " + result.getBno() + " | 게시물의 제목 : " + result.getTitle());
            });//forEach문 종료//IntStream 종료
    }

    @Test
    public void testSelect(){
        Long bno = 100L; //게시물 번호가 100번인 개체를 확인해보자

        Optional<Board> result = boardRepository.findById(bno);
        //Optional에 null 값이 나올 경우를 대비한 객체
        //findById는 select 역할을 한다.
        Board board = result.orElseThrow(); //값이 있으면 넣어라

        log.info(bno + "가 데이터 베이스에 존재합니다.");
        log.info(board);
    }
/*
    Hibernate:
    select
    b1_0.bno,
    b1_0.content,
    b1_0.mod_date,
    b1_0.reg_date,
    b1_0.title,
    b1_0.writer
            from
    board b1_0
    where
    b1_0.bno=?
            2025-07-16T14:18:28.649+09:00  INFO 6124 --- [board] [    Test worker] o.m.b.repository.BoardRepositoryTests    : 100가 데이터 베이스에 존재합니다.
            2025-07-16T14:18:28.657+09:00  INFO 6124 --- [board] [    Test worker] o.m.b.repository.BoardRepositoryTests    : Board(bno=100, title=제목...100, content=내용...100, writer=사용자...100)
*/



    @Test
    public void testUpdate() {
        long bno = 100L; //100번 게시물을 가져와서 수정 후 테스트 종료

        Optional<Board> result = boardRepository.findById(bno);

        Board board = result.orElseThrow(); //가져온 값이 있으면 board 타입에 객체를 넣는다.

        board.change("수정테스트 제목", "수정테스트 내용"); //제목과 내용만 수정할 수 있는 메서드

        boardRepository.save(board); //save 메서드는 없으면 insert, 있으면 update (pk값이)
    }
/*
    Hibernate:
    select
    b1_0.bno,
    b1_0.content,
    b1_0.mod_date,
    b1_0.reg_date,
    b1_0.title,
    b1_0.writer
            from
    board b1_0
    where
    b1_0.bno=?
    Hibernate:
    select
    b1_0.bno,
    b1_0.content,
    b1_0.mod_date,
    b1_0.reg_date,
    b1_0.title,
    b1_0.writer
            from
    board b1_0
    where
    b1_0.bno=?
    Hibernate:
    update
            board
    set
    content=?,
    mod_date=?,
    title=?,
    writer=?
    where
    bno=?*/

    @Test
    public void testDelete(){

        Long bno = 1L;

        boardRepository.deleteById(bno);

    }/*
    Hibernate:
    select
    b1_0.bno,
    b1_0.content,
    b1_0.mod_date,
    b1_0.reg_date,
    b1_0.title,
    b1_0.writer
            from
    board b1_0
    where
    b1_0.bno=?
    Hibernate:
    delete
            from
    board
            where
    bno=?*/

    @Test
    public void testPaging(){
        //findAll()는 모든 리스트를 출력하는 메서드
        //전체 리스트에 페이징과 정렬 기법도 추가해보자

        Pageable pageable = PageRequest.of(0, 5, Sort.by("bno").descending());

        Page<Board> result = boardRepository.findAll(pageable);
        //1장의 종이에 Board 객체를 가지고 있는 결과는 result에 담긴다.
        //page 클래스는 다음 페이지 존재 여부, 이전 페이지 존재 여부, 전체 데이터 개수 등등 계산을 한다.
        log.info("전체 게시물 수 : " + result.getTotalElements());
        log.info("총 페이지 수 : " + result.getTotalPages());
        log.info("편재 페이지 번호 : " + result.getNumber());
        log.info("페이지당 데이터 개수 : " + result.getSize());
        log.info("다음페이지 여부 : " + result.hasNext());
        log.info("시작페이지 여부 : " + result.isFirst());
        //콘솔에 결과를 출력해보자
        List<Board> boardList = result.getContent(); //페이징 처리된 내용을 가져와라

        boardList.forEach(board -> log.info(board));
        //forEach는 인덱스를 사용하지 않고 앞에서부터 객체를 리턴함.
        //람다식 1개의 명령어가 있을 때 활용
    }
/*
    Hibernate:
    select
    b1_0.bno,
    b1_0.content,
    b1_0.mod_date,
    b1_0.reg_date,
    b1_0.title,
    b1_0.writer
            from
    board b1_0
    order by
    b1_0.bno desc   //(bno를 기준으로 내림차순 정렬)
    limit
        ?, ?
    Hibernate:
    select
    count(b1_0.bno) //board 전체 리스트 수를 알아옴
    from
    board b1_0*/
}
