package com.example.demo.serviceImpl;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.dao.IUserDao;
import com.example.demo.dto.respone.ResponseMessage;
import com.example.demo.entity.User;
import com.example.demo.security.userprical.UserPrinciple;
import com.example.demo.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    IUserDao userDao;

    @Autowired
    HttpServletRequest request;

    private static JavaMailSender mailSender;

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        // TODO Auto-generated method stub
        return userDao.findByUserName(userName);
    }

    @Override
    public Boolean existsByUserName(String userName) {
        // TODO Auto-generated method stub
        return userDao.existsByEmail(userName);
    }

    @Override
    public Boolean existByEmail(String email) {
        // TODO Auto-generated method stub
        return userDao.existsByEmail(email);
    }

    @Override
    public User save(User user) {
        // TODO Auto-generated method stub
        return userDao.save(user);
    }

    @Override
    public Optional<User> getAuthenticatedUser(String userName) {
        return userDao.findByUserName(userName);
    }

    @Transactional
    @Override
    public void follow(String userName) throws ResponseMessage {
        String user = request.getUserPrincipal().getName();
        Optional<User> authUser = getAuthenticatedUser(user);

//		boolean isSameId = false;
//		targetUser.get().getFollowers().forEach(e->{
//			if(e.getId() == authUser.get().getId()) {
//				isSameId = true;
//				return;
//			}
//		});

//		if (!targetUser.get().getFollowerUsers().contains) {
//			targetUser.get().getFollowers().add(authUser.get());
//			targetUser.get().setNumbOfFollowers(targetUser.get().getFollowers().size());

//			List<User> listFollowings = new ArrayList<User>();
//			listFollowings.add(targetUser.get());
//			authUser.get().setFollowing(listFollowings);
//			authUser.get().setNumbOfFollowing(authUser.get().getFollowing().size());
//			System.out.println("targetUser.get() "+targetUser.get());
//			targetUser.get().getFollowers().forEach(e->System.out.println(e.toString()));
        // userDao.save(targetUser.get());
//		} else {
//			unfollow(userName);
//		}
        Optional<User> targetUser = findByUserName(userName);
        if (!targetUser.get().getFollowerUsers().contains(authUser.get())) {

            authUser.get().getFollowingUsers().add(targetUser.get());
            authUser.get().setNumbOfFollowing(authUser.get().getFollowingUsers().size());

            targetUser.get().getFollowerUsers().add(authUser.get());
            targetUser.get().setNumbOfFollowers(targetUser.get().getFollowerUsers().size());

            System.out.println(targetUser.get().getNumbOfFollowers());
            userDao.save(targetUser.get());
            userDao.save(authUser.get());

        } else {
            unfollow(userName);
        }

    }

    @Override
    public void unfollow(String userName) throws ResponseMessage {
        String user = request.getUserPrincipal().getName();
        Optional<User> authUser = getAuthenticatedUser(user);
        if (!authUser.get().getUserName().equals(userName)) {
            Optional<User> targetUser = findByUserName(userName);
            authUser.get().getFollowingUsers().remove(targetUser.get());
            authUser.get().setNumbOfFollowing(authUser.get().getFollowingUsers().size());

            targetUser.get().getFollowerUsers().remove(authUser.get());
            targetUser.get().setNumbOfFollowers(targetUser.get().getFollowerUsers().size());

            userDao.save(targetUser.get());
            userDao.save(authUser.get());

        } else {
            throw new ResponseMessage("You only unfollow with another");
        }
    }

    public UserDetails loadUserByUsername(String username) {

        Optional<User> user = userDao.findByUserName(username);

        if (user.isEmpty()) {

            throw new UsernameNotFoundException(username);

        }

        if (this.checkLogin(user)) {

            return UserPrinciple.build(user.get());

        }

        boolean enable = false;

        boolean accountNonExpired = false;

        boolean credentialsNonExpired = false;

        boolean accountNonLocked = false;

        return new org.springframework.security.core.userdetails.User(user.get().getUserName(),

                user.get().getPassword(), enable, accountNonExpired, credentialsNonExpired,

                accountNonLocked, null);

    }

    @Override

    public boolean checkLogin(Optional<User> user) {

        Iterable<User> users = this.findAll();

        boolean isCorrectUser = false;

        for (User currentUser : users) {

            if (currentUser.getUserName().equals(user.get().getUserName())

                    && user.get().getPassword().equals(currentUser.getPassword()) &&

                    currentUser.isEnabled()) {

                isCorrectUser = true;

            }

        }

        return isCorrectUser;

    }

    @Override
    public boolean isCorrectConfirmPassword(User user) {
        boolean isCorrectConfirmPassword = false;

        if (user.getPassword().equals(user.getConfirmPassword())) {

            isCorrectConfirmPassword = true;

        }

        return isCorrectConfirmPassword;
    }

    @Override
    public Optional<User> findByEmail(String email) throws ResponseMessage {
        return userDao.findByEmail(email);
    }

//	@Override
//	public User getUserByEmail(String email) throws ResponseMessage {
//		return userDao.findByEmail(email).orElseThrow(ResponseMessage::new);
//	}
//
//	@Override
//	public void forgotPassword(String password) throws ResponseMessage {
//		User targetUser = getUserByEmail(password);
//		UserPrinciple userPrinciple = new UserPrinciple(targetUser);
//
//	}
//
//	@Override
//	public String validatePasswordResetToken(long id, String token, String email) throws ResponseMessage {
//
//		boolean isCorrectPassword = userDao.existsByEmail(email);
//		Optional<PasswordResetToken> passToken = userDao.findByToken(token);
//		if (isCorrectPassword == true) {
//			if (passToken.isEmpty()) {
//				throw new ResponseMessage("invalidToken");
//			}
//			Calendar cal = Calendar.getInstance();
//			if ((passToken.get().getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
//				throw new ResponseMessage("expired");
//			}
//			final User user = passToken.get().getUser();
//			final Authentication auth = new UsernamePasswordAuthenticationToken(user, null,
//					Arrays.asList(new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
//			SecurityContextHolder.getContext().setAuthentication(auth);
//
//			return null;
//		}
//		return null;
//	}
//
//	@Override
//	public void createPasswordResetTokenForUser(User user, String token) {
//	    PasswordResetToken myToken = new PasswordResetToken(token, user);
//	    passwordTokenDao.save(myToken);
//	}
//
//	@Override
//	public ResponseMessage resetPassword(HttpServletRequest request, String email) throws ResponseMessage {
//		Optional<User> targetUser = userDao.findByEmail(email);
//		if(targetUser.isEmpty()) {
//			throw new ResponseMessage();
//		}
//		 String token = UUID.randomUUID().toString();
//		 createPasswordResetTokenForUser(targetUser.get(), token);
//		 mailSender.send()
//		return null;
//	}

}
