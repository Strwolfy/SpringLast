package web.dao;

import org.springframework.stereotype.Repository;
import web.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
@Repository
public class RoleDaoImpl implements RoleDao {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public Role getRoleByName(String name) {
        return entityManager.createQuery("select role from  Role role where role.role =: name",Role.class)
                .setParameter("name",name)
                .getSingleResult();
    }

    @Override
    @Transactional
    public List getListRole() {
        return entityManager.createQuery("from Role").getResultList();
    }
}
