package com.example.unidac.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.unidac.api.model.Colaborador;
import com.example.unidac.api.model.OpcaoAlimento;
import com.example.unidac.api.repository.ColaboradorRepository;
import com.example.unidac.api.repository.OpcaoAlimentoRepository;

@RestController
@RequestMapping("/opcoes")
public class OpcaoAlimentoResource {

	@Autowired
	OpcaoAlimentoRepository opcaoAlimentoRepository;

	@Autowired
	ColaboradorRepository colaboradorRepository;

	// Salvar uma opção de alimento
	@PostMapping
	public ResponseEntity<?> criar(@Valid @RequestBody OpcaoAlimento opcaoAlimento, HttpServletResponse response) {
//		OpcaoAlimento opcaoAlimentoVerificado = ignorarCodigo(opcaoAlimento, new OpcaoAlimento());
		if (opcaoAlimento.getColaborador() != null) {
			if (opcaoAlimento.getColaborador().getCodigo() != null) {
				List<Colaborador> colaboradores = colaboradorRepository.listarPorCodigo(opcaoAlimento.getColaborador().getCodigo());
				if (!colaboradores.isEmpty()) {
					opcaoAlimentoRepository.salvar(opcaoAlimento.getNome(), opcaoAlimento.getColaborador().getCodigo());
					return ResponseEntity.ok().build();

				}
			}
		}
		return ResponseEntity.badRequest().build();
	}

	// Atualizar as opções de alimento
	@PutMapping("/{codigo}")
	public void atualizar(@Valid @RequestBody OpcaoAlimento opcaoAlimento, @PathVariable Long codigo,
			HttpServletResponse response) {
		opcaoAlimentoRepository.atualizar(opcaoAlimento.getNome(), codigo);
	}

	// Listar todas as opções de alimento
	@GetMapping
	public ResponseEntity<?> listar() {
		List<OpcaoAlimento> opcaoAlimentos = opcaoAlimentoRepository.listarTodos();
		return !opcaoAlimentos.isEmpty() ? ResponseEntity.ok(opcaoAlimentos) : ResponseEntity.noContent().build();
	}

	// Listar opções de alimento pelo código
	@GetMapping("/{codigo}")
	public ResponseEntity<List<OpcaoAlimento>> buscarPeloCodigo(@PathVariable Long codigo) {
		List<OpcaoAlimento> opcaoAlimentos = opcaoAlimentoRepository.listarPorCodigo(codigo);
		return !opcaoAlimentos.isEmpty() ? ResponseEntity.ok(opcaoAlimentos) : ResponseEntity.notFound().build();
	}

	// Deletar opções de alimento pelo código
	@DeleteMapping("/{codigo}")
	public void delete(@PathVariable Long codigo) {
		opcaoAlimentoRepository.deletarPorCodigo(codigo);
	}

//	//Ignorar codigo caso o cliente passe o campo codigo no corpo da mensagem
//	private OpcaoAlimento ignorarCodigo(OpcaoAlimento atualizacao, OpcaoAlimento novo) {
//		BeanUtils.copyProperties(atualizacao, novo, "codigo");
//		return novo;
//	}
}
