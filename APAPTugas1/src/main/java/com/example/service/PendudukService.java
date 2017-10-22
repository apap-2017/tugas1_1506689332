package com.example.service;

import com.example.model.PendudukModel;

public interface PendudukService {
	PendudukModel selectPenduduk (String nik);
	String addPenduduk(PendudukModel penduduk);
	void updatePenduduk(PendudukModel penduduk, String nik);
	void nonaktifkanPenduduk(String nik);
	String extractDate(String date);
}
