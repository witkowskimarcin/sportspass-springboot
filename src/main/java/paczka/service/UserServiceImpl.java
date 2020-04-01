package paczka.service;

import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.lang.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import paczka.dto.*;
import paczka.entity.*;
import paczka.exception.UserEmailAlreadyExistsException;
import paczka.exception.UserNotFoundException;
import paczka.repository.MAClassRepository;
import paczka.repository.MAOfferRepository;
import paczka.repository.RoleRepository;
import paczka.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private RoleRepository roleRespository;
	private MAOfferRepository maOfferRepository;
	private MAClassRepository maClassRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private JdbcTemplate jdbc;
	private ModelMapper modelMapper;
	private EmailSender emailSender;
	private TemplateEngine templateEngine;

	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRespository, MAOfferRepository maOfferRepository, MAClassRepository maClassRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JdbcTemplate jdbc, ModelMapper modelMapper, EmailSender emailSender, TemplateEngine templateEngine)
	{
		this.userRepository = userRepository;
		this.roleRespository = roleRespository;
		this.maOfferRepository = maOfferRepository;
		this.maClassRepository = maClassRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.jdbc = jdbc;
		this.modelMapper = modelMapper;
		this.emailSender = emailSender;
		this.templateEngine = templateEngine;
	}

	@Override
	public void saveClient(User user) {
		if(userExists(user)) throw new UserEmailAlreadyExistsException(user.getEmail());
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(1);
		Role userRole = roleRespository.findByRole("CLIENT");
		user.setRoles(new ArrayList<Role>(Arrays.asList(userRole)));
		userRepository.save(user);
	}
	
	@Override
	public void saveCoach(User user) {
		if(userExists(user)) throw new UserEmailAlreadyExistsException(user.getEmail());
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(1);
		Role userRole = roleRespository.findByRole("COACH");
		user.setRoles(new ArrayList<Role>(Arrays.asList(userRole)));
		userRepository.save(user);
	}

	@Override
	public void saveOwner(User user) {
		if(userExists(user)) throw new UserEmailAlreadyExistsException(user.getEmail());
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(1);
		Role userRole = roleRespository.findByRole("OWNER");
		user.setRoles(new ArrayList<Role>(Arrays.asList(userRole)));
		userRepository.save(user);
	}

	@Override
	public void saveAdmin(User user) {
		if(userExists(user)) throw new UserEmailAlreadyExistsException(user.getEmail());
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(1);
		Role userRole = roleRespository.findByRole("ADMIN");
		user.setRoles(new ArrayList<Role>(Arrays.asList(userRole)));
		userRepository.save(user);
	}

	@Override
	public Boolean userExists(User user){
		return userRepository.findByEmail(user.getEmail())!=null;
	}

	@Override
	public User checkIfUserExists(UserDto user)
	{
		User u = userRepository.findByEmail(user.getEmail());
		if(u==null){
			throw new UserNotFoundException(user.getEmail());
		}
		return u;
	}

	@Override
	public void changePassword(UserDto user, String newPassword)
	{
		User u = checkIfUserExists(user);
		u.setPassword(ecryptPassword(newPassword));
		userRepository.save(u);
	}

	@Override
	public void forgotPassword(UserDto user)
	{
		String newPassword = generatePassword();
		changePassword(user, newPassword);

		Context context = new Context();
		context.setVariable("header", "Forgot password");
		context.setVariable("title", "New password is");
		context.setVariable("description", newPassword);
		String body = templateEngine.process("emailtemplate", context);
		emailSender.sendEmail(user.getEmail(), "Forgot password", body);
	}

	@Override
	public void delete(User user) {

		jdbc.execute("DELETE FROM USER_ROLE WHERE USER_ID ="+user.getId());
		jdbc.execute("DELETE FROM USER_MA_PASSES WHERE USER_ID ="+user.getId());
		jdbc.execute("DELETE FROM USER_GYM_PASSES WHERE USER_ID ="+user.getId());
		jdbc.execute("DELETE FROM MAOFFER_CLIENTS WHERE CLIENTS_ID ="+user.getId());
		userRepository.delete(user);
	}

	@Override
	public void deleteRoleCoach(User user) {

        List<MAClass> all = maClassRepository.findAll();

        for(MAClass mc : all){
            if(mc.getCoach()==user){
                maClassRepository.delete(mc);
            }
        }

        Role userRole = roleRespository.findByRole("COACH");
		user.getRoles().remove(userRole);
		userRepository.save(user);
	}

	@Override
	public void deleteRoleAdmin(User user) {

		Role userRole = roleRespository.findByRole("ADMIN");
		user.getRoles().remove(userRole);
		userRepository.save(user);
	}

	@Override
	public String generatePassword() {
		String newPass=  RandomStringUtils.randomAscii(7);
		return newPass;
	}

	@Override
	public String ecryptPassword(String str){
		return bCryptPasswordEncoder.encode(str);
	}

	@Override
	public UserDto convertToDto(User user)
	{
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public User convertToEntity(UserDto user)
	{
		return modelMapper.map(user, User.class);
	}

	@Override
	public Authentication getAuthentication()
	{
		return SecurityContextHolder.getContext().getAuthentication();
	}

	@Override
	public String getCurrentUserName()
	{
		return getAuthentication().getName();
	}

	@Override
	public UserDto getCurrentUserDto()
	{
		User user = userRepository.findByEmail(getCurrentUserName());
		if(user!=null)
		{
			return modelMapper.map(user, UserDto.class);
		} else return null;
	}

	@Override
	public User getCurrentUser()
	{
		User user = userRepository.findByEmail(getCurrentUserName());
		if(user!=null)
		{
			return user;
		} else return null;
	}

	@Override
	public Boolean isAuthenticated()
	{
		return getAuthentication().isAuthenticated();
	}

	@Override
	public void editUser(UserDto user)
	{
		userRepository.save(convertToEntity(user));
	}

	@Override
	public void save(User user)
	{
		userRepository.save(user);
	}

	@Override
	public Boolean checkIfUserHasGymPass(GymOfferDetailDto god, UserDto user)
	{
		final boolean[] result = {false};
		user.getGymPasses().forEach((x)->{
			if(x.getId()==god.getId()) result[0] = true;
		});
		return result[0];
	}

	@Override
	public UserDto findById(Long l)
	{
		return convertToDto(userRepository.findById(l).orElseThrow(()->new RuntimeException("User id: "+l+" does not exist")));
	}

	@Override
	public UserDto findByEmail(String username)
	{
		return convertToDto(userRepository.findByEmail(username));
	}

	@Override
	public void addRoleToUser(UserDto user, RoleDto role)
	{
		Role userRole = roleRespository.findByRole(role.getRole());
		User u = userRepository.findByEmail(user.getEmail());
		u.setRoles(new ArrayList<Role>(Arrays.asList(userRole)));
		userRepository.save(u);
	}

	@Override
	public void disActiveAccount(UserDto user)
	{
		User u = convertToEntity(user);
		u.setActive(0);
		userRepository.save(u);
	}

    @Override
    public List<UserDto> findUsersByRole(RoleDto roleAdmin)
    {
		List<UserDto> users = findAll();
		List<UserDto> admins = new ArrayList<>();
    	for(UserDto u : users){
            if(u.getRoles().contains(roleAdmin)){
                admins.add(u);
            }
        }
    	return admins;
    }

	@Override
	public List<UserDto> findAll()
	{
		return userRepository
				.findAll()
				.stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());
	}

	@Override
	public void addRoleToUser(Long id, Integer roleId)
	{
		User user = userRepository.findById(id).orElseThrow(()->new RuntimeException("User id:"+id+" does not exist"));
		Role role = roleRespository.findById(roleId).orElseThrow(()->new RuntimeException("Role id:"+roleId+" does not exist"));

		user.getRoles().add(role);
		userRepository.save(user);
	}

	@Override
	public void deleteRoleFromUser(Long id, Integer roleId)
	{
		User user = userRepository.findById(id).orElseThrow(()->new RuntimeException("User id:"+id+" does not exist"));
		Role role = roleRespository.findById(roleId).orElseThrow(()->new RuntimeException("Role id:"+roleId+" does not exist"));

		user.getRoles().removeIf((e)->e.getRole()==role.getRole());
		userRepository.save(user);
	}
}
