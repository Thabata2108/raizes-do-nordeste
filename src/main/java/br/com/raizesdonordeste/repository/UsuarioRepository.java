package br.com.raizesdonordeste.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.raizesdonordeste.model.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

}