package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import com.example.model.KecamatanModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;

@Mapper
public interface WilayahMapper {
	@Select("SELECT id, nama_kota, kode_kota FROM kota")
	@Results(value= {
			@Result(property="id", column="id"),
			@Result(property="kode_kota", column="kode_kota"),
			@Result(property="nama_kota", column="nama_kota")
	})
	List<KotaModel> selectAllKota();
	
	@Select("SELECT id, id_kota, kode_kecamatan, nama_kecamatan from kecamatan where id_kota = #{id_kota}")
	List<KecamatanModel> selectAllKecamatan(@Param("id_kota") String id_kota);
	
	@Select("SELECT id, id_kecamatan, kode_kelurahan, nama_kelurahan, kode_pos from kelurahan WHERE id_kecamatan = #{id_kecamatan}")
	List<KelurahanModel> selectAllKelurahan(@Param("id_kecamatan") String id_kecamatan);
	
	@Select("SELECT id from kelurahan where nama_kelurahan = #{namaKelurahan}")
	KelurahanModel getIdKelurahan(@Param("namaKelurahan") String namaKelurahan);
	
	@Select("SELECT nama_kota from kota where id = #{id}")
	KotaModel getNamaKota(@Param("id") String id);
	
	@Select("SELECT nama_kecamatan from kecamatan where id = #{id}")
	KecamatanModel getNamaKecamatan(@Param("id") String id);
	
	@Select("SELECT nama_kelurahan from kelurahan where id = #{id}")
	KelurahanModel getNamaKelurahan(@Param("id") String id);
}
