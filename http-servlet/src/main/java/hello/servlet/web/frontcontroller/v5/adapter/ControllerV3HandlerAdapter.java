package hello.servlet.web.frontcontroller.v5.adapter;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v5.MyHandlerAdapter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerV3HandlerAdapter implements MyHandlerAdapter {

    // 핸들러 매핑 검사
    @Override
    public boolean supports(Object handler) {
        return (handler instanceof ControllerV3);
    }

    // 핸들러 호출 결과에 대응하는 modelView 인스턴스 반환
    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws ServletException, IOException {
        ControllerV3 controllerV3 = (ControllerV3) handler;

        Map<String, String> paramMap = createParamMap(request);
        ModelView modelView = controllerV3.process(paramMap);

        return modelView;
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));

        return paramMap;
    }

}
