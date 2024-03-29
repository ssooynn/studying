package study.datajpa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import study.datajpa.dto. MemberDto;
import study.datajpa.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	// List<Member> findByUsernameAndGreaterThan(String username, int age);
	//
	// List<Member> findTop3HelloBy();

	@Query("select m from Member  m where m.username = :username and m.age = :age")
	List<Member> findUser(@Param("username") String username, @Param("age") int age);

	// 단순한 검색
	@Query("select m.username from Member m")
	List<String> findUsernameList();

	// DTO 검색
	@Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
	List<MemberDto> findMemberDto();

	// names에 있는 멤버 찾기
	@Query("select m from Member m where m.username in :names")
	List<Member> findByNames(@Param("names") List<String> names);

	List<Member> findListByUsername(String username); //컬렉션

	Member findMemberByUsername(String username); // 멤버버}

	Optional<Member> findOptionalByUsername(String username); // 멤버버
}