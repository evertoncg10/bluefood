package br.com.everton.bluefood.application;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.everton.bluefood.domain.restaurante.Restaurante;
import br.com.everton.bluefood.domain.restaurante.RestauranteRepository;

public class RestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;

	public void saveRestaurante(Restaurante restaurante) throws ValidationException {

		if (!validateEmail(restaurante.getEmail(), restaurante.getId())) {
			throw new ValidationException("O E-mail está duplicado");
		}

		restauranteRepository.save(restaurante);
	}

	private boolean validateEmail(String email, Integer id) {

		Restaurante restaurante = restauranteRepository.findByEmail(email);

		if (restaurante != null) {
			if (id == null) {
				return false;
			}

			if (!restaurante.getId().equals(id)) {
				return false;
			}
		}
		return true;
	}

}
