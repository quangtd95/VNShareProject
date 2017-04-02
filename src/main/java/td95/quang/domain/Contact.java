package td95.quang.domain;

import lombok.Data;

@Data
public class Contact {
	private String name;
	private String phone;
	private String email;
	private String content;
	
	public String toString(){
		return name+"\n"+phone+"\n"+email+"\n"+content+"\n";
	}

	public Contact(String name, String phone, String email, String content) {
		super();
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.content = content;
	}
	public Contact(){}
	
	
}
