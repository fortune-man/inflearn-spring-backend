package hello.servlet.web.frontcontroller.v5;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerV5 extends HttpServlet {

    private final Map<String, Object> HANDLER_MAPPING_MAP = new HashMap<>();
    private final List<MyHandlerAdapter> HANDLER_ADAPTERS = new ArrayList<>();

    // v3를 지원하는 프론트 컨트롤러 v5
    public FrontControllerV5() {
        initializeHandlerMappingMap();
        initializeHandlerAdapters();
    }


    private void initializeHandlerMappingMap() {
        HANDLER_MAPPING_MAP.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        HANDLER_MAPPING_MAP.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        HANDLER_MAPPING_MAP.put("/front-controller/v5/v3/members", new MemberListControllerV3());
    }

    private void initializeHandlerAdapters() {
        HANDLER_ADAPTERS.add(new ControllerV3HandlerAdapter());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 핸들러 호출
        Object handler = getHandler(request);

        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 핸들러 어댑터 호출, modelView 반환
        MyHandlerAdapter adapter = getHandlerAdapter(handler);
        ModelView modelView = adapter.handle(request, response, handler);

        // viewResolver 호출, view 반환
        String viewName = modelView.getViewName();
        MyView view = viewResolver(viewName);

        // render(model) 호출 -> HTML 렌더
        view.render(modelView.getModel(), request, response);

    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {

        for (MyHandlerAdapter adapter : HANDLER_ADAPTERS) {
            if (adapter.supports(handler)) {
                 return adapter;
            }
        }

        throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다. handler = " + handler);
    }

    private Object getHandler(HttpServletRequest request) {
        // 요청이 오면 HANDLER_MAPPING_MAP에서 조회한 URI에 대응하는 핸들러 조회하여 반환
        String requestURI = request.getRequestURI();
        return HANDLER_MAPPING_MAP.get(requestURI);
    }
}
