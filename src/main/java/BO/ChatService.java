package BO;

import DB.DAL.ChatDb;
import DB.Entities.ChatMessageEntity;
import ViewModel.ChatMessageViewModel;

import javax.print.attribute.standard.Media;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by waleedhassan on 27/11/16.
 */
@Path("/Chat")
public class ChatService {
    private ChatDb db;

    public ChatService(){
        this.db = new ChatDb();
    }

    @POST
    @Path("/sendMessage")
    @Consumes(MediaType.APPLICATION_JSON)
    public void sendMessage(ChatMessageViewModel chat) {
        db.addMessage(chat);
    }

    @POST
    @Path("/getChatMessagesBySenderAndReceiver")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public List<ChatMessageViewModel> getChatMessagesBySenderAndReceiver(ChatMessageViewModel chat) {
        Collection<ChatMessageEntity> chatMessages = db.findChatMessagesBySenderAndReceiver(chat);
        return chatMessages.stream().map(ModelConverter::convertToChatMessageViewModel).collect(Collectors.toList());
    }
}
