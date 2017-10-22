package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.KeluargaMapper;
import com.example.dao.WilayahMapper;
import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WilayahServiceDatabase implements WilayahService
{
	@Autowired
	private WilayahMapper wilayahMapper;
	@Autowired
	private KeluargaMapper keluargaMapper;
	
	@Override
	public List<KotaModel> selectAllKota() {
		return wilayahMapper.selectAllKota();
	}

	@Override
	public List<KecamatanModel> getKecamatanByKota(String id_kota) {
		log.info("id kota {}", id_kota);
		return wilayahMapper.selectAllKecamatan(id_kota);
	}

	@Override
	public List<KelurahanModel> getKelurahanByKecamatan(String id_kecamatan) {
		List<KelurahanModel> cek = wilayahMapper.selectAllKelurahan(id_kecamatan);
		for(int ii = 0; ii < cek.size(); ii++) {
			log.info("{}", cek.get(ii).getNama_kelurahan());
		}
		return wilayahMapper.selectAllKelurahan(id_kecamatan);
	}

	@Override
	public List<PendudukModel> getPendudukByKelurahan(String id_kelurahan) {
		List<KeluargaModel> keluarga = keluargaMapper.getKeluargaByIdKelurahan(id_kelurahan);
		List<PendudukModel> penduduk = new ArrayList<PendudukModel>();
		
		for(int ii = 0; ii < keluarga.size(); ii++) {
			KeluargaModel each = keluargaMapper.selectKeluarga(keluarga.get(ii).getNomor_kk());
			
			for(int jj = 0; jj < each.getAnggotaKeluarga().size(); jj++) {
				penduduk.add(each.getAnggotaKeluarga().get(jj));
				log.info("nik penduduk {}", penduduk.get(jj).getNik());
			}
		}
		log.info("{}", penduduk.size());
		return penduduk;
	}
	
	@Override
	public String getNamaKotaById(String id_kota)
	{
		KotaModel kota = wilayahMapper.getNamaKota(id_kota);
		
		return kota.getNama_kota();
	}
	
	@Override
	public String getNamaKecamatanById(String id_kecamatan)
	{
		log.info("id kecamatan {}", id_kecamatan);
		KecamatanModel kecamatan = wilayahMapper.getNamaKecamatan(id_kecamatan);
		
		return kecamatan.getNama_kecamatan();
	}
	
	@Override
	public String getNamaKelurahanById(String id_kelurahan)
	{
		KelurahanModel kelurahan = wilayahMapper.getNamaKelurahan(id_kelurahan);
		
		return kelurahan.getNama_kelurahan();
	}
}
