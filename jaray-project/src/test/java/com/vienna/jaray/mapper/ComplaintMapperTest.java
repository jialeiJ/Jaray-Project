package com.vienna.jaray.mapper;

import com.vienna.jaray.mapper.complaint.ComplaintMapper;
import com.vienna.jaray.model.system.CommonParamsModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class ComplaintMapperTest {
	
	@Autowired
	private ComplaintMapper complaintMapper;
	
	@Test
	public void testFindComplaintList() throws Exception {
		CommonParamsModel commonParamsModel = new CommonParamsModel();
        log.info("{}",complaintMapper.findAll(commonParamsModel).size());
    }

}
