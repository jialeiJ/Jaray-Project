package com.vienna.jaray.mapper;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@SpringBootTest
public class ComplaintMapperTest {
	
	@Autowired
	private ComplaintMapper complaintMapper;
	
	@Test
	public void testFindComplaintList() throws Exception {
		complaintMapper.findComplaintList();
        log.info("{}",complaintMapper.findComplaintList().size());
    }

}
