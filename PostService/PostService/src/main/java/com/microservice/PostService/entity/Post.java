package com.microservice.PostService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;
    private Long userId; // Store the user ID instead of the User entity
    @ElementCollection
    private List<Long>likes=new ArrayList<>();
}
//
//import jakarta.persistence.*;
//        import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Setter
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//public class Post {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String description;
//    @Lob
//    @Column(columnDefinition = "LONGBLOB")
//    private byte[]image;
//    @ManyToOne
//    @JoinColumn(name = "uid_fk")
//    private User user;
//    @ManyToMany
//    @JoinTable(name = "likesTab",
//            joinColumns = @JoinColumn(name = "pid_fk"),
//            inverseJoinColumns = @JoinColumn(name = "uid_fk"))
//    private List<User> likes=new ArrayList<>();
//}