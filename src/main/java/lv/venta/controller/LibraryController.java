package lv.venta.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

public class LibraryController {
	@Controller
	@RequestMapping("/filter")
	public class FilteringController {
		
		
		@GetMapping("/grade/failed")
		public String getFilterGradeFailed(Model model) {
			
			try {
				return "error-page";
			}
			catch(Exception e) {
				model.addAttribute("mypackage", e.getMessage());
				return "error-page";
			}
		}
																																																																																																														
	}
}
