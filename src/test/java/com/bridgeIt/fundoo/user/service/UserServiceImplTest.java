package com.bridgeIt.fundoo.user.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bridgeIt.fundoo.user.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
class UserServiceImplTest {

	@InjectMocks
	UserService userService;

	@Mock
	UserRepository userRepository;
	
	private static final long ID=1;
	
	@Test
	public void registration() {
		//userService.registration(userDto)
	}
}
