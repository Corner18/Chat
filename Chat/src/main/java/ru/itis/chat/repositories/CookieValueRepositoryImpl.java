package ru.itis.chat.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import ru.itis.chat.models.CookieValue;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;

@Component
public class CookieValueRepositoryImpl implements CookieValueRepository {

    //language=SQL
    private static final String SQL_SELECT_BY_COOKIE_VALUE =
            "select c from cookie_value c where valuee = ?1 ";

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public Optional<CookieValue> findByValue(String value) {
        try {
            CookieValue cookieValue =(CookieValue) entityManager.createQuery(SQL_SELECT_BY_COOKIE_VALUE)
                    .setParameter(1,value)
                    .getResultList()
                    .get(0);
            return Optional.of(cookieValue);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public void save(CookieValue cookieValue) {
        entityManager.persist(cookieValue);
    }
}
