package web.dao;

import org.springframework.stereotype.Repository;
// в UserDao не нужна модель Role? незадействованный import web.model.Role;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Override
    @Transactional
    public User getUser(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void editUser(User user) {
         entityManager.merge(user);
    }

    @Override
    public void deleteUser(int id) {
        entityManager.createQuery("delete User where id = "+ id).executeUpdate();
    }

    @Override
    public User showUserByUsername(String email) {
        return entityManager
                .createQuery("select u from User u where u.email =?1", User.class)
                .setParameter(1, email)
                .getSingleResult();
    }
}
