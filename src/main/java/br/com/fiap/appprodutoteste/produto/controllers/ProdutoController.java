package br.com.fiap.appprodutoteste.produto.controllers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import br.com.fiap.appprodutoteste.produto.dto.ProdutoDto;
import br.com.fiap.appprodutoteste.produto.model.Produto;
import br.com.fiap.appprodutoteste.produto.repositories.ProdutoRepository;


@Controller
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping("/produtos")
	public ModelAndView index() {
		ModelAndView modelView = new ModelAndView("produtos/index");
		
		List<Produto> produtoDaRepository = produtoRepository.findAll();
		modelView.addObject("listarProdutos", produtoDaRepository);
		
		return modelView;
	}
	
	@GetMapping("/produtos/criar")
	public ModelAndView criar(ProdutoDto produto) {
		ModelAndView modelView = new ModelAndView("produtos/criar");
		return modelView;
	}
	
	@PostMapping("produtos")
	public ModelAndView salvar(@Valid ProdutoDto produto, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return new ModelAndView("produtos/criar");
		}
		
		Produto produtoEntity = modelMapper.map(produto, Produto.class);
		produtoRepository.save(produtoEntity);
		return new ModelAndView("redirect:/produtos");
	}
	
	@GetMapping("produtos/{id}")
	public ModelAndView mostrar(@PathVariable Long id) {
		Optional<Produto> produto = produtoRepository.findById(id);
		
		if(produto.isPresent()) {
			Produto produtoGet = produto.get();
			ModelAndView modelView = new ModelAndView("produtos/detalhe");
			modelView.addObject("produto",produtoGet);
			return modelView;
		}
		System.out.println("não encontrado!");
		return new ModelAndView("redirect:/produtos");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
