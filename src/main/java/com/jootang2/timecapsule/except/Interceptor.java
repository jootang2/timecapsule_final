package com.jootang2.timecapsule.except;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Component
public class Interceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession();
        String capsuleId = request.getRequestURI().split("/")[1];
        try {
            String confirm = session.getAttribute("capsule" + capsuleId + "access").toString();
            // 해당 게시판에 대한 세션이 없으면 catch문으로 넘어감
        }catch(NullPointerException e){
            log.info("해당 캡슐 비밀번호가 없음");
            response.sendRedirect("/%s/board/password/defaultKey".formatted(capsuleId));
            //세션에 비밀번호가 없으면 비밀번호 입력 페이지로 이동.
            return false;
        }
        log.info("해당 캡슐 비밀번호가 있음");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // do what you want ..
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex)
            throws Exception {
        // do what you want ..
    }

}
