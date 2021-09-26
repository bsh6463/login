package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;

  //  @GetMapping("/")
    public String home() {

        return "home";
    }

   // @GetMapping("/")
    public String homeLogin(@CookieValue(name = "memberId", required = false) Long memberId, Model model){

        //홈 화면 처음 접속 - 미 로그인 상태
        if (memberId == null){
            return "home";
        }

        // 쿠키로 id가 넘어왔는데, repository에 없는 경우
        Member loginMember = memberRepository.findById(memberId);
        if(loginMember == null){
            return "home";
        }

        // 쿠키로 id가 넘어왔는데, repository에 존재.
        model.addAttribute("member", loginMember);
        return "loginHome";

    }

    @GetMapping("/")
    public String homeLoginV2(HttpServletRequest request, Model model){

        //세션 관리자에 저장된, 회원 정보 조회
        Member member = (Member) sessionManager.getSession(request);

        //세션에회원 정보가 없는 경우. -> 일반 home
        if(member == null){
            return "home";
        }

        //세션에 회원 정보가 있는 경우.-> login home
        model.addAttribute("member", member);
        return "loginHome";

    }

}