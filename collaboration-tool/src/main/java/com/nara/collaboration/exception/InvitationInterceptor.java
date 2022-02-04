package com.nara.collaboration.exception;

import com.nara.collaboration.entity.User;
import com.nara.collaboration.repository.InvitationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class InvitationInterceptor implements HandlerInterceptor {

    private final InvitationRepository notificationRepository;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (modelAndView != null && !isRedirectView(modelAndView) && authentication != null) {
            User user = ((User)authentication.getPrincipal());
            long count = notificationRepository.countByUser(user); // 알림의 개수를 구한다.
            modelAndView.addObject("hasNotification", count > 0); // 알람이 있는 경우 true, 없으면 false
        }
    }

    private boolean isRedirectView(ModelAndView modelAndView) {
        return modelAndView.getViewName().startsWith("redirect:") || modelAndView.getView() instanceof RedirectView;
    }

}
