package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.KeluargaMapper;
import com.example.dao.PendudukMapper;
import com.example.model.KeluargaModel;
import com.example.model.PendudukModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PendudukServiceDatabase implements PendudukService 
{
	@Autowired
	private PendudukMapper pendudukMapper;
	@Autowired
	private KeluargaMapper keluargaMapper;
	
	@Override
	public PendudukModel selectPenduduk(String nik) {
		log.info ("select penduduk with nik {}", nik);
        return pendudukMapper.selectPenduduk(nik);
	}
	
	@Override
	public String extractDate(String date) {
		String[] splitDate = date.split("-");
		String dateConverted = "" + Integer.parseInt(splitDate[2]) + " ";
		
		if(splitDate[1].equals("01")) {
			dateConverted += "Januari";
		} else if(splitDate[1].equals("02")) {
			dateConverted += "Februari";
		} else if(splitDate[1].equals("03")) {
			dateConverted += "Maret";
		} else if(splitDate[1].equals("04")) {
			dateConverted += "April";
		} else if(splitDate[1].equals("05")) {
			dateConverted += "Mei";
		} else if(splitDate[1].equals("06")) {
			dateConverted += "Juni";
		} else if(splitDate[1].equals("07")) {
			dateConverted += "Juli";
		} else if(splitDate[1].equals("08")) {
			dateConverted += "Agustus";
		} else if(splitDate[1].equals("09")) {
			dateConverted += "September";
		} else if(splitDate[1].equals("10")) {
			dateConverted += "Oktober";
		} else if(splitDate[1].equals("11")) {
			dateConverted += "November";
		} else {
			dateConverted += "Desember";
		}
		dateConverted += " " + splitDate[0];
		return dateConverted;
	}

	@Override
	public String addPenduduk(PendudukModel penduduk) {
		String kodeKecamatan = pendudukMapper.selectKodeKecamatan(penduduk.getId_keluarga()).getKode_kecamatan();
		String[] tgl_lahir = penduduk.getTanggal_lahir().split("-");
		String nik = "";
		
		if(penduduk.getJenis_kelamin().equals("1")) {
			nik = kodeKecamatan.substring(0, 6) + (Integer.parseInt(tgl_lahir[2]) + 40) + tgl_lahir[1] + tgl_lahir[0].substring(2, 4) + "%";
		} else {
			nik = kodeKecamatan.substring(0, 6) + Integer.parseInt(tgl_lahir[2]) + tgl_lahir[1] + tgl_lahir[0].substring(2, 4) + "%";
		}
		
		
		List<PendudukModel> pendudukAda = pendudukMapper.cekPenduduk(nik);
		int angkaAkhir = 0;
		if(pendudukAda.size() > 0) {
			angkaAkhir = Integer.parseInt(pendudukAda.get(pendudukAda.size()-1).getNik().substring(12, 16));
		}
		nik = nik.substring(0, 12);
		if(angkaAkhir < 9) {
			nik += "000" + (angkaAkhir + 1);
		} else if(angkaAkhir < 99) {
			nik += "00" + (angkaAkhir + 1);
		} else if(angkaAkhir < 999) {
			nik += "0" + (angkaAkhir + 1);
		} else {
			nik += (angkaAkhir + 1);
		}
		log.info("size orang ada {}", angkaAkhir);
		log.info("tanggal lahir {}", penduduk.getTanggal_lahir());
		log.info("kode kecamatan {}", kodeKecamatan);
		log.info("nik {}", nik);
		
		String id = "" + (Integer.parseInt(pendudukMapper.getLastPenduduk().getId()) + 1);
		
		PendudukModel newPenduduk = penduduk;
		newPenduduk.setNik(nik);
		newPenduduk.setId(id);
		pendudukMapper.addPenduduk(newPenduduk);
		return nik;
	}

	@Override
	public void updatePenduduk(PendudukModel penduduk, String nikLama) {
		PendudukModel pendudukLama = pendudukMapper.selectPenduduk(nikLama);
		log.info("pendudukLama tgl lahir {}", pendudukLama.getTanggal_lahir());
		log.info("pendudukbaru tgl lahir {}", penduduk.getTanggal_lahir());
		
		if(!pendudukLama.getId_keluarga().equals(penduduk.getId_keluarga()) || !pendudukLama.getTanggal_lahir().equals(penduduk.getTanggal_lahir())) {
			String kodeKecamatan = pendudukMapper.selectKodeKecamatan(penduduk.getId_keluarga()).getKode_kecamatan();
			String[] tgl_lahir = penduduk.getTanggal_lahir().split("-");
			String nik = "";
			
			if(penduduk.getJenis_kelamin().equals("1")) {
				nik = kodeKecamatan.substring(0, 6) + (Integer.parseInt(tgl_lahir[2]) + 40) + tgl_lahir[1] + tgl_lahir[0].substring(2, 4) + "%";
			} else {
				nik = kodeKecamatan.substring(0, 6) + Integer.parseInt(tgl_lahir[2]) + tgl_lahir[1] + tgl_lahir[0].substring(2, 4) + "%";
			}
			
			
			List<PendudukModel> pendudukAda = pendudukMapper.cekPenduduk(nik);
			int angkaAkhir = 0;
			if(pendudukAda.size() > 0) {
				angkaAkhir = Integer.parseInt(pendudukAda.get(pendudukAda.size()-1).getNik().substring(12, 16));
			}
			nik = nik.substring(0, 12);
			if(angkaAkhir < 9) {
				nik += "000" + (angkaAkhir + 1);
			} else if(angkaAkhir < 99) {
				nik += "00" + (angkaAkhir + 1);
			} else if(angkaAkhir < 999) {
				nik += "0" + (angkaAkhir + 1);
			} else {
				nik += (angkaAkhir + 1);
			}
			log.info("size orang ada {}", pendudukAda);
			log.info("tanggal lahir {}", penduduk.getTanggal_lahir());
			log.info("kode kecamatan {}", kodeKecamatan);
			log.info("nik {}", nik);
			penduduk.setNik(nik);
		}
		pendudukMapper.updatePenduduk(penduduk, nikLama);
	}

	@Override
	public void nonaktifkanPenduduk(String nik) {
		log.info("nik {}", nik);
		pendudukMapper.setPendudukWafat("1", nik);
		
		PendudukModel penduduk = pendudukMapper.selectPenduduk(nik);
		log.info("id keluarga penduduk {}", penduduk.getId_keluarga());
		KeluargaModel keluarga = keluargaMapper.selectKeluargaById(penduduk.getId_keluarga());
		int jmlAnggotaKeluarga = keluarga.getAnggotaKeluarga().size();
		int countHidup = 0;
		
		for(int ii = 0; ii < jmlAnggotaKeluarga; ii++) {
			if(keluarga.getAnggotaKeluarga().get(ii).getIs_wafat().equals("0")) {
				countHidup++;
			}
		}
		
		if(countHidup == 0) {
			keluargaMapper.nonaktifkanKeluarga("1", keluarga.getNomor_kk());
		}
	}

}
