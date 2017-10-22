package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.KeluargaModel;
import com.example.service.KeluargaService;

@Controller
public class KeluargaController {
	@Autowired
	KeluargaService keluargaDAO;
	
	@RequestMapping("/keluarga")
	public String view(Model model, @RequestParam(value = "nkk", required = true) String nkk) {
		KeluargaModel keluarga = keluargaDAO.selectKeluarga(nkk);
		
		if(keluarga != null) {
			List<String> date = keluargaDAO.convertDate(keluarga);
			model.addAttribute("date", date);
			model.addAttribute("keluarga", keluarga);
			return "view-keluarga";
		} else {
			model.addAttribute("nkk", nkk);
			return "nkk-not-found";
		}
	}
	
	@RequestMapping("/keluarga/tambah")
	public String tambah(Model model) {
		model.addAttribute("keluarga", new KeluargaModel());
		return "form-add-keluarga";
	}
	
	@RequestMapping(value = "/keluarga/tambah", method = RequestMethod.POST)
	public String tambahSubmit(KeluargaModel keluarga, Model model) {
		String nkk = keluargaDAO.addKeluarga(keluarga);
		model.addAttribute("nkk", nkk);
		return "add-keluarga-success";
	}
	
	@RequestMapping("/keluarga/ubah/{nkk}")
	public String update(Model model, @PathVariable(value = "nkk") String nkk)
	{
		KeluargaModel keluarga = keluargaDAO.selectKeluarga(nkk);
		
		if(keluarga != null) {
			model.addAttribute("keluarga", keluarga);
			model.addAttribute("nkk", nkk);
			return "form-update-keluarga";
		} else {
			model.addAttribute("nkk", nkk);
			return "nkk-not-found";
		}
	}
	
	@RequestMapping(value = "/keluarga/ubah/{nkk}", method = RequestMethod.POST)
	public String updateSubmit(KeluargaModel keluarga, Model model, @PathVariable(value = "nkk") String nkk)
	{
		keluargaDAO.updateKeluarga(keluarga, nkk);
		model.addAttribute("nkk", nkk);
		return "update-keluarga-success";
	}
}
