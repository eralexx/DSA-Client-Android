package app.movie.tutorial.com.model;

import java.util.ArrayList;
import java.util.List;

public class Chat {

    private List<String> Messages = new ArrayList<>(20);


    public void AddMessage(String NewMessage) {
        try {
            if (NewMessage != null)
                if (Messages.size()>22){
                    ShiftRight( NewMessage );
                }
                else {
                    Messages.add(NewMessage);
                }
        } catch (Exception ex) {
            throw ex;
        }
    }
    public List<String> getMessages() {
        return Messages;
    }

    public void setMessages(List<String> messages) {
        Messages = messages;
    }

    private void ShiftRight(String newMessage) {
        try {
            for (int i = 19; i > 0; i--) {
                if (Messages.get(i-1) !=null) {
                    Messages.set(i, Messages.get(i - 1));
                }
            }
            Messages.set(0, newMessage);
        }
        catch(Exception ex){
            throw ex;
        }
    }
    /*@Override
    public String toString() {
        return "Chat [Messages=" + Messages+"]";
    }*/

}
