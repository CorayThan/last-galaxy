package com.corinthgames.lastgalaxy.enums;

import com.nathanwestlake.dartgenerator.annotations.DartInclude;
import org.springframework.security.core.GrantedAuthority;

@DartInclude
public enum Role implements GrantedAuthority {
	USER,ADMIN;

	@Override
	public String getAuthority() {
		return this.toString();
	}

}
