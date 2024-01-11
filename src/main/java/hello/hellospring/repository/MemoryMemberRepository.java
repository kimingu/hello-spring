package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;

// 현재 db가 없기 떄문에 가상의 객체 클래스를 만들어 메모리 db형식으로 사용
public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // Optional.ofNullable 값이 null일 수도 있으니 감싸자.
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        // 람다식 스트림
        // Map<Long, Member> store 에 Member를
        // 루프 돌려서 찾는다(filter)
        // equals member(Map에 담긴 Member)랑 파라미터로 들어온 name랑 같은지 확인 같은 경우에만 필터링 된다
        // 같으면 반환 findAny();
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}
