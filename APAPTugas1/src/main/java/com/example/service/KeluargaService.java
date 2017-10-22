package com.example.service;

import java.util.List;

import com.example.model.KeluargaModel;

public interface KeluargaService {
	KeluargaModel selectKeluarga(String nomor_kk);
	String addKeluarga(KeluargaModel keluarga);
	void updateKeluarga(KeluargaModel keluarga, String nkk);
	List<String> convertDate(KeluargaModel keluarga);
}
