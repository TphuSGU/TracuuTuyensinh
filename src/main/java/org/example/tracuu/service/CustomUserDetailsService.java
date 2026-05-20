package org.example.tracuu.service;

import org.example.tracuu.model.ThiSinh;
import org.example.tracuu.repository.ThiSinhRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final ThiSinhRepository thiSinhRepository;

    public CustomUserDetailsService(ThiSinhRepository thiSinhRepository) {
        this.thiSinhRepository = thiSinhRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // System.out.println(">>> Dang kiem tra dang nhap cho user: [" + username + "]");
        // System.out.println(">>> Username length: " + (username != null ? username.length() : "null"));
        // System.out.println(">>> Username isEmpty: " + (username != null ? username.isEmpty() : "null"));
        
        // Đăng nhập bằng CCCD của thí sinh (bảng xt_thisinhxettuyen25)
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
