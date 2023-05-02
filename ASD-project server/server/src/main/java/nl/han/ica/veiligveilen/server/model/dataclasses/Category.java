package nl.han.ica.veiligveilen.server.model.dataclasses;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Type;

/**
 * The dataclass for Categories.
 * The server won't create new ones.
 */
@Entity(name = "category")
public class Category {

	@Id
	@Type(type = "uuid-char")
	@Size(max = 36)
	@Column(name = "category_id")
	private UUID categoryId;


	@Size(max = 128)
	@Column(name = "category_name")
	private String categoryName;

	/**
	 * The constructor.
	 * @param categoryId A UUID. Automatically generated when a new category gets added to the database.
	 * @param categoryName A String. The name of the category.
	 */
	public Category(UUID categoryId, String categoryName) {
		this.categoryId = categoryId;
		this.categoryName = categoryName;
	}

	public Category() {

	}

	public UUID getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(UUID categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
