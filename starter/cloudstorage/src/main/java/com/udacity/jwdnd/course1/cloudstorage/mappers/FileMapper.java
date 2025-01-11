package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE USERID = #{userId}")
    List<File> getFiles(Integer userId);

    @Select("SELECT * FROM FILES WHERE FILEID = #{fileId}")
    File getFile(Integer fileId);

    @Select("SELECT * FROM FILES WHERE USERID = #{userid} AND FILENAME = #{filename}")
    File getFileByName(String filename, Integer userid);

    @Insert("INSERT INTO FILES (FILENAME, CONTENTTYPE, FILESIZE, USERID, FILEDATA) " +
            "VALUES(#{filename}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer insert(File file);

    @Delete("DELETE FROM FILES WHERE FILEID = #{fileId}")
    void delete(Integer fileId);
}