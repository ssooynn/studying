package study.datajpa.repository;

import static org.junit.jupiter.api.Assertions.*;

import  static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import study.datajpa.entity.Member;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {
	@Autowired MemberJpaRepository memberJpaRepository;

	@Test
	public void testMember(){
		Member member = new Member("memberA");
		Member savedMember = memberJpaRepository.save(member);

		Member findMember = memberJpaRepository.find(savedMember.getId());

		assertThat(findMember.getId()).isEqualTo(member.getId());
		assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
	}

	@Test
	public void basicCRUD(){
		Member member1 = new Member("member1");
		Member member2 = new Member("member2");
		memberJpaRepository.save(member1);
		memberJpaRepository.save(member2);

		//단건 조회 검증
		Member findMember1 = memberJpaRepository.findById(member1.getId()).get();
		Member findMember2 = memberJpaRepository.findById(member2.getId()).get();
		assertThat(findMember1).isEqualTo(member1);
		assertThat(findMember2).isEqualTo(member2);

		findMember1.setUsername("member!!!!");

		List<Member> all = memberJpaRepository.findAll();
		assertThat(all.size()).isEqualTo(2);

		memberJpaRepository.count();
		long count = memberJpaRepository.count();
		assertThat(count).isEqualTo(2);

		memberJpaRepository.delete(member1);
		memberJpaRepository.delete(member2);

		long deleteCount = memberJpaRepository.count();
		assertThat(deleteCount).isEqualTo(0);

	}

	@Test
	public void testNamedQuery(){
		Member member1 = new Member("member1", 10);
		Member member2 = new Member("member2", 10);
		memberJpaRepository.save(member1);
		memberJpaRepository.save(member2);

	}
}