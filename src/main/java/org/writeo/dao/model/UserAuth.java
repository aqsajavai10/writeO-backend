package org.writeo.dao.model;

import org.writeo.utils.CommonConstants.CommonConstants;
import lombok.*;
import org.springframework.lang.Nullable;

import jakarta.persistence.*;
import java.util.Date;
import lombok.Builder;
import lombok.Data;
import jakarta.persistence.PrePersist;
import java.util.UUID;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = CommonConstants.userAuthTable)
public class UserAuth {

    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Nullable
    @Column(name = "last_login")
    @Temporal(TemporalType.DATE)
    private Date lastLogin;

    @Nullable
    @Column(name = "last_login_device")
    private String lastLoginDevice;

    @Transient
    private String key;

    @PrePersist
    public void generateUserId() {
        if (this.userId == null || this.userId.isEmpty()) {
            this.userId = UUID.randomUUID().toString();  // Generate a UUID if userId is not already set
        }
    }

    @PostLoad
    @PostUpdate
    public void populateKey() {
        this.key = CommonConstants.userAuthTable + "_" + this.userId;
    }
}
