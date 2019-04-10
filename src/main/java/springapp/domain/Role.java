package springapp.domain;

import static springapp.domain.Permission.LIST_CLIENTS;
import static springapp.domain.Permission.LIST_PETS;
import static springapp.domain.Permission.GET_CLIENT;
import static springapp.domain.Permission.GET_PET;
import static springapp.domain.Permission.SAVE_CLIENT;
import static springapp.domain.Permission.SAVE_PET;


import java.util.ArrayList;
import java.util.List;


public enum Role {

	SUPER_ADMIN(Permission.values()),
	CUSTOMER(GET_PET, GET_CLIENT, SAVE_CLIENT, SAVE_PET);
	
	
	private final Permission[] permissions;
	
	Role(Permission ... permissions) {
		this.permissions = permissions;
	}
	
	public List<Permission> getPermissions(){
		List<Permission> list = new ArrayList<>();
		for(Permission p: permissions) {
			list.add(p);
		}
		return list;
	}
	
}
