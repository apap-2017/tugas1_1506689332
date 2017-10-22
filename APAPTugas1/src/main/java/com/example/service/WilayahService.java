package com.example.service;

import java.util.List;

import com.example.model.KecamatanModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;

public interface WilayahService {
	List<KotaModel> selectAllKota();
	List<KecamatanModel> getKecamatanByKota(String id_kota);
	List<KelurahanModel> getKelurahanByKecamatan(String id_kecamatan);
	List<PendudukModel> getPendudukByKelurahan(String id_kelurahan);
	String getNamaKotaById(String id_kota);
	String getNamaKecamatanById(String id_kelurahan);
	String getNamaKelurahanById(String id_kelurahan);
}
