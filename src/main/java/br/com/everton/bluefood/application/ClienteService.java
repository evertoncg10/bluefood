package br.com.everton.bluefood.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.everton.bluefood.domain.cliente.Cliente;
import br.com.everton.bluefood.domain.cliente.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public void saveCliente(Cliente cliente) throws ValidationException {

		if (!validateEmail(cliente.getEmail(), cliente.getId())) {
			throw new ValidationException("O E-mail está duplicado");
		}

		clienteRepository.save(cliente);
	}

	private boolean validateEmail(String email, Integer id) {

		Cliente cliente = clienteRepository.findByEmail(email);

		if (cliente != null) {
			if (id == null) {
				return false;
			}

			if (!cliente.getId().equals(id)) {
				return false;
			}
		}
		return true;
	}
}
