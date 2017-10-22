package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PendudukModel
{
	private String id;
	private String nik;
	private String nama;
	private String tempat_lahir;
	private String tanggal_lahir;
	private String jenis_kelamin;
	private String is_wni;
	private String id_keluarga;
	private String agama;
	private String pekerjaan;
	private String status_perkawinan;
	private String status_dalam_keluarga;
	private String golongan_darah;
	private String is_wafat;
	
	private String alamat;
	private String RT;
	private String RW;
	private String kelurahan;
	private String kecamatan;
	private String kota;
			
//		if(!this.tanggal_lahir.equals(null)) {
//			String[] tgl_lahir = this.tanggal_lahir.split("-");
//			tgl_lahir[2] = "" + Integer.parseInt(tgl_lahir[2]);
//			int bulan = Integer.parseInt(tgl_lahir[1]);
//			
//			switch(bulan) {
//				case 1:
//					tgl_lahir[1] = "Januari";
//					break;
//				case 2:
//					tgl_lahir[1] = "Februari";
//					break;
//				case 3:
//					tgl_lahir[1] = "Maret";
//					break;
//				case 4:
//					tgl_lahir[1] = "April";
//					break;
//				case 5:
//					tgl_lahir[1] = "Mei";
//					break;
//				case 6:
//					tgl_lahir[1] = "Juni";
//					break;
//				case 7:
//					tgl_lahir[1] = "Juli";
//					break;
//				case 8:
//					tgl_lahir[1] = "Agustus";
//					break;
//				case 9:
//					tgl_lahir[1] = "September";
//					break;
//				case 10:
//					tgl_lahir[1] = "Oktober";
//					break;
//				case 11:
//					tgl_lahir[1] = "November";
//					break;
//				case 12:
//					tgl_lahir[1] = "Desember";
//					break;
//			}
//			
//			this.tanggal_lahir = tgl_lahir[2] + " " + tgl_lahir[1] + " " + tgl_lahir[0];
//		}
}
