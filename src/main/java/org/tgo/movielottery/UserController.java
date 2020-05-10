package org.tgo.movielottery;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@GetMapping("/api/user")
	public ResponseEntity<?> getUser(@AuthenticationPrincipal UserDetails user) {
		if (user == null) {
			return new ResponseEntity<>("", HttpStatus.OK);
		} else {
			return ResponseEntity.ok().body(user);
		}
	}

}
