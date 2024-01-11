package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {

    // 저장소 우선 생성
    private final MemberRepository memberRepository = new MemoryMemberRepository();

    /*
    * 회원가입
    * */
    public Long join(Member member){
        // 같은 이름이 있는 중복 회원x
        validateDuplicateMember(member);

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // Optional인 경우 따로 조건문을 쓰지 않고도 ifPresent를 사용하여 조건문처럼 사용 할 수 있다.
        // Optional 안에 member가 있다고 생각 member를 Optional 감싸고 있다고 생각하면 편함 그리기에 Optional안에 기능을 사용가능
        // 아래를 해석하자면 널이 아니라 result(m 멤버에)에 값이 있으면
        /*
        Optional<Member> result = memberRepository.findByName(member.getName());

        result.ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
         */

        // 위를 아래처럼 줄여서 쓸 수 있다.
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /*
    *  전체 회원 조회
    * */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
