package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;

  //  @GetMapping("/")
    public String home() {

        return "home";
    }

    @GetMapping("/")
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

}