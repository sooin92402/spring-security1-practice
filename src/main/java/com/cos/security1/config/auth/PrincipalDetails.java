package com.cos.security1.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.security1.model.User;

//시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
//로그인 진행이 완료가 되면 시큐리티 session을 만들어준다. (Security ContextHolder)
//오브젝트 타입 => Authentication 타입 객체
//Authentication 안에 User정보가 있어야 된다.
//User 오브젝트타입 => UserDetail 타입 객체

//Security Session => Authentication => UserDetails (Principal Details)

public class PrincipalDetails implements UserDetails{
	
	private User user; //콤포지션 
	
	public PrincipalDetails(User user) {
		this.user = user;
	}

	//해당 User의 권한을 리턴
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect = new ArrayList<>(); 
		
			collect.add(new GrantedAuthority() {
					  
					  @Override 
					  public String getAuthority() { 
						  return user.getRole(); 
						  } 
					  }); 
			
		return collect; 
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() { //비밀번호 기한체크
		return true;
	}

	@Override
	public boolean isEnabled() {//계정 활성화 (휴면계정 관리) 
		//사이트 1년동안 회원이 로그인을 안하면 휴면계정으로 하기로함 
		//현재시간 = 로그인시간 => 1년을 초과하면 return false로 설정
		return true;
	}
	
}
