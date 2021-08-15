package tacos.web;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Taco;
import tacos.data.IngredientRepository;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

	private final IngredientRepository ingredientRepo;

	@Autowired
	public DesignTacoController(IngredientRepository ingredientRepo) {
		this.ingredientRepo = ingredientRepo;
	}

	@ModelAttribute
	public void addIngredientsToModel(Model model) {
		Iterable<Ingredient> ingredients = ingredientRepo.findAll();
		Type[] types = Ingredient.Type.values();
		for (Type type : types) {
			model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
		}
	}


	@GetMapping
	public String showDesignForm(Model model) {
		model.addAttribute("taco", new Taco());

		return "design";
	}


	@PostMapping
	public String processTaco(@Valid @ModelAttribute("taco") Taco taco, Errors errors) {
		if (errors.hasErrors()) {
			return "design";
		}

		// Save the taco...
		// We'll do this in chapter 3
		log.info("Processing taco: " + taco);

		return "redirect:/orders/current";
	}


	private Iterable<Ingredient> filterByType(Iterable<Ingredient> ingredientsList, Type type) {
		List<Ingredient> ingredients = (List<Ingredient>) ingredientsList;
		
		return ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
	}
}
