package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateNewUserWithOneRole() {
		Role roleAdmin=entityManager.find(Role.class,1);
		User userDattaa = new User("dattaa@gmail.com","Dattaa@123","Dattaa","sai");
		userDattaa.addRole(roleAdmin);
		
		User savedUser=repo.save(userDattaa);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	@Test
	public void testCreateNewUserWithTwoRole() {
		User userSatish = new User("satish@gmail.com","Satish@123","Satish","mulaga");
		Role roleEditor =new Role(3);
		Role roleAssistant =new Role(5);
		
		
		userSatish.addRole(roleEditor);
		userSatish.addRole(roleAssistant);
		
		User savedUser = repo.save(userSatish);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
		
	}

	@Test
	public void testListAllUsers() {
		Iterable<User>listUsers=repo.findAll();
		listUsers.forEach(user -> System.out.println(user));
	
	}
	@Test
	public void testGetUserById() {
		User userDattaa=repo.findById(1).get();
		 System.out.println(userDattaa);
		assertThat(userDattaa).isNotNull();
	}
	@Test
	public void testUpdateUserDetails() {
		User userDattaa=repo.findById(1).get();
		userDattaa.setEnabled(true);
		userDattaa.setEmail("Dattaajavaprogrammer@gmail.com");
		
		repo.save(userDattaa);
	}
	
	@Test
	public void testUpdateUserRoles() {
		User userSatish=repo.findById(2).get();
		Role roleEditor =new Role(3);
		Role roleSalesperson =new Role(2);


		userSatish.getRoles().remove(roleEditor);
		userSatish.addRole(roleSalesperson);
		repo.save(userSatish);
	}
	@Test
	public void testDeleteUser() {
		Integer userId=2;
		repo.deleteById(userId);
	}
	@Test
	public void testGetUserByEmail() {
		String email = "chandu@gmail.com";
		User user = repo.getUserByEmail(email);
		
		assertThat(user).isNotNull();
	}
	@Test
	public void testCountById() {
		Integer id=1;
	// countById = repo.countById(id);
		
	//(countById).isNotNull().isGreaterThan(0);
	}
	

	@Test
	public void testDisabledUser() {
		Integer id=1;
		//repo.updateEnabledStatus(id,false);
	
	}
}
