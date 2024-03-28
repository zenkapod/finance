package stores.data;


import jakarta.persistence.*;

@Entity
@Table(name = "role")
public class Role {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;
   private String name;

   public Role() {

   }

   public Role(String name) {
      super();
      this.name = name;
   }
   public String getName() {
      return name;
   }

}