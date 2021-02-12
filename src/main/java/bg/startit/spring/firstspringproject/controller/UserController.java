package bg.startit.spring.firstspringproject.controller;

import bg.startit.spring.firstspringproject.dto.ChangePasswordRequest;
import bg.startit.spring.firstspringproject.dto.CreateUserRequest;
import bg.startit.spring.firstspringproject.dto.UserResponse;
import bg.startit.spring.firstspringproject.model.User;
import bg.startit.spring.firstspringproject.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

  private UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public List<UserResponse> listUsers(
      @RequestParam(name = "page", required = false, defaultValue = "0") int pageNumber,
      @RequestParam(name = "size", required = false, defaultValue = "20") int pageSize) {

    Page<User> users = userService.listUsers(PageRequest.of(pageNumber, pageSize));

    return users.stream().map(user -> new UserResponse()
        .setId(user.getId())
        .setUsername(user.getUsername())
        .setLastLoginTime(user.getLastLoginTime())
        .setRegistrationTime(user.getRegistrationTime())
    ).collect(Collectors.toList());

  }

  // POST /api/v1/users
  @PostMapping
  public User registerUser(CreateUserRequest createUserRequest) {
    return userService.register(createUserRequest.getUsername(), createUserRequest.getPassword(),
        createUserRequest.getPassConfirmation());
  }

  // /api/v1/users -> from class @RequestMapping
  // /api/v1/users/{userID} -> current, appended after class mapping
  // {userID} -> variable, that is coming/part from the URL
  // example: POST /api/v1/users/10
  @PostMapping("/{userID}")
  public User updatePassword(@PathVariable("userID") long userID, ChangePasswordRequest request) {
    return userService.changePassword(userID, request.getOldPassword(), request.getNewPassword());
  }

//  @PostMapping("current")
//  public  User updateCurrentPassword(ChangePasswordRequest request, @AuthenticationPrincipal
//      org.springframework.security.core.userdetails.UserDetails user){
////    Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
////    User user = (User) authentication.getPrincipal(); // can be cast to User
//    return userService.changePassword((User)user, request.getOldPassword(),request.getNewPassword());
// }
  // user
  // change password -> POST /api/v1/users/10
  // why 10?
  // change password -> POST /api/v1/users/current ?


}
