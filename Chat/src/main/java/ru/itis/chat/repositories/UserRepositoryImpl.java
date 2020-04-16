package ru.itis.chat.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import ru.itis.chat.models.CookieValue;
import ru.itis.chat.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
public class UserRepositoryImpl implements UserRepository {

    //language=SQL
    private static final String SQL_SELECT_BY_LOGIN =
            "select u from itis_user u where login = ?1 ";

    //language=SQL
    private static final String SQL_SELECT_ALL =
            "select u from itis_user u ";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Optional<User> findByLogin(String login) {
        try {
            User user =(User) entityManager.createQuery(SQL_SELECT_BY_LOGIN)
                    .setParameter(1,login)
                    .getResultList()
                    .get(0);
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public List<User> getAll() {
        return entityManager.createQuery(SQL_SELECT_ALL).getResultList();
    }

    @Override
    @Transactional
    public void save(User user) {
        entityManager.persist(user);
    }
}
