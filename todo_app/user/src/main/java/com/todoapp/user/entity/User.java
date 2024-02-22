package com.todoapp.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "users_table")
@Getter
@Setter
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "customer_id")
    private Long id;

    @Column(name= "customer_name")
    private String name;

    private String email;
    private String password;


}
