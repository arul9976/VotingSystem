package datamanagement;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import usermanagement.EoAdmin;

public class AdminDataManagement {
  private static final File FILE = new File("admin.json");
  private static final ObjectMapper objectMapper = new ObjectMapper();
  private static final List<EoAdmin> adminData = getAdminDataBase();

  private static List<EoAdmin> getAdminDataBase() {
    try {
      List<EoAdmin> totalAdmin = objectMapper.readValue(FILE,
          objectMapper.getTypeFactory().constructCollectionType(List.class, EoAdmin.class));
      return totalAdmin;
    } catch (IOException e) {
      
      System.out.println(e.toString());
    }

    return null;
  }

  public static void addAdmin(EoAdmin admin) throws IOException {
    adminData.add(admin);
    objectMapper.writeValue(FILE, adminData);
  }

  public static boolean valiDateAdmin(String email, String password) {
    for (EoAdmin admin : adminData) {
      if (email.equals(admin.getEmail()) && password.equals(admin.getPassword())) {
        return true;
      }
    }
    return false;
  }
}
