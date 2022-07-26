package study.datajpa.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

@SpringBootTest
@Transactional
@Rollback(false)
public class MemberRepositoryTest {

	@PersistenceContext
	EntityManager em;
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	TeamRepository teamRepository;

	@Test
	public void testEntity(){
		Team teamA = new Team("teamA");
		Team teamB = new Team("teamB");
		em.persist(teamA);
		em.persist(teamB);

		Member member1 = new Member("member1", 10, teamA);
		Member member2 = new Member("member2", 10, teamA);
		Member member3 = new Member("member3", 10, teamA);
		Member member4 = new Member("member4", 10, teamA);

		em.persist(member1);
		em.persist(member2);
		em.persist(member3);
		em.persist(member4);

		//초기화
		em.flush();
		em.close();

		//확인
		List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();

		for(Member member: members){
			System.out.println("member = " + member);
			System.out.println("-> member.team = " + member.getTeam());
		}
	}
	@Test
	public void basicCRUD(){
		Member member1 = new Member("member1");
		Member member2 = new Member("member2");
		memberRepository.save(member1);
		memberRepository.save(member2);

		//단건 조회 검증
		Member findMember1 = memberRepository.findById(member1.getId()).get();
		Member findMember2 = memberRepository.findById(member2.getId()).get();
		assertThat(findMember1).isEqualTo(member1);
		assertThat(findMember2).isEqualTo(member2);

		findMember1.setUsername("member!!!!");

		List<Member> all = memberRepository.findAll();
		assertThat(all.size()).isEqualTo(2);

		memberRepository.count();
		long count = memberRepository.count();
		assertThat(count).isEqualTo(2);

		memberRepository.delete(member1);
		memberRepository.delete(member2);

		long deleteCount = memberRepository.count();
		assertThat(deleteCount).isEqualTo(0);

	}

	@Test
	public void testQuery(){
		Member m1 = new Member("AAA", 10);
		Member m2 = new Member("BBB", 20);
		memberRepository.save(m1);
		memberRepository.save(m2);

		List<Member> result = memberRepository.findUser("AAA", 10);
		assertThat(result.get(0)).isEqualTo(m1);

	}

	@Test
	public void testQuery2(){
		Member m1 = new Member("AAA", 10);
		Member m2 = new Member("BBB", 20);
		memberRepository.save(m1);
		memberRepository.save(m2);

		List<String> usernameList = memberRepository.findUsernameList();
		for(String s : usernameList){
			System.out.println("s= " + s);
		}
	}

	@Test
	public void testQuery3(){

		Team team = new Team("teamA");
		teamRepository.save(team);

		Member m1 = new Member("AAA", 10);
		m1.setTeam(team);
		memberRepository.save(m1);

		List<MemberDto> memberDto = memberRepository.findMemberDto();
		for(MemberDto dto : memberDto){
			System.out.println("s= " + dto);
		}
	}

	@Test
	public void findUsernameList(){
		Member m1 = new Member("AAA", 10);
		Member m2 = new Member("BBB", 20);
		memberRepository.save(m1);
		memberRepository.save(m2);

		List<Member> result = memberRepository.findByNames(Arrays.asList("AAA", "BBB"));
		for(Member member : result){
			System.out.println("s= " + member);
		}
	}


	@Test
	public void returnType(){
		Member m1 = new Member("AAA", 10);
		Member m2 = new Member("BBB", 20);
		memberRepository.save(m1);
		memberRepository.save(m2);

		List<Member> aaa = memberRepository.findListByUsername("AAA");
		for(Member member : aaa){
			System.out.println("s= " + member);
		}
		Optional<Member> option = memberRepository.findOptionalByUsername("asdfasdf");
	}
}
