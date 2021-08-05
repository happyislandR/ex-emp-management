package jp.co.sample.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Administrator;
import jp.co.sample.form.InsertAdministratorForm;
import jp.co.sample.service.AdministratorService;

/**
 * 管理者情報を操作するコントローラ.
 * 
 * @author fukushima
 *
 */
@Controller
@RequestMapping("/")
public class AdministratorController {
	@Autowired
	private AdministratorService administratorService;

	@ModelAttribute
	public InsertAdministratorForm setUpInsertAdministratorForm() {
		InsertAdministratorForm insertAdministratorForm = new InsertAdministratorForm();
		return insertAdministratorForm;
	}
	
	/**
	 * 管理者登録画面にフォワードする
	 * 
	 * @return 管理登録者画面
	 */
	@RequestMapping("/toInsert")
	public String toInsert() {
		return "administrator/insert.html";
	}
	
	/**
	 * 管理者情報を登録する.
	 * 
	 * @param form 管理者情報が登録されたフォーム
	 * @return ログイン画面
	 */
	@RequestMapping("/insert")
	public String insert(InsertAdministratorForm form) {
		Administrator administrator = new Administrator();
		BeanUtils.copyProperties(form, administrator);
		administratorService.insert(administrator);
		return "redirect:/";
	}
}
