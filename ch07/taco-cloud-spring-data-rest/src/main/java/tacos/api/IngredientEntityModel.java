package tacos.api;

import org.springframework.hateoas.EntityModel;
import lombok.Getter;
import tacos.domain.Ingredient;
import tacos.domain.Ingredient.Type;

public class IngredientEntityModel extends EntityModel<IngredientEntityModel> {

	@Getter
	private String name;

	@Getter
	private Type type;

	public IngredientEntityModel(Ingredient ingredient) {
		this.name = ingredient.getName();
		this.type = ingredient.getType();
	}

}
