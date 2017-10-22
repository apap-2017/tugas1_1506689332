package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.KeluargaMapper;
import com.example.model.KeluargaModel;
import com.example.model.PendudukModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KeluargaServiceDatabase implements KeluargaService {
	@Autowired
	private KeluargaMapper keluargaMapper;
	
	@Override
	public KeluargaModel selectKeluarga(String nomor_kk) {
		log.info ("select keluarga with nkk {}", nomor_kk);
        return keluargaMapper.selectKeluarga(nomor_kk);
	}
	
	@Override
	public List<String> convertDate(KeluargaModel keluarga) {
		List<String> dataAnggotaConverted = new ArrayList<>();
		List<PendudukModel> anggota = keluarga.getAnggotaKeluarga();
		int sizeAnggota = keluarga.getAnggotaKeluarga().size();
		
		for(int ii = 0; ii < sizeAnggota; ii++) {
			String[] splitDate = anggota.get(ii).getTanggal_lahir().split("-");
			log.info("tgl {}", anggota.get(ii).getTanggal_lahir());
			
			dataAnggotaConverted.add(splitDate[2] + "-" + splitDate[1] + "-" + splitDate[0]);
		}
		return dataAnggotaConverted;
	}

	@Override
	public String addKeluarga(KeluargaModel keluarga) {
		String id = "" + (Integer.parseInt(keluargaMapper.getLastKeluarga().getId()) + 1);
		keluarga.setId(id);
		
		String idKelurahan = keluargaMapper.getKelurahan(keluarga.getKelurahan().toUpperCase(), keluarga.getKecamatan().toUpperCase()).getId();
		keluarga.setId_kelurahan(idKelurahan);
		
		String prefix = keluargaMapper.getKodeKecamatan(keluarga.getKecamatan().toUpperCase(), keluarga.getKota().toUpperCase()).getKode_kecamatan().substring(0, 6);
		long millis = System.currentTimeMillis();  
        java.sql.Date date = new java.sql.Date(millis);
        String[] dateNow = ("" + date).split("-");
        
        
        String nkk = prefix + dateNow[2] + dateNow[1] + dateNow[0].substring(2, 4) + "%";
        List<KeluargaModel> keluargaAda = keluargaMapper.cekKeluarga(nkk);
        int angkaAkhir = 0;
        if(keluargaAda.size() > 0) {
        	angkaAkhir = Integer.parseInt(keluargaAda.get(keluargaAda.size()-1).getNomor_kk().substring(12, 16));
        }
        nkk = nkk.substring(0, 12);
        if(angkaAkhir < 9) {
			nkk += "000" + (angkaAkhir + 1);
		} else if(angkaAkhir < 99) {
			nkk += "00" + (angkaAkhir + 1);
		} else if(angkaAkhir < 999) {
			nkk += "0" + (angkaAkhir + 1);
		} else {
			nkk += (angkaAkhir + 1);
		}
        keluarga.setNomor_kk(nkk);
        keluarga.setIs_tidak_berlaku("0");
        
        keluargaMapper.addKeluarga(keluarga);
        log.info("size orang ada {}", angkaAkhir);
        log.info("kode kecamatan {}", prefix);
		log.info("tgl skrg {}{}{}", dateNow[2], dateNow[1], dateNow[0]);
		log.info("nkk {}", nkk);
		
		return nkk;
	}

	@Override
	public void updateKeluarga(KeluargaModel keluarga, String nkkLama) {
		KeluargaModel keluargaLama = keluargaMapper.selectKeluarga(nkkLama);
		keluarga.setId(keluargaLama.getId());
		
		long millis = System.currentTimeMillis();  
        java.sql.Date date = new java.sql.Date(millis);
        String[] dateNow = ("" + date).split("-");
        String tglSekarang = dateNow[2] + dateNow[1] + dateNow[0].substring(2, 4);
		
		if(!keluargaLama.getKelurahan().equals(keluarga.getKelurahan()) || !nkkLama.substring(6, 12).equals(tglSekarang)) {
			String idKelurahan = keluargaMapper.getKelurahan(keluarga.getKelurahan().toUpperCase(), keluarga.getKecamatan().toUpperCase()).getId();
			keluarga.setId_kelurahan(idKelurahan);
			
			String prefix = keluargaMapper.getKodeKecamatan(keluarga.getKecamatan().toUpperCase(), keluarga.getKota().toUpperCase()).getKode_kecamatan().substring(0, 6);
	        
	        
	        String nkk = prefix + dateNow[2] + dateNow[1] + dateNow[0].substring(2, 4) + "%";
	        List<KeluargaModel> keluargaAda = keluargaMapper.cekKeluarga(nkk);
	        int angkaAkhir = 0;
	        if(keluargaAda.size() > 0) {
	        	angkaAkhir = Integer.parseInt(keluargaAda.get(keluargaAda.size()-1).getNomor_kk().substring(12, 16));
	        }
	        nkk = nkk.substring(0, 12);
	        if(angkaAkhir < 9) {
				nkk += "000" + (angkaAkhir + 1);
			} else if(angkaAkhir < 99) {
				nkk += "00" + (angkaAkhir + 1);
			} else if(angkaAkhir < 999) {
				nkk += "0" + (angkaAkhir + 1);
			} else {
				nkk += (angkaAkhir + 1);
			}
	        keluarga.setNomor_kk(nkk);
		} else {
			keluarga.setId_kelurahan(keluargaLama.getId_kelurahan());
			keluarga.setNomor_kk(nkkLama);
		}
		keluarga.setIs_tidak_berlaku("0");
		keluargaMapper.updateKeluarga(keluarga, nkkLama);
	}
}
