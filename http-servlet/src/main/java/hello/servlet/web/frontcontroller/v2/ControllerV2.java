package hello.servlet.web.frontcontroller.v2;

import hello.servlet.web.frontcontroller.MyView;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface ControllerV2 {

    // 좋은 인터페이스 설계인 이유 : 반환 타입이 view임을 명시
    MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
