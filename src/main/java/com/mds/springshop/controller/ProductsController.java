package com.mds.springshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mds.springshop.dao.ProductsDAO;
import com.mds.springshop.model.CosInfo;
import com.mds.springshop.model.PaginationResult;
import com.mds.springshop.model.ProductInfo;

@Controller
public class ProductsController {
	
	private int productId;
	private ProductInfo productToUpdate;
	
	@Autowired
	ProductsDAO productDAO;
	
	@RequestMapping(value =  "detaliiProdus", method=RequestMethod.GET)
	public String getProductDetails(Model model) {

	  model.addAttribute("product", productDAO.getProductById(productId));
      return "detaliiProdus";
  }
	
	@RequestMapping(value =  "detaliiProdus/{id}", method=RequestMethod.GET)
	public String getProductDetailsById(Model model, @PathVariable int id) {

	  productId = id;
      return "redirect:http://localhost:8080/SpringShop/detaliiProdus";
  }
	
	@RequestMapping(value = { "/editareProdus/{id}" }, method = RequestMethod.GET)
	@Transactional(propagation = Propagation.NEVER)
	public String editProduct(@PathVariable int id, Model model)
	{	
		ProductInfo productInfo = productDAO.getProductById(id);
		productId = id;
		productToUpdate = productInfo;
		model.addAttribute("saveProductForm", productInfo);
		model.addAttribute("product", productInfo);
		
		return "redirect:http://localhost:8080/SpringShop/editareProdus";
	}
	
	
	@RequestMapping(value={"/editareProdus"},method=RequestMethod.GET)
	public String displayEditProduct(Model model, @ModelAttribute("saveProductForm")  @Validated ProductInfo productInfo,BindingResult result,
			final RedirectAttributes redirectAttributes)
	{
		if (productInfo.getName() == null) {
			model.addAttribute("product", productToUpdate);
		} else {
			productDAO.updateProduct(productId, productInfo);
			model.addAttribute("product", productDAO.getProductById(productId));
		}

		return "editareProdus";
	}
	@RequestMapping(value={"/Cos"},method=RequestMethod.GET)
	public String displayCart(Model model,@RequestParam(value = "page", defaultValue = "1") int page,//
										  @RequestParam(value = "prodId", defaultValue = "0") int prodId){
		final int maxResult = 4;
		final int maxNavigationPage = 10;
		PaginationResult<CosInfo> result=productDAO.queryCartProducts(prodId, page, maxResult, maxNavigationPage);
		model.addAttribute("cartProducts", result);
		return "cosCurent";
	}
	@RequestMapping(value={"/Delete"},method=RequestMethod.GET)
	public String deleteFromCart(@RequestParam(value="idProd",defaultValue="0") int idProd){
		if(idProd!=0)
			productDAO.deleteCartProdId(idProd);
		return "redirect:/Cos";
	}
	@RequestMapping(value={"/UpdateCart"},method=RequestMethod.GET)
	public String updateCart(@RequestParam(value="idProd",defaultValue="0") int idProd,//
							 @RequestParam(value="quantity",defaultValue="0") int quantity){
		if(quantity>0)
			productDAO.updateCart(idProd, quantity);
		else
			productDAO.deleteCartProdId(idProd);
		return "redirect:/Cos";
	}
}







