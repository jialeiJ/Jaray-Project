package com.vienna.jaray.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.vienna.jaray.entity.complaint.Complaint;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ComplaintMapper {
	
	public List<Complaint> findAll();
	
	public int add(@Param("entity") Complaint complaintEntity);
	
	public Complaint findByCid(@Param("cid")String cid);

	public int deleteByCids(@Param("cids") String[] cids);

	public int updateByCid(@Param("entity") Complaint complaintEntity);
	
}
