package jp.co.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Employee;
import jp.co.sample.form.UpdateEmployeeForm;
import jp.co.sample.service.EmployeeService;

/**
 * 従業員情報を操作するコントローラ.
 * 
 * @author fukushima
 *
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@ModelAttribute
	public UpdateEmployeeForm setUpUpdateEmployeeForm() {
		UpdateEmployeeForm updateEmployeeForm = new UpdateEmployeeForm();
		return updateEmployeeForm;
	}
	
	/**
	 * 従業員一覧を出力する.
	 * 
	 * @param model モデル
	 * @return 従業員一覧画面
	 */
	@RequestMapping("/showList")
	public String showList(Model model) {
		model.addAttribute("employeeList", employeeService.showList());
		return "/employee/list";
	}
	
	/**
	 * 従業員情報を取得する.
	 * 
	 * @param id 従業員ID
	 * @param model モデル
	 * @return 従業員詳細画面
	 */
	@RequestMapping("/showDetail")
	public String showDetail(String id, Model model) {
		model.addAttribute("employee", employeeService.showDetail(Integer.parseInt(id)));
		return "/employee/detail";
	}
	
	/**
	 * 従業員詳細の扶養人数を更新する.
	 * 
	 * @param form 従業員情報
	 * @return 従業員詳細画面
	 */
	@RequestMapping("/update")
	public String update(UpdateEmployeeForm form) {
		Employee employee = employeeService.showDetail(Integer.parseInt(form.getId()));
		employee.setDependentsCount(Integer.parseInt(form.getDependentsCount()));
		employeeService.update(employee);
		return "redirect:/employee/showList";
	}
}
