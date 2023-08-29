package kr.or.iei.pay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.iei.pay.model.service.PayService;
import kr.or.iei.pay.model.vo.Pay;
import kr.or.iei.product.model.vo.Product;


@Controller
@RequestMapping(value = "/pay")
public class PayController {
	@Autowired
	private PayService payService;
	
	@ResponseBody
	@GetMapping(value = "/insertPay")
	public int insertPay(Pay p, Model model, int productNo) {
		// 결제한 상품의 현재 재고 확인
		Product product = payService.checkStock(productNo);
		int currentStock = product.getProductStock();
		if(currentStock <= 0) {
			return 0;
		}else {
			// pay테이블에 결제내역 업로드
			int result1 = payService.insertPay(p);
			
			// 결제한 상품의 재고 감소
			int result2 = payService.updateProductStock(productNo);
			
			int result = result1 + result2;
			
			return result;			
		}
	}
	
	@GetMapping(value = "/paySuccess")
	public String paySuccess(Model model) {
		model.addAttribute("title", "결제 성공");
		model.addAttribute("msg", "결제가 완료되었습니다.");
		model.addAttribute("icon", "success");
		model.addAttribute("loc", "/product/list");
		return "common/msg";
	}
	
	@GetMapping(value = "/payError")
	public String payError(Model model) {
		model.addAttribute("title", "결제 실패");
		model.addAttribute("msg", "결제가 실패했습니다. 잠시 후 다시 시도해주세요.");
		model.addAttribute("icon", "error");
		model.addAttribute("loc", "/product/list");
		return "common/msg";
	}
}