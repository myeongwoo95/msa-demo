package com.example.userservice.domain.users;

import com.example.userservice.common.BaseTimeEntity;
import com.example.userservice.type.UsersRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Set;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
@ToString(exclude = "")
@Builder
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "users")
public class Users extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private String encryptedPwd;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Set<UsersRole> roles;
}
