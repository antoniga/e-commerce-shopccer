package com.shopccer.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopccer.admin.exception.UsuarioNotFoundException;
import com.shopccer.admin.repository.UsuarioRepository;
import com.shopccer.common.entity.Usuario;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {
	
	public static final Integer USUARIOS_POR_PAG = 4;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<Usuario> listAll() {
		return (List<Usuario>) usuarioRepository.findAll();
	}

	public Usuario save(Usuario usuario) {

		boolean isUpdateUsuario = (usuario.getIdUsuario() != null);

		if (isUpdateUsuario) {
			Usuario usuarioEditar = usuarioRepository.findById(usuario.getIdUsuario()).get();

			if (usuario.getpassword().isEmpty()) {
				usuario.setpassword(usuarioEditar.getpassword());
			} else {
				encodePassword(usuario);
			}

		} else {

			encodePassword(usuario);
		}

		return usuarioRepository.save(usuario);
	}

	private void encodePassword(Usuario usuario) {

		String encodedPwd = passwordEncoder.encode(usuario.getpassword());
		usuario.setpassword(encodedPwd);
	}

	public Boolean isEmailUnique(Integer id, String email) {
		
		/**Buscamos el usuario por el email*/
		Usuario usuarioEmail = usuarioRepository.findByEmail(email);
		
		/**Si no existe, es null, es que no existe y el email es unico*/
		if (usuarioEmail == null)
			return true;
		
		/**Si existe el email, el usuario existe, miramos si es nueva creacion de usuario o edicion*/
		boolean nuevoUsuario = (id == null);

		/**Si es nuevo usuario*/
		if (nuevoUsuario) {
			/**Y el email existe, devolvemos false*/
			if (usuarioEmail != null)
				return false;
		} else {
			/**Si el email no exsite, estamos en edicion, devolvemos false si el id no coincide*/
			if (usuarioEmail.getIdUsuario() != id) {
				return false;
			}
		}
		return true;
	}

	
	public Usuario findById(Integer id) throws UsuarioNotFoundException {

		return usuarioRepository.findById(id)
				.orElseThrow(() -> new UsuarioNotFoundException("No existe ningún usuario con id: " + id));
	}

	public void deleteByID(Integer id) throws UsuarioNotFoundException {

		if (usuarioRepository.findById(id).isPresent()) {
			usuarioRepository.deleteById(id);
		} else {
			throw new UsuarioNotFoundException("No existe ningún usuario con id: " + id);

		}

	}
	
	public void updateUsuarioActivo(Integer id, Boolean activo) {
		
		usuarioRepository.updateUsuarioActivo(id, activo);
	}

	
	public Page<Usuario> listByPage(Integer numeroPagina, String campoOrden, String dirOrden, String palabraClave) {

		Sort sort = Sort.by(campoOrden);

		sort = ("asc").equals(dirOrden) ? sort.ascending() : sort.descending();
		

		Pageable pageable = PageRequest.of(numeroPagina - 1, USUARIOS_POR_PAG, sort);

		if (palabraClave != null) {

			return usuarioRepository.findAll(palabraClave, pageable);
		}

		return usuarioRepository.findAll(pageable);
	}

}
