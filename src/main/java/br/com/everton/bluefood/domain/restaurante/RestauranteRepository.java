package br.com.everton.bluefood.domain.restaurante;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RestauranteRepository extends JpaRepository<Restaurante, Integer> {

	public Restaurante findByEmail(String email);

    /* Quando o método contém na sua nomenclatura o Containing, significa que ele vai procurar
     * se contém aquela string que foi passado por parâmetro
     *  O IgnoreCase significa independente de maiuscula ou minuscula
     *  Nesse caso estamos buscando o nome que contenha a parte da string do parametro 
     *  ignorando se é maiuscula ou minuscula
     */
    public List<Restaurante> findByNomeIgnoreCaseContaining(String nome);

    public List<Restaurante> findByCategorias_Id(Integer categoriaId);
}
