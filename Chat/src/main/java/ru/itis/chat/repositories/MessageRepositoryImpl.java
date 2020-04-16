package ru.itis.chat.repositories;

import org.springframework.stereotype.Component;
import ru.itis.chat.models.Message;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class MessageRepositoryImpl implements MessageRepository {

    //language=SQL
    private static final String SQL_SELECT_BY_RECEIVER =
            "select m from messages m where receiver = ?1 ";

    //language=SQL
    private static final String SQL_SELECT_BY_SENDER =
            "select m from messages m where sender = ?1 ";

    //language=SQL
    private static final String SQL_SELECT_BY_SENDER_AND_RECEIVER =
            "select m from messages m where sender = ?1 and receiver = ?2 ";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<Message> getAllByReceiver_Login(String receiver) {
        return entityManager.createQuery(SQL_SELECT_BY_RECEIVER)
                .setParameter(1,receiver)
                .getResultList();
    }

    @Override
    @Transactional
    public List<Message> getAllBySender_Login(String sender) {
        return entityManager.createQuery(SQL_SELECT_BY_SENDER)
                .setParameter(1,sender)
                .getResultList();
    }

    @Override
    @Transactional
    public List<Message> getAllBySender_LoginAndReceiver_Login(String senderEmail, String receiverEmail) {
        return entityManager.createQuery(SQL_SELECT_BY_SENDER_AND_RECEIVER)
                .setParameter(1,senderEmail)
                .setParameter(2, receiverEmail)
                .getResultList();
    }

    @Override
    @Transactional
    public void save(Message message) {
        entityManager.persist(message);
    }
}
