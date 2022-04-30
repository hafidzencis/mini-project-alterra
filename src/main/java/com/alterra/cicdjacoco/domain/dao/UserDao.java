package com.alterra.cicdjacoco.domain.dao;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class UserDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_user")
    private String nameUser;

    @Column(name = "telephone_number")
    private String telephoneNumber;

    @Column(name = "alamat")
    private String alamat;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "is_boolean_admin")
    private Boolean is_boolean_admin;

//    @OneToMany(mappedBy = "user_id")
//    List<ChildDao> childs;

}
