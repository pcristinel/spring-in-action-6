package tacos.api;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tacos.data.TacoRepository;
import tacos.domain.Taco;

@RestController
@RequestMapping(path = "/design", produces = "application/json")
@CrossOrigin(origins = "*")
public class DesignTacoController {
	private TacoRepository tacoRepo;

	public DesignTacoController(TacoRepository tacoRepo) {
		this.tacoRepo = tacoRepo;
	}


	@GetMapping("/recent")
	public CollectionModel<TacoEntityModel> recentTacos() {
		PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());

		List<Taco> tacos = tacoRepo.findAll(page).getContent();

		List<TacoEntityModel> tacoResources = new TacoEntityModelAssembler().toModels(tacos);

		CollectionModel<TacoEntityModel> recentResources =
				new CollectionModel<TacoEntityModel>(tacoResources);

		recentResources.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(DesignTacoController.class).recentTacos())
				.withRel("recents"));

		return recentResources;
	}


	@GetMapping("/{id}")
	public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
		Optional<Taco> optTaco = tacoRepo.findById(id);

		if (optTaco.isPresent()) {
			return new ResponseEntity<>(optTaco.get(), HttpStatus.OK);
		}

		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}


	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Taco postTaco(@RequestBody Taco taco) {
		return tacoRepo.save(taco);
	}



}
