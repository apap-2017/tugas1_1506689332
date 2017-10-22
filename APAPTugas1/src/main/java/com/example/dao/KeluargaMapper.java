package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;

import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.PendudukModel;

@Mapper
public interface KeluargaMapper {
	@Select("select keluarga.id, nomor_kk, alamat, rt, rw, id_kelurahan, is_tidak_berlaku, nama_kelurahan, nama_kecamatan,"
			+ " nama_kota from keluarga join kelurahan on keluarga.id_kelurahan = kelurahan.id join kecamatan on"
			+ " kelurahan.id_kecamatan = kecamatan.id join kota on kecamatan.id_kota = kota.id where nomor_kk = #{nomor_kk}")
	@Results(value = {
			@Result(property="id", column="id"),
			@Result(property="nomor_kk", column="nomor_kk"),
			@Result(property="alamat", column="alamat"),
			@Result(property="rt", column="rt"),
			@Result(property="rw", column="rw"),
			@Result(property="id_kelurahan", column="id_kelurahan"),
			@Result(property="is_tidak_berlaku", column="is_tidak_berlaku"),
			@Result(property="id_kelurahan", column="id_kelurahan"),
			@Result(property="is_tidak_berlaku", column="is_tidak_berlaku"),
			@Result(property="kelurahan", column="nama_kelurahan"),
			@Result(property="kecamatan", column="nama_kecamatan"),
			@Result(property="kota", column="nama_kota"),
			@Result(property="anggotaKeluarga", column="id",
					javaType = List.class,
					many=@Many (select = "selectPenduduk"))
	})
	KeluargaModel selectKeluarga(@Param("nomor_kk") String nomor_kk);
	
	@Select("select keluarga.id, nomor_kk, alamat, rt, rw, id_kelurahan, is_tidak_berlaku, nama_kelurahan, nama_kecamatan,"
			+ " nama_kota from keluarga join kelurahan on keluarga.id_kelurahan = kelurahan.id join kecamatan on"
			+ " kelurahan.id_kecamatan = kecamatan.id join kota on kecamatan.id_kota = kota.id where keluarga.id = #{id_keluarga}")
	@Results(value = {
			@Result(property="id", column="id"),
			@Result(property="nomor_kk", column="nomor_kk"),
			@Result(property="alamat", column="alamat"),
			@Result(property="rt", column="rt"),
			@Result(property="rw", column="rw"),
			@Result(property="id_kelurahan", column="id_kelurahan"),
			@Result(property="is_tidak_berlaku", column="is_tidak_berlaku"),
			@Result(property="id_kelurahan", column="id_kelurahan"),
			@Result(property="is_tidak_berlaku", column="is_tidak_berlaku"),
			@Result(property="kelurahan", column="nama_kelurahan"),
			@Result(property="kecamatan", column="nama_kecamatan"),
			@Result(property="kota", column="nama_kota"),
			@Result(property="anggotaKeluarga", column="id",
					javaType = List.class,
					many=@Many (select = "selectPenduduk"))
	})
	KeluargaModel selectKeluargaById(@Param("id_keluarga") String id_keluarga);
	
	@Select("select penduduk.id, nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, id_keluarga, agama, pekerjaan,"
			+ " status_perkawinan, status_dalam_keluarga, golongan_darah, is_wafat from keluarga join penduduk on keluarga.id = "
			+ "penduduk.id_keluarga where keluarga.id = #{id}")
	List<PendudukModel> selectPenduduk (@Param("id") String id);
	
	@Select("select keluarga.id from keluarga order by id desc limit 1")
	KeluargaModel getLastKeluarga();
	
	@Select("select kelurahan.id from kelurahan join kecamatan on kecamatan.id = kelurahan.id_kecamatan where nama_kelurahan"
			+ " = #{kelurahan} and nama_kecamatan = #{kecamatan}")
	KelurahanModel getKelurahan(@Param("kelurahan") String kelurahan, @Param("kecamatan") String kecamatan);
	
	@Select("select kecamatan.kode_kecamatan from kecamatan join kota on kecamatan.id_kota = kota.id where nama_kecamatan"
			+ " = #{kecamatan} and nama_kota = #{kota}")
	KecamatanModel getKodeKecamatan(@Param("kecamatan") String kecamatan, @Param("kota") String kota);
	
	@Select("select nomor_kk from keluarga where nomor_kk LIKE #{nkk}")
	List<KeluargaModel> cekKeluarga(@Param("nkk") String nkk);
	
	@Insert("INSERT INTO keluarga (id, nomor_kk, alamat, RT, RW, id_kelurahan, is_tidak_berlaku) VALUES (#{id}, #{nomor_kk},"
			+ " #{alamat}, #{rt}, #{rw}, #{id_kelurahan}, #{is_tidak_berlaku})")
	void addKeluarga(KeluargaModel keluarga);
	
	@Update("UPDATE keluarga SET nomor_kk = #{keluarga.nomor_kk}, alamat = #{keluarga.alamat}, RT = #{keluarga.rt},"
			+ " RW = #{keluarga.rw}, id_kelurahan = #{keluarga.id_kelurahan} WHERE nomor_kk = #{nkkLama}")
	void updateKeluarga(@Param("keluarga") KeluargaModel keluarga, @Param("nkkLama") String nkkLama);
	
	@Update("UPDATE keluarga SET is_tidak_berlaku = #{is_tidak_berlaku} WHERE nkk = #{nkk}")
	void nonaktifkanKeluarga(@Param("is_tidak_berlaku") String is_tidak_berlaku, @Param("nkk") String nkk);
	
	@Select("select nomor_kk from keluarga where id_kelurahan = #{id_kelurahan}")
	List<KeluargaModel> getKeluargaByIdKelurahan(@Param("id_kelurahan") String id_kelurahan);
}
