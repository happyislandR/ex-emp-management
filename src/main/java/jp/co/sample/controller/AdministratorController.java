package jp.co.sample.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Administrator;
import jp.co.sample.form.InsertAdministratorForm;
import jp.co.sample.form.LoginForm;
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
	
	@Autowired
	private HttpSession session;

	@ModelAttribute
	public InsertAdministratorForm setUpInsertAdministratorForm() {
		InsertAdministratorForm insertAdministratorForm = new InsertAdministratorForm();
		return insertAdministratorForm;
	}
	
	@ModelAttribute
	public LoginForm setUpLoginForm() {
		LoginForm loginForm = new LoginForm();
		return loginForm;
	}
	
	/**
	 * ログイン画面にフォワードする.
	 * 
	 * @return ログイン画面
	 */
	@RequestMapping("/")
	public String toLogin() {
		return "administrator/login";
	}
	
	/**
	 * 管理者登録画面にフォワードする.
	 * 
	 * @return 管理登録者画面
	 */
	@RequestMapping("/toInsert")
	public String toInsert() {
		return "administrator/insert";
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
	
	/**
	 * ログイン処理をする.
	 * 
	 * @param form ログインフォーム
	 * @param model モデル
	 * @return ログイン画面 or 従業員一覧ページ
	 */
	@RequestMapping("/login")
	public String login(LoginForm form, Model model) {
		Administrator administrator = administratorService.login(form.getMailAddress(), form.getPassword());
		if(administrator == null) {
			model.addAttribute("message", "メールアドレスまたはパスワードが不正です。");
			return "/administrator/login";
		}
		session.setAttribute("administratorName", administrator);
		return "forward:/employee/showList";
	}
	
	/**
	 * ログアウトする.
	 * 
	 * @return ログイン画面
	 */
	@RequestMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/";
	}
}
