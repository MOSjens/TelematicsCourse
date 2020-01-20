package messages;


/**
 * Superclass for all Messages. Contains Header information
 * 
 * @author IG4
 *
 */
public class Message {
	private int version;
	private int group;
	private int type;
	private int length;
	private MessageType messageType;
	
	public Message(){
		this.version = 1;
		this.group = 0;
		this.type = 0;
		this.length = 0;
		this.setMessageType();
	}
	
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		if(version<1) {
			version = 1;
		}
		if(version >128){
			version= 128;
		}
		this.version = version;
	}
	
	public int getGroup() {
		return group;
	}
	public void setGroup(int group) {
		if(group < 0) {
			group = 0;
		}
		if(group > 3){
			group = 3;
		}
		this.group = group;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		if(type < 0) {
			type = 0;
		}
		if(type >128){
			type= 128;
		}
		this.type = type;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}

	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
		this.setGroupAndType();
	}
	public void setGroupAndType(){
		switch(this.messageType) {
		case ANSWER:
			this.setGroup(1);
			this.setType(3);
			break;
		case ANSWER_RESULT:
			this.setGroup(1);
			this.setType(7);
			break;
		case BUZZ:
			this.setGroup(1);
			this.setType(4);
			break;
		case BUZZ_RESULT:
			this.setGroup(1);
			this.setType(5);
			break;
		case CATEGORY_SELECTION:
			this.setGroup(1);
			this.setType(1);
			break;
		case CATEGORY_SELECTOR_ANNOUNCEMENT:
			this.setGroup(1);
			this.setType(0);
			break;
		case GAME_END:
			this.setGroup(2);
			this.setType(0);
			break;
		case GENERAL_TEXT:
			this.setGroup(3);
			this.setType(0);
			break;
		case PLAYER_LIST:
			this.setGroup(3);
			this.setType(1);
			break;
		case PLAYER_READY:
			this.setGroup(0);
			this.setType(2);
			break;
		case QUESTION:
			this.setGroup(1);
			this.setType(2);
			break;
		case SCOREBOARD:
			this.setGroup(1);
			this.setType(6);
			break;
		case SCREW:
			this.setGroup(1);
			this.setType(8);
			break;
		case SCREW_RESULT:
			this.setGroup(1);
			this.setType(9);
			break;
		case SIGN_ON:
			this.setGroup(0);
			this.setType(0);
			break;
		case SIGN_ON_RESPONSE:
			this.setGroup(0);
			this.setType(1);
			break;
		default:
			break;
		
		}
	}
	public void setMessageType () {
		switch(this.group) {
			case 0:{
				switch(this.type) {
					case 0:{
						this.setMessageType(MessageType.SIGN_ON);
						break;
					}
					case 1:{
						this.setMessageType(MessageType.SIGN_ON_RESPONSE);
						break;
					}
					case 2:{
						this.setMessageType(MessageType.PLAYER_READY);
						break;
					}
					default:{
						break;
					}
				}
				break;
			}
			case 1:{
				switch(this.type) {
				case 0:{
					this.setMessageType(MessageType.CATEGORY_SELECTOR_ANNOUNCEMENT);
					break;
				}
				case 1:{
					this.setMessageType(MessageType.CATEGORY_SELECTION );
					break;
				}
				case 2:{
					this.setMessageType(MessageType.QUESTION);
					break;
				}
				case 3:{
					this.setMessageType(MessageType.ANSWER);
					break;
				}
				case 4:{
					this.setMessageType(MessageType.BUZZ );
					break;
				}
				case 5:{
					this.setMessageType(MessageType.BUZZ_RESULT);
					break;
				}
				case 6:{
					this.setMessageType(MessageType.SCOREBOARD);
					break;
				}
				case 7:{
					this.setMessageType(MessageType.ANSWER_RESULT );
					break;
				}
				case 8:{
					this.setMessageType(MessageType.SCREW);
					break;
				}
				case 9:{
					this.setMessageType(MessageType.SCREW_RESULT);
					break;
				}
				default:{
					break;
				}
			}
				break;
			}
			case 2:{
				switch(this.type) {
				case 0:{
					this.setMessageType(MessageType.GAME_END);
					break;
				}
				default:{
					break;
				}
			}
				break;
			}
			case 3:{
				switch(this.type) {
				case 0:{
					this.setMessageType(MessageType.GENERAL_TEXT);
					break;
				}
				case 1:{
					this.setMessageType(MessageType.PLAYER_LIST);
					break;
				}
				default:{
					break;
				}
			}
				break;
			}
			default:{
				break;
			}
		}
	}
}
