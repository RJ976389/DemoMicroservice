package com.sigma.om.cs.config;

import com.sigma.om.sdk.repo.EntitySpecRepository;
import com.sigma.om.sdk.repo.OrderRepository;
import com.sigma.om.utils.SequenceMgr;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration("soiConfigurationBean")
@DependsOn({ OrderRepository.BEAN_NAME, EntitySpecRepository.BEAN_NAME, SequenceMgr.BEAN_NAME })
public class SOIConfigurationBean {
}
