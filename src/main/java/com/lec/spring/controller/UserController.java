package com.lec.spring.controller;



import com.lec.spring.domain.User;
import com.lec.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@CrossOrigin
public class UserController {



        private final UserService userService;



        @GetMapping("/api/user/list")
        public ResponseEntity<?> list () {
                return new ResponseEntity<>(userService.list(), HttpStatus.OK);
        }

        @PostMapping("/api/user/signup")
        public ResponseEntity<?> signup(@RequestBody User user){
                return new ResponseEntity<>(userService.signup(user),HttpStatus.CREATED);
        }

        @GetMapping("/mypage/{id}")
        public ResponseEntity<?> detail(@PathVariable Long id){
                return new ResponseEntity<>(userService.detail(id),HttpStatus.OK);
        }



        @PutMapping("/update")
        public ResponseEntity<?> update(@RequestBody User user){
                return new ResponseEntity<>(userService.update(user),HttpStatus.OK);
        }

        @DeleteMapping("/userout/{id}")
        public ResponseEntity<?> delete(@PathVariable Long id){
                return new ResponseEntity<>(userService.delete(id),HttpStatus.OK);
        }



}
