package usermanagement;

public class EoAdmin extends Voter {
  private static int id = 1;
  private String adminId;
  // private int idX = id;

  public EoAdmin() {
    
  }

  public EoAdmin(String adminId, User user) {
    super(user);
    this.adminId = "ADMIN0" + adminId;
  }

  public EoAdmin(String adminId, String name, byte age, String email, String password) {
    super(name, age, email, password);
    this.adminId = adminId;

  }

  public static int getId() {
    return id;
  }

  public static void setId(int id) {
    EoAdmin.id = id;
  }

  public String getAdminId() {
    return adminId;
  }

  public void setAdminId(String adminId) {
    this.adminId = adminId;
  }

}
