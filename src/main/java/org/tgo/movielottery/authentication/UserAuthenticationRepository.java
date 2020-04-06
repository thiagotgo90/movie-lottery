package org.tgo.movielottery.authentication;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthenticationRepository extends CrudRepository<UserAuthentication, Long> {

	UserAuthentication findByUserLogin(String userLogin);

}
