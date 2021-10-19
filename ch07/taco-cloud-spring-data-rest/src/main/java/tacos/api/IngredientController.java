package tacos.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tacos.data.IngredientRepository;
import tacos.domain.Ingredient;

// @RestController
@RequestMapping(path = "/ingredientsx", produces = "application/json")
@CrossOrigin(origins = "*")
public class IngredientController {
	private IngredientRepository repo;

	@Autowired
	public IngredientController(IngredientRepository repo) {
		this.repo = repo;
	}

	@GetMapping
	public Iterable<Ingredient> allIngredients() {
		return repo.findAll();
	}
}
