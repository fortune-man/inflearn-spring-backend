package hello.servlet.web.frontcontroller.v4.controller;


import hello.servlet.web.frontcontroller.v4.ControllerV4;
import java.util.Map;

public class MemberFormControllerV4 implements ControllerV4 {


    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        // modelView 인스턴스 생성도 필요없이 오직 논리명만 반환하면 되는 단순하고 실용적인 구조
        return "new-form";
    }

}
