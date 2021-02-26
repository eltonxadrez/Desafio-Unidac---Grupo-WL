package com.example.unidac.api.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.unidac.api.model.Colaborador;
import com.example.unidac.api.repository.ColaboradorRepository;

@RestController
@RequestMapping("/colaboradores")
public class ColaboradorResource {

	@Autowired
	ColaboradorRepository colaboradorRepository;
	
	//Salvar colaborador
	
	@PostMapping
	public void criar(@Valid @RequestBody Colaborador colaborador, HttpServletResponse response) {
		Colaborador colaboradorVerificado = ignorarCodigo(colaborador, new Colaborador());
		colaboradorRepository.salvar(colaboradorVerificado.getNome(), colaboradorVerificado.getCpf());
	}
	
	//Atualizar colaborador
	
	@PutMapping("/{codigo}")
	public void atualizar(@Valid @RequestBody Colaborador colaborador, @PathVariable Long codigo,HttpServletResponse response) {
		colaboradorRepository.atualizar(colaborador.getNome(), colaborador.getCpf(), codigo);
	}
	
	//Listar todos os colaboradores
	@CrossOrigin
	@GetMapping
	public ResponseEntity<?> listar() {
		List<Colaborador> colaboradores = colaboradorRepository.listarTodos();
		return !colaboradores.isEmpty() ? ResponseEntity.ok(colaboradores) : ResponseEntity.noContent().build();
	}

	//Listar colaboradores pelo código
	@CrossOrigin
	@GetMapping("/{codigo}")
	public ResponseEntity<List<Colaborador>> buscarPeloCodigo(@PathVariable Long codigo) {
		List<Colaborador> colaboradores = colaboradorRepository.listarPorCodigo(codigo);
		return !colaboradores.isEmpty() ? ResponseEntity.ok(colaboradores) : ResponseEntity.notFound().build();
	}

	//Deletar colaborador pelo código
	@DeleteMapping("/{codigo}")
//	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long codigo) {
		colaboradorRepository.deletarOpcoesPorCodigo(codigo);
		colaboradorRepository.deletarPorCodigo(codigo);
	}
	
	//Ignorar codigo caso o cliente passe o campo codigo no corpo da mensagem
	private Colaborador ignorarCodigo(Colaborador atualizacao, Colaborador novo) {
		BeanUtils.copyProperties(atualizacao, novo, "codigo");
		return novo;
	}
	
}
