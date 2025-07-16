package org.mbc.board.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@Log4j2
public class SampleController {
    //컨트롤러는 url생성과 프론트를 연결하는 부분으로 과거의 servlet-context와 같은 역할을 함

    @GetMapping("/hello") // http://192.168.111.105:80/hello -> void -> hello.html
    public void hello(Model model){
        log.info("SampleController.hello 메서드 실행.... ");

        model.addAttribute("msg", "안녕하세요 자바의 종점입니다.");
    }

    @GetMapping("/ex/ex1")   //http://192.168.111.105:80/ex/ex1 -> resources/templates/ex/ex1.html
    public void ex1(Model model){
        //list타입으로 데이터를 보내보자
        List<String> list = Arrays.asList("김기원", "김기원", "김기원", "김기원", "김기원");

        model.addAttribute("list", list);
    }

    class SampleDTO{
        private String p1,p2,p3;

        public String getP1() {
            return p1;
        }

        public String getP2() {
            return p2;
        }

        public String getP3() {
            return p3;
        }

        //이너 클래스 안쪽에 클래스를 선언할 때 활용된다.
    }

    @GetMapping("/ex/ex2")
    public void ex2(Model model){
        log.info("SampleController.ex2 메서드 실행..");
        //이너 클래스를 사용해서 객체를 뿌려보자

        List<String> list = IntStream.range(1,10) //1~10의 정수를 사용한다.
                .mapToObj(i -> "데이터"+i)
                .collect(Collectors.toList()); //리스트에 정수 문자열 생성

        Map<String, String> map = new HashMap<>();
        map.put("id", "kkw");
        map.put("pw","1234");

        SampleDTO sampleDTO = new SampleDTO();
        sampleDTO.p1 = "값.......p1";
        sampleDTO.p2 = "값.......p2";
        sampleDTO.p3 = "값.......p3";
        //객체 3개 완성

        model.addAttribute("list",list);
        model.addAttribute("map",map);
        model.addAttribute("dto", sampleDTO);
    }

    @GetMapping("/ex/ex3")
    public void ex3(Model model){
        log.info("SampleController.ex3 메서드 실행");

        model.addAttribute("arr", new String[]{"김기원, 김기원, 김기원"});
    }
}
