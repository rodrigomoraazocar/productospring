package com.example.springboot.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.dtos.ProductRecordDto;
import com.example.springboot.models.ProductModel;
import com.example.springboot.repositories.ProductRepository;

import jakarta.validation.Valid;

@RestController // usado quando realizar uma API REST
public class ProductController {

	@Autowired // Se la realizar la inyeccion de dependencia para usar Jpa Repository
	ProductRepository productRepository;

//Post    - Guardar/Salvar del Crud 
//Records - Apartir de Java 16
//@RequestBody	- Va recibir en el cuerpo del Body html
//@Valid  - Para que tome las validaciones de la Dto	

	@PostMapping("/products")
	public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto) {
		var productModel = new ProductModel();
		// Convierte productRecordDto para productModel
		BeanUtils.copyProperties(productRecordDto, productModel);
		// status 201 - será guardado en el productModel
		return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
	}

	// Status 200 -
	@GetMapping("/products")
	public ResponseEntity<List<ProductModel>> getAllProducts() {
		return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll());
	}

	// Listar por solamente 1 producto con Id
	@GetMapping("/products/{id}")
	public ResponseEntity<Object> getOneProduct(@PathVariable(value = "id") UUID id) {
		// localizará en la base datos la id que ocupamos en la URL
		Optional<ProductModel> productO = productRepository.findById(id);
		if (productO.isEmpty()) {
			// Si está vacio
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Not found");
		}
		return ResponseEntity.status(HttpStatus.OK).body(productO.get());
	}
}
