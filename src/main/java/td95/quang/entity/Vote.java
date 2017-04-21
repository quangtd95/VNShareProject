package td95.quang.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="vote")
public class Vote{
	public static int VOTE_UP = 1;
	public static int VOTE_DOWN = 0;
	public static int NONE = -1;
	
	@Id
	@GeneratedValue
	@Column(name="id",nullable= false)
	private int id;
	
	@Column(name="user_id",nullable=false)
	private int userId;
	
	@Column(name="post_id",nullable=false)
	private int postId;
	
	@Column(name="status")
	private int status;
}