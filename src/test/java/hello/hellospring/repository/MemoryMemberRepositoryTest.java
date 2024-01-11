package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    // 테스트는 순서와 상관없이 의존관계 없이 해야하기 때문에 저장소나 이런걸 지워줘야함
    // @AfterEach => 테스트 한번 하고 동작
    @AfterEach
    public  void afterEach(){
        repository.clearStore();
    }

    @Test
    public void save(){
        Member member = new Member();
        member.setName("김인구");

        repository.save(member);

        // get()으로 꺼내지 않고 그냥 Member에 담으려고 하면 오류가 난다. 반환타입이 같지 않기때문
        //Member test0 = repository.findById(member.getId());

        // 기본적으로 findById는 반환타입이 member이 아닌 Optional[Member] 타입
        // 그러므로 member랑 비교하면 같을 수 없다.
        Optional<Member> test = repository.findById(member.getId());
        // 이렇게 받은경우 멤버랑 비교 하려면 후 가공을 해줘야한다.
        Member test2 = test.get();

        // get(); 으로 꺼내면 Optional이 없는 Member 타입으로 받아진다.
        Member result = repository.findById(member.getId()).get();

        assertThat(member).isEqualTo(result); // (기준)멤버랑 리절트랑 같냐
    }

    @Test
    public void findByName(){

        // 멤버 객체 생성 후 set으로 네임 넣어줌
        Member member1 = new Member();
        member1.setName("spring1");

        // 위에서 만든 member1을 맨위에서 선언한 레파지스토리에 넣음
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();

        assertThat(member1).isEqualTo(result); // (기준)멤버랑 리절트랑 같냐
    }

    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }
}
