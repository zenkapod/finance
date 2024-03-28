package stores.security;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import stores.data.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

   private UserRepository userRepository;
   private BCryptPasswordEncoder passwordEncoder;

   public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
      super();
      this.userRepository = userRepository;
      this.passwordEncoder = passwordEncoder;
   }

   @Override
   public User save(UserRegistrationDto registrationDto) {
      var user = new User(
              registrationDto.getUserName(),
                 registrationDto.getFullName(),
                  registrationDto.getStreet(),
                   registrationDto.getCity(),
                   registrationDto.getZip(),
                   registrationDto.getPhoneNumber(),
                   passwordEncoder.encode(registrationDto
                          .getPassword()),
                   Arrays.asList(new Role("ROLE_USER")));

      return userRepository.save(user);
   }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        var user = userRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException
                    ("Invalid username or password.");
        }

        Collection<Role> roles = user.getRoles();
        roles.add(new Role("ROLE_USER"));

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority>
          mapRolesToAuthorities(Collection<Role> roles) {
      return roles.stream()
            .map(role -> new SimpleGrantedAuthority
                  (role.getName()))
            .collect(Collectors.toList());
   }
   @Override
   public List<User> getAll() {
      return userRepository.findAll();
   }
}
