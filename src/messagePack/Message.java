package messagePack;
import java.io.Serializable;

public class Message implements Serializable {
	private static int count = 1;
	private final int id;
	protected String type;
    protected String status;
    protected String text;

    public Message(){
    	this.id = count++;
        this.type = "Undefined";
        this.status = "Undefined";
        this.text = "Undefined";
    }

    public Message(String text, String type, String status){
        this.id = count++;
    	setType(type);
        setStatus(status);
        setText(text);
    }

    private void setType(String type){
    	this.type = type;
    }

    public void setStatus(String status){
    	this.status = status;
    }

    public void setText(String text){
    	this.text = text;
    }

    public int getId() {
    	return id;
    }
    
    public String getType(){
    	return type;
    }

    public String getStatus(){
    	return status;
    }

    public String getText(){
    	return text;
    }
}
