package DB.DAL;

import BO.ModelConverter;
import DB.Entities.ChatMessageEntity;
import ViewModel.ChatMessageViewModel;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.sql.Date;
import java.util.Calendar;
import java.util.Collection;

public class ChatDb {


    private EntityManager entityManager;
    private ChatMessageEntity chatMessage;

    public ChatDb() {

    }

    public void addMessage(ChatMessageViewModel chat) {
        this.chatMessage = ModelConverter.convertToChatMessageEntity(chat);
        this.chatMessage.setSendDate(new Date(Calendar.getInstance().getTime().getTime()));

        entityManager = DbContext.emf.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(this.chatMessage);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Collection<ChatMessageEntity> findChatMessagesBySenderAndReceiver(ChatMessageViewModel chat) {
        this.chatMessage = ModelConverter.convertToChatMessageEntity(chat);

        entityManager = DbContext.emf.createEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ChatMessageEntity> cq = cb.createQuery(ChatMessageEntity.class);
        Root<ChatMessageEntity> c = cq.from(ChatMessageEntity.class);
        Predicate p1 = cb.and(cb.equal(c.get("sender"),this.chatMessage.getSender()),cb.equal(c.get("receiver"),this.chatMessage.getReceiver()));
        Predicate p2 = cb.and(cb.equal(c.get("sender"),this.chatMessage.getReceiver()),cb.equal(c.get("receiver"),this.chatMessage.getSender()));
        cq.where(cb.or(p1,p2));
        Collection<ChatMessageEntity> chatMessages = entityManager.createQuery(cq).getResultList();
        entityManager.close();

        return  chatMessages;
    }
}