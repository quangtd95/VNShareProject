package td95.quang.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "post")
public class Post {
	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false)
	private int id;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "content", nullable = false)
	private String content;

	@Column(name = "count_views")
	private int countViews;

	@Column(name = "points")
	private int points;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToMany
	@JoinTable(
			name="post_tag",
			joinColumns= @JoinColumn(name="post_id"),
			inverseJoinColumns = @JoinColumn(name="tag_id")
			)
	private Set<Tag> tags;

}
