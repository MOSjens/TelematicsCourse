package Message;


import java.nio.charset.StandardCharsets;

import MessageType.PregamMessageType;

public class SignOnMessage extends Message {
    private String playerAlias;

    public SignOnMessage(String playerAlias) {
        super(PregamMessageType.SIGN_ON, playerAlias.getBytes(StandardCharsets.UTF_8));
        this.playerAlias = playerAlias;
    }


}
