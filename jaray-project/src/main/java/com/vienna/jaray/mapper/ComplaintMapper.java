package com.vienna.jaray.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.vienna.jaray.entity.ComplaintEntity;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ComplaintMapper {
	
	public List<ComplaintEntity> findComplaintList();
	
	public int addComplaint(@Param("entity")ComplaintEntity complaintEntity);
	
	public ComplaintEntity findComplaintByCid(@Param("cid")String cid);

	public int delComplaint(@Param("cids") String[] cids);

	public int updateComplaint(@Param("entity")ComplaintEntity complaintEntity);
	
}
