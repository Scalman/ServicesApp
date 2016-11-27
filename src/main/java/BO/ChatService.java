package BO;

import DB.DAL.ChatDb;
import ViewModel.ChatMessageViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

@Path("/Chat")
public class ChatService {

    private ChatDb db;

    public ChatService(){
        db = new ChatDb();
    }

    @POST
    @Path("/SendMessage")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sendMessage(ChatMessageViewModel chat) {
        db.addMessage(chat);
        return Response.ok().build();
    }

    @POST
    @Path("/ChatMessages")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response getChatMessagesBySenderAndReceiver(ChatMessageViewModel chat) {
        List<ChatMessageViewModel> chatMessages =  db.findChatMessagesBySenderAndReceiver(chat).stream().map(ModelConverter::convertToChatMessageViewModel).collect(Collectors.toList());
        Type type = new TypeToken<List<ChatMessageViewModel>>() {}.getType();
        String json = new Gson().toJson(chatMessages,type);
        return Response.ok().entity(json).build();
    }
}
