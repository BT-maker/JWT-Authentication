// Spring Security için kullanıcı detay servisi implementasyonu
package com.bahattintok.JWT_Authentication.security.services;

import com.bahattintok.JWT_Authentication.model.User;
import com.bahattintok.JWT_Authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service // Spring servis katmanı
public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository; // Kullanıcı repository
    
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username)); // Kullanıcı bulunamazsa hata fırlatır
        
        return UserDetailsImpl.build(user); // UserDetailsImpl nesnesi döner
    }
}
