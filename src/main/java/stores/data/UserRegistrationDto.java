package stores.data;

import lombok.Data;

@Data
public class UserRegistrationDto {

   private String userName;
   private String fullName;
   private String street;
   private String city;
   private String zip;
   private String phoneNumber;
   private String password;

}