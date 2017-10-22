package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.model.KecamatanModel;
import com.example.model.PendudukModel;


@Mapper
public interface PendudukMapper {
	@Select("select penduduk.id, nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni,"
			+ " id_keluarga, agama, pekerjaan, status_perkawinan, status_dalam_keluarga, golongan_darah, is_wafat,"
			+ " alamat, RT, RW, nama_kelurahan, nama_kecamatan, nama_kota from penduduk join keluarga on keluarga.id = penduduk.id_keluarga"
			+ " join kelurahan on keluarga.id_kelurahan = kelurahan.id join kecamatan on kelurahan.id_kecamatan = kecamatan.id"
			+ " join kota on kecamatan.id_kota = kota.id where nik = #{nik}")
	@Results(value= {
    		@Result(property="id", column="id"),
    		@Result(property="nik", column="nik"),
    		@Result(property="nama", column="nama"),
    		@Result(property="tempat_lahir", column="tempat_lahir"),
    		@Result(property="tanggal_lahir", column="tanggal_lahir"),
    		@Result(property="jenis_kelamin", column="jenis_kelamin"),
    		@Result(property="is_wni", column="is_wni"),
    		@Result(property="id_keluarga", column="id_keluarga"),
    		@Result(property="agama", column="agama"),
    		@Result(property="pekerjaan", column="pekerjaan"),
    		@Result(property="status_perkawinan", column="status_perkawinan"),
    		@Result(property="status_dalam_keluarga", column="status_dalam_keluarga"),
    		@Result(property="golongan_darah", column="golongan_darah"),
    		@Result(property="is_wafat", column="is_wafat"),
    		@Result(property="alamat", column="alamat"),
    		@Result(property="RT", column="RT"),
    		@Result(property="RW", column="RW"),
    		@Result(property="kelurahan", column="nama_kelurahan"),
    		@Result(property="kecamatan", column="nama_kecamatan"),
    		@Result(property="kota", column="nama_kota")
    })
    PendudukModel selectPenduduk (@Param("nik") String nik);
	
	@Select("select penduduk.id from penduduk order by id desc limit 1")
	PendudukModel getLastPenduduk();
	
	@Insert("INSERT INTO penduduk (id, nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, id_keluarga, agama, pekerjaan,"
			+ " status_perkawinan, status_dalam_keluarga, golongan_darah, is_wafat) VALUES (#{id}, #{nik}, #{nama}, #{tempat_lahir},"
			+ " #{tanggal_lahir}, #{jenis_kelamin}, #{is_wni}, #{id_keluarga}, #{agama}, #{pekerjaan}, #{status_perkawinan},"
			+ " #{status_dalam_keluarga}, #{golongan_darah}, #{is_wafat})")
	void addPenduduk(PendudukModel penduduk);
	
	@Select("select kecamatan.kode_kecamatan from penduduk join keluarga on penduduk.id_keluarga = keluarga.id join kelurahan on keluarga.id_kelurahan = kelurahan.id join kecamatan on"
			+ " kelurahan.id_kecamatan = kecamatan.id where penduduk.id_keluarga = #{id_keluarga} limit 1")
	KecamatanModel selectKodeKecamatan(@Param("id_keluarga") String id_keluarga);
	
	@Select("select nik from penduduk where nik LIKE #{nik}")
	List<PendudukModel> cekPenduduk(@Param("nik") String nik);
	
	@Update("UPDATE penduduk SET nik = #{penduduk.nik}, nama = #{penduduk.nama}, tempat_lahir = #{penduduk.tempat_lahir}, tanggal_lahir = #{penduduk.tanggal_lahir}, "
			+ "jenis_kelamin = #{penduduk.jenis_kelamin}, is_wni = #{penduduk.is_wni}, id_keluarga = #{penduduk.id_keluarga}, agama = #{penduduk.agama}, pekerjaan = #{penduduk.pekerjaan},"
			+ " status_perkawinan = #{penduduk.status_perkawinan}, status_dalam_keluarga = #{penduduk.status_dalam_keluarga}, golongan_darah = #{penduduk.golongan_darah},"
			+ " is_wafat = #{penduduk.is_wafat} WHERE nik = #{nikLama}")
	void updatePenduduk(@Param("penduduk") PendudukModel penduduk, @Param("nikLama") String nikLama);
	
	@Update("UPDATE penduduk SET is_wafat = #{is_wafat} WHERE nik = #{nik}")
	void setPendudukWafat(@Param("is_wafat") String is_wafat, @Param("nik") String nik);
	
	@Select("select nik from penduduk where id_keluarga = #{id_keluarga}")
	List<PendudukModel> getPendudukByIdKeluarga(@Param("id_keluarga") String id_keluarga);
}
