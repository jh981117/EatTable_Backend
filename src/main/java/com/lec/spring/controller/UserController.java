package com.lec.spring.controller;



import com.lec.spring.domain.User;
import com.lec.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {



        private final UserService userService;



        @GetMapping("/list")
        public ResponseEntity<?> list () {
                return new ResponseEntity<>(userService.list(), HttpStatus.OK);
        }

        @PostMapping("/signup")
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


        @PostMapping("/login")
        public ResponseEntity<?> login(@RequestBody User user) {
                // LoginRequest 클래스는 클라이언트에서 전송한 로그인 정보를 담은 DTO여야 합니다.
                // 아래와 같이 LoginRequest 클래스를 정의하셔야 합니다.

                String id = user.getUsername();
//                String password = user.getPassword();

                // 로그인 비즈니스 로직은 UserService에서 수행합니다.
                User user1 = userService.login(id);

                if (user1 != null) {
                        // 로그인 성공 시 사용자 정보를 반환합니다.
                        return ResponseEntity.ok(user1);
                } else {
                        // 로그인 실패 시 적절한 응답을 반환합니다.
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
                }
        }

        @GetMapping("/user")
        @PreAuthorize("hasAnyRole('MEMBER','ADMIN')")
        public ResponseEntity<User> getMyUserInfo() {
                return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
        }
        @GetMapping("/{username}")
        @PreAuthorize("hasAnyRole('ADMIN')")
        public ResponseEntity<User> getUserInfo(@PathVariable String username) {
                return ResponseEntity.ok(userService.getUserWithAuthorities(username).get());
        }
}
