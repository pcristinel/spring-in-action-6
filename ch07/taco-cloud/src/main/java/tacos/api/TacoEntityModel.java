package tacos.api;

import java.util.Date;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.core.Relation;
import lombok.Getter;
import tacos.domain.Taco;

@Relation(value = "taco", collectionRelation = "tacos")
public class TacoEntityModel extends CollectionModel<TacoEntityModel> {

	private static final IngredientEntityModelAssembler ingredientAssembler =
			new IngredientEntityModelAssembler();

	@Getter
	private final String name;

	@Getter
	private final Date createdAt;

	@Getter
	private final CollectionModel<IngredientEntityModel> ingredients;

	public TacoEntityModel(Taco taco) {
		this.name = taco.getName();
		this.createdAt = taco.getCreatedAt();
		this.ingredients = ingredientAssembler.toCollectionModel(taco.getIngredients());
	}

}
