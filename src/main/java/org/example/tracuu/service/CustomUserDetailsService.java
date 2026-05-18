package org.example.tracuu.service;

import org.example.tracuu.model.ThiSinh;
import org.example.tracuu.model.User;
import org.example.tracuu.repository.UserRepository;
import org.example.tracuu.repository.ThiSinhRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ThiSinhRepository thiSinhRepository;

    public CustomUserDetailsService(UserRepository userRepository,
                                    ThiSinhRepository thiSinhRepository) {
        this.userRepository = userRepository;
        this.thiSinhRepository = thiSinhRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // System.out.println(">>> Dang kiem tra dang nhap cho user: [" + username + "]");
        // System.out.println(">>> Username length: " + (username != null ? username.length() : "null"));
        // System.out.println(">>> Username isEmpty: " + (username != null ? username.isEmpty() : "null"));
        
        // 1. Kiểm tra trong bảng User (Admin / System Users)
        // var userOpt = userRepository.findByUsername(username);
        // if (userOpt.isPresent()) {
        //     User user = userOpt.get();
        //     System.out.println(">>> Tim thay Admin/User: " + username);
        //     return new org.springframework.security.core.userdetails.User(
        //             user.getUsername(),
        //             user.getPassword(),
        //             Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
        // }

        // 2. Kiểm tra trong bảng ThiSinh (xt_thisinhxettuyen25)
        var thiSinhOpt = thiSinhRepository.findByCccd(username);
        if (thiSinhOpt.isPresent()) {
            ThiSinh thiSinh = thiSinhOpt.get();
            System.out.println(">>> Tim thay Thi sinh: " + username);
            return new org.springframework.security.core.userdetails.User(
                    thiSinh.getCccd(),
                    thiSinh.getPassword(), // Dạng {noop}DDMMYYYY
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        }

        System.out.println(">>> KHONG tim thay user: [" + username + "]");
        throw new UsernameNotFoundException("Khong tim thay tai khoan hoac thi sinh voi CCCD: " + username);
    }
}

