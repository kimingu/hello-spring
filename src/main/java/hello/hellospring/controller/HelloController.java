package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

// 처음 컨트롤러 생성
@Controller
public class HelloController {
    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("data","hello!!");

        return "hello";
    }

    // @RequestParam은 기본적으로 required 가 true이므로 값을 무조건 넘겨야함
    // 값을 넘기고 싶지 않을 경우 @RequestParam(value = "name", required = false)
    // 이런식으로 지정해줘야함
    // command + p 단축키로 확인 가능
    @GetMapping("hello-mvc")
    public  String helloMvc(@RequestParam("name") String name, Model model){
        model.addAttribute("name",name);
        return "hello-template";
    }

    // ResponseBody 그대로 뿌려준다 view가 필요없음
    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name){
        return "hello " + name;
    }

    // 이걸 보통 api 형식이라고 부름
    // view 화면단이 필요 없음
    // 단순 문자열이 아닌 데이터(객체)를 보냄 기본 json 방식으로 {"name":"@RequestParam으로 받은 값"} 보냄
    // ResponseBody인 경우 viewResolve가 아닌 HttpMessageConverter이 객체인지 문자열인지 체크하고 보내줌
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    // static 사용 하여 클래스를 만들면 클래스 안에서도 클래스 사용 가능
    // get,set터 방식 자바 been방식, 프로퍼티 방식이라고 부름
    static class Hello{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
