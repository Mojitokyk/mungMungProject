package kr.or.iei.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.iei.product.model.service.ProductService;

@Controller
@RequestMapping(value = "/product")
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@GetMapping(value = "/list")
	public String productList(Model model) {
		List productList = productService.selectProductList();
		model.addAttribute("productList", productList);
		return "product/productList";
	}
}