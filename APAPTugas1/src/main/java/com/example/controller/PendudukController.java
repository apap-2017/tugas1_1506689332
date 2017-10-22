package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.KecamatanModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;
import com.example.service.PendudukService;
import com.example.service.WilayahService;


@Controller
public class PendudukController
{
	@Autowired
	PendudukService pendudukDAO;
	@Autowired
	WilayahService wilayahDAO;
	
	public boolean masukMati = false;
	
	@RequestMapping("/")
    public String index ()
    {
        return "index";
    }
	
	@RequestMapping("/penduduk")
	public String view(Model model, @RequestParam(value = "nik", required = true) String nik) {
		if(masukMati) {
			masukMati = false;
			model.addAttribute("nik", nik);
			return "nonaktifkan-penduduk-success";
		} else {
			PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);
			
			if(penduduk != null) {
				String date = pendudukDAO.extractDate(penduduk.getTanggal_lahir());
				model.addAttribute("date", date);
				model.addAttribute("penduduk", penduduk);
				return "view-penduduk";
			} else {
				model.addAttribute("nik", nik);
				return "nik-not-found";
			}
		}
	}
	
	@RequestMapping("/penduduk/tambah")
	public String tambah(Model model)
	{
		model.addAttribute("penduduk", new PendudukModel());
		return "form-add-penduduk";
	}
	
	@RequestMapping(value = "/penduduk/tambah", method = RequestMethod.POST)
	public String tambahSubmit(PendudukModel penduduk, Model model)
	{
		String nik = pendudukDAO.addPenduduk(penduduk);
		model.addAttribute("nik", nik);
		return "add-penduduk-success";
	}
	
	@RequestMapping("/penduduk/ubah/{nik}")
	public String update(Model model, @PathVariable(value = "nik") String nik)
	{
		PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);
		
		if(penduduk != null) {
			model.addAttribute("penduduk", penduduk);
			model.addAttribute("nik", nik);
			return "form-update-penduduk";
		} else {
			model.addAttribute("nik", nik);
			return "nik-not-found";
		}
	}
	
	@RequestMapping(value = "/penduduk/ubah/{nik}", method = RequestMethod.POST)
	public String updateSubmit(PendudukModel penduduk, Model model, @PathVariable(value = "nik") String nik)
	{
		pendudukDAO.updatePenduduk(penduduk, nik);
		model.addAttribute("nik", nik);
		return "update-penduduk-success";
	}
	
	@RequestMapping(value = "/penduduk/mati", method = RequestMethod.POST)
	public String nonaktifkanPenduduk(PendudukModel penduduk)
	{
		String nik = penduduk.getNik();
		pendudukDAO.nonaktifkanPenduduk(nik);
		masukMati = true;
		return "redirect:/penduduk?nik=" + nik;
	}
	
	@RequestMapping("/penduduk/cari")
	public String cariPenduduk(Model model, @RequestParam(value = "kt", required = false) String kt, @RequestParam(value = "kc", required = false) String kc, @RequestParam(value = "kl", required = false) String kl)
	{
		if(kl != null) {
			List<PendudukModel> penduduk = wilayahDAO.getPendudukByKelurahan(kl);
			String namaKota = wilayahDAO.getNamaKotaById(kt);
			String namaKecamatan = wilayahDAO.getNamaKecamatanById(kc);
			String namaKelurahan = wilayahDAO.getNamaKelurahanById(kl);
			
			model.addAttribute("penduduk", penduduk);
			model.addAttribute("namaKelurahan", namaKelurahan);
			model.addAttribute("kl", kl);
			model.addAttribute("namaKecamatan", namaKecamatan);
			model.addAttribute("kc", kc);
			model.addAttribute("namaKota", namaKota);
			model.addAttribute("kt", kt);
			return "find-penduduk-result";
		} else if(kc != null) {
			List<KelurahanModel> kelurahan = wilayahDAO.getKelurahanByKecamatan(kc);
			String namaKota = wilayahDAO.getNamaKotaById(kt);
			String namaKecamatan = wilayahDAO.getNamaKecamatanById(kc);
			
			model.addAttribute("kelurahan", kelurahan);
			model.addAttribute("namaKecamatan", namaKecamatan);
			model.addAttribute("kc", kc);
			model.addAttribute("namaKota", namaKota);
			model.addAttribute("kt", kt);
			return "find-penduduk-kelurahan";
		} else if(kt != null) {
			List<KecamatanModel> kecamatan = wilayahDAO.getKecamatanByKota(kt);
			String namaKota = wilayahDAO.getNamaKotaById(kt);
			
			model.addAttribute("kecamatan", kecamatan);
			model.addAttribute("namaKota", namaKota);
			model.addAttribute("kt", kt);
			return "find-penduduk-kecamatan";
		} else {
			List<KotaModel> kota = wilayahDAO.selectAllKota();
			model.addAttribute("kota", kota);
			model.addAttribute("data", new KotaModel());
			return "find-penduduk-kota";
		}
	}
}