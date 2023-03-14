package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;
import java.util.Map;

public interface ControllerV3 {

    // 서블릿 의존성 제거
    ModelView process(Map<String, String> paramMap);
}
