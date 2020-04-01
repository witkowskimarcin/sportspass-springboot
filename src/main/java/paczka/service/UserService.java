package paczka.service;

import org.springframework.security.core.Authentication;
import paczka.dto.*;
import paczka.entity.User;

import java.util.List;

public interface UserService {

	//User findUserByEmail(String email);
	void saveClient(User user);
	void saveCoach(User user);
	void saveOwner(User user);
	void saveAdmin(User user);
	void delete(User user);
	void deleteRoleCoach(User user);
	void deleteRoleAdmin(User user);
	String generatePassword();
	String ecryptPassword(String str);
	UserDto convertToDto(User user);
	User convertToEntity(UserDto user);
	Boolean userExists(User user);
	User checkIfUserExists(UserDto user);
	void changePassword(UserDto user, String newPassword);
	void forgotPassword(UserDto user);
	Authentication getAuthentication();
	String getCurrentUserName();
	UserDto getCurrentUserDto();
	User getCurrentUser();
	Boolean isAuthenticated();
    void editUser(UserDto user);
	void save(User user);
	Boolean checkIfUserHasGymPass(GymOfferDetailDto god, UserDto userDto);
    UserDto findById(Long l);
    UserDto findByEmail(String username);
	void addRoleToUser(UserDto user, RoleDto role);
	void disActiveAccount(UserDto user);
	List<UserDto> findUsersByRole(RoleDto roleAdmin);
    List<UserDto> findAll();
	void addRoleToUser(Long id, Integer role);
    void deleteRoleFromUser(Long id, Integer role);
}
