package web.dao;

import org.springframework.stereotype.Repository;
import web.model.Role;

import java.util.List;

public interface RoleDao {
    Role getRoleByName(String name);
    List<Role> getListRole();
}
