package study.datajpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "username", "age"}) // 연관관계필드는 toString 안하는게 좋음
@NamedQuery(name="Member.findByUsername",
query="select m  from Member m where m.username=:username")
public class Member {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="member_id")
	private Long id;
	private String username;
	private int age;

	@ManyToOne
	@JoinColumn(name="team_id")
	private Team team;

	public Member(String username, int age, Team team){
		this.username = username;
		this.age = age;
		this.team = team;
		if(team != null){
			changeTeam(team);
		}
	}

	public Member(String username){
		this.username = username;
	}

	public Member(String member, int i) {
		this.username = member;
		this.age = i;
	}

	public void changeTeam(Team team){
		this.team = team;
		team.getMembers().add(this);
	}
}
